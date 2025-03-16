package matc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The type Geocode response.
 */
public class GeocodeResponse {

    @JsonProperty("place_id")
    private long placeId;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("lat")
    private String latitude;

    @JsonProperty("lon")
    private String longitude;

    @JsonProperty("boundingbox")
    private List<String> boundingBox;

    @JsonProperty("importance")
    private double importance;

    @JsonProperty("addresstype")
    private String addressType;

    @JsonProperty("name")
    private String name;

    /**
     * Gets place id.
     *
     * @return the place id
     */
// Getters and Setters
    public long getPlaceId() {
        return placeId;
    }

    /**
     * Sets place id.
     *
     * @param placeId the place id
     */
    public void setPlaceId(long placeId) {
        this.placeId = placeId;
    }

    /**
     * Gets display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets display name.
     *
     * @param displayName the display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets bounding box.
     *
     * @return the bounding box
     */
    public List<String> getBoundingBox() {
        return boundingBox;
    }

    /**
     * Sets bounding box.
     *
     * @param boundingBox the bounding box
     */
    public void setBoundingBox(List<String> boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * Gets importance.
     *
     * @return the importance
     */
    public double getImportance() {
        return importance;
    }

    /**
     * Sets importance.
     *
     * @param importance the importance
     */
    public void setImportance(double importance) {
        this.importance = importance;
    }

    /**
     * Gets address type.
     *
     * @return the address type
     */
    public String getAddressType() {
        return addressType;
    }

    /**
     * Sets address type.
     *
     * @param addressType the address type
     */
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}