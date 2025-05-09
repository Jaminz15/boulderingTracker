package matc.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Keys - Represents a collection of JSON Web Keys (JWKS) retrieved from AWS Cognito.
 * Holds a list of public keys used for verifying JWT signatures.
 */
public class Keys{

	/**
	 * A list of KeysItem objects representing individual public keys
	 * retrieved from the JWKS endpoint.
	 */
	@JsonProperty("keys")
	private List<KeysItem> keys;

	/**
	 * Retrieves the list of public keys used for JWT signature validation.
	 *
	 * @return a list of KeysItem objects
	 */
	public List<KeysItem> getKeys(){
		return keys;
	}
}