package weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Root{
    public String type;
    public Geometry geometry;
    public Properties properties;
}
