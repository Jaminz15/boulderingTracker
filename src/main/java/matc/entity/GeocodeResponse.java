package matc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

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


}