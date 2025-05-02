package matc.controller;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import matc.auth.*;
import matc.entity.User;
import matc.persistence.GenericDao;
import matc.util.PropertiesLoader;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/auth"})
public class Auth extends HttpServlet implements PropertiesLoader {
    private Properties properties;
    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String OAUTH_URL;
    private String LOGIN_URL;
    private String REDIRECT_URL;
    private String REGION;
    private String POOL_ID;
    private Keys jwks;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();

        CLIENT_ID = (String) context.getAttribute("CLIENT_ID");
        CLIENT_SECRET = (String) context.getAttribute("CLIENT_SECRET");
        OAUTH_URL = (String) context.getAttribute("OAUTH_URL");
        LOGIN_URL = (String) context.getAttribute("LOGIN_URL");
        REDIRECT_URL = (String) context.getAttribute("REDIRECT_URL");
        REGION = (String) context.getAttribute("REGION");
        POOL_ID = (String) context.getAttribute("POOL_ID");

        loadKey();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authCode = req.getParameter("code");
        if (authCode == null) {
            logger.error("Authorization code is missing in request.");
            req.setAttribute("errorMessage", "Authorization code is missing.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("error.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        HttpRequest authRequest = buildAuthRequest(authCode);
        try {
            TokenResponse tokenResponse = getToken(authRequest);
            String displayName = validateAndSaveUser(tokenResponse, req);
            req.setAttribute("userName", displayName);
        } catch (IOException | InterruptedException e) {
            logger.error("Error getting or validating the token: " + e.getMessage(), e);
            req.setAttribute("errorMessage", "Authentication failed.");
            RequestDispatcher dispatcher = req.getRequestDispatcher("error.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        resp.sendRedirect("dashboard");
    }

    private TokenResponse getToken(HttpRequest authRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(authRequest, HttpResponse.BodyHandlers.ofString());

        logger.debug("Response headers: " + response.headers());
        logger.debug("Response body: " + response.body());

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), TokenResponse.class);
    }

    private String validateAndSaveUser(TokenResponse tokenResponse, HttpServletRequest req) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CognitoTokenHeader tokenHeader = mapper.readValue(CognitoJWTParser.getHeader(tokenResponse.getIdToken()).toString(), CognitoTokenHeader.class);

        String keyId = tokenHeader.getKid();
        KeysItem correctKey = jwks.getKeys().stream()
                .filter(key -> key.getKid().equals(keyId))
                .findFirst().orElse(null);

        if (correctKey == null) {
            logger.error("No matching key found in JWKS for kid: " + keyId);
            throw new IOException("No matching key found");
        }

        PublicKey publicKey;
        try {
            BigInteger modulus = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(correctKey.getN()));
            BigInteger exponent = new BigInteger(1, org.apache.commons.codec.binary.Base64.decodeBase64(correctKey.getE()));
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            logger.error("Error creating public key: " + e.getMessage(), e);
            throw new IOException("Failed to create public key for token validation", e);
        }

        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, POOL_ID))
                .acceptLeeway(60)
                .withClaim("token_use", "id")
                .build();

        DecodedJWT jwt = verifier.verify(tokenResponse.getIdToken());

        String cognitoSub = jwt.getClaim("sub").asString();
        String email = jwt.getClaim("email").asString();
        String username = jwt.getClaim("preferred_username").asString();

        // Extract user groups from JWT and determine if user is an admin
        List<String> groups = jwt.getClaim("cognito:groups").asList(String.class);
        boolean isAdmin = groups != null && groups.contains("Admin");

        User user; // <--- Declare user here
        HttpSession session = req.getSession(); // <--- Declare session here

        logger.info("User logged in - Cognito Sub: " + cognitoSub
                + ", Email: " + email
                + ", Username: " + username
                + ", isAdmin: " + isAdmin);

        // Check if the user exists in the database
        GenericDao<User> userDao = new GenericDao<>(User.class);
        List<User> users = userDao.findByPropertyEqual("cognitoSub", cognitoSub);

        if (users.isEmpty()) {
            user = new User(email, username, cognitoSub);
            user.setIsAdmin(isAdmin);
            int userId = userDao.insert(user);
            logger.info("New user inserted with ID: " + userId);
        } else {
            user = users.get(0);
            if (user.isAdmin() != isAdmin) {
                user.setIsAdmin(isAdmin);
                userDao.update(user); // or saveOrUpdate if you added it
                logger.info("Updated isAdmin status for user: " + user.getEmail());
            }
        }

        // **Store user role and details in session**
        session.setAttribute("user", user);

        return user.getUsername() != null ? user.getUsername() : user.getEmail();
    }

    private HttpRequest buildAuthRequest(String authCode) {
        String keys = CLIENT_ID + ":" + CLIENT_SECRET;
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("client_secret", CLIENT_SECRET);
        parameters.put("client_id", CLIENT_ID);
        parameters.put("code", authCode);
        parameters.put("redirect_uri", REDIRECT_URL);

        String form = parameters.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(keys.getBytes());

        return HttpRequest.newBuilder()
                .uri(URI.create(OAUTH_URL))
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic " + encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();
    }

    private void loadKey() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            URL jwksURL = new URL(String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", REGION, POOL_ID));
            File jwksFile = new File("jwks.json");
            FileUtils.copyURLToFile(jwksURL, jwksFile);
            jwks = mapper.readValue(jwksFile, Keys.class);
            logger.debug("JWKS loaded successfully.");
        } catch (IOException e) {
            logger.error("Cannot load JWKS: " + e.getMessage(), e);
        }
    }
}