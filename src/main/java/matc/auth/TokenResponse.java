package matc.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * TokenResponse - Represents the response from an OAuth token exchange.
 * Contains tokens and metadata returned from AWS Cognito after authentication.
 */
public class TokenResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("id_token")
	private String idToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private int expiresIn;

	/**
	 * Gets the access token for authorization.
	 *
	 * @return the access token as a String
	 */
	public String getAccessToken(){
		return accessToken;
	}

	/**
	 * Gets the refresh token used for obtaining new access tokens.
	 *
	 * @return the refresh token as a String
	 */
	public String getRefreshToken(){
		return refreshToken;
	}

	/**
	 * Gets the ID token containing identity claims.
	 *
	 * @return the ID token as a String
	 */
	public String getIdToken(){
		return idToken;
	}

	/**
	 * Gets the type of the token (e.g., "Bearer").
	 *
	 * @return the token type as a String
	 */
	public String getTokenType(){
		return tokenType;
	}

	/**
	 * Gets the duration (in seconds) before the access token expires.
	 *
	 * @return the expiration time in seconds
	 */
	public int getExpiresIn(){
		return expiresIn;
	}
}