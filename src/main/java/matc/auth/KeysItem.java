package matc.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * KeysItem - Represents an individual JSON Web Key (JWK) item from a JWKS response.
 * Holds information about the key type, usage, algorithm, and key components.
 */
public class KeysItem{

	@JsonProperty("kty")
	private String kty;

	@JsonProperty("e")
	private String E;

	@JsonProperty("use")
	private String use;

	@JsonProperty("kid")
	private String kid;

	@JsonProperty("alg")
	private String alg;

	@JsonProperty("n")
	private String N;

	/**
	 * Gets the key type (kty) of the JWK.
	 *
	 * @return the key type as a String
	 */
	public String getKty(){
		return kty;
	}

	/**
	 * Gets the public exponent (e) of the RSA key.
	 *
	 * @return the exponent as a String
	 */
	public String getE(){
		return E;
	}

	/**
	 * Gets the intended use (use) of the key.
	 *
	 * @return the use of the key as a String
	 */
	public String getUse(){
		return use;
	}

	/**
	 * Gets the Key ID (kid) used to identify the key.
	 *
	 * @return the key ID as a String
	 */
	public String getKid(){
		return kid;
	}

	/**
	 * Gets the algorithm (alg) used with the key.
	 *
	 * @return the algorithm as a String
	 */
	public String getAlg(){
		return alg;
	}

	/**
	 * Gets the modulus (n) value of the RSA key.
	 *
	 * @return the modulus as a String
	 */
	public String getN(){
		return N;
	}
}