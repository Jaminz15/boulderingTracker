package matc.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CognitoTokenHeader - Represents the header portion of a JWT token from AWS Cognito.
 * Contains key ID (kid) and algorithm (alg) used for signing.
 */
public class CognitoTokenHeader{

	// The Key ID used to identify the public key for token validation.
	@JsonProperty("kid")
	private String kid;

	// The algorithm used to sign the JWT.
	@JsonProperty("alg")
	private String alg;

	/**
	 * Gets the Key ID (kid) from the JWT header.
	 *
	 * @return the Key ID as a String
	 */
	public String getKid(){
		return kid;
	}

	/**
	 * Gets the algorithm (alg) used to sign the JWT.
	 *
	 * @return the algorithm as a String
	 */
	public String getAlg(){
		return alg;
	}
}