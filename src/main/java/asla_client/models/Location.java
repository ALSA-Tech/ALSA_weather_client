package asla_client.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Model
 */

public class Location implements Serializable {

    private String location; // Name
    private String requestTimeStamp;
    private ArrayList<LocationDataXY> dataSeriesXY;

    public Location() {
    }

    public Location(String location, String requestTimeStamp, ArrayList<LocationDataXY> dataSeriesXY) {
        this.location = location;
        this.requestTimeStamp = requestTimeStamp;
        this.dataSeriesXY = dataSeriesXY;
    }

    public String getLocation() {
        return location;
    }

    public String getRequestTimeStamp() {
        return requestTimeStamp;
    }

    public ArrayList<LocationDataXY> getDataSeriesXY() {
        return dataSeriesXY;
    }

    @Override
    public String toString() {
        return "Location{" +
                "location='" + location + '\'' +
                ", requestTimeStamp='" + requestTimeStamp + '\'' +
                ", dataSeriesXY=" + dataSeriesXY +
                '}';
    }
}
