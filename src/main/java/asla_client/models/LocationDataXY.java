package asla_client.models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Sebastian Norén <s.norén@gmail.com>
 */
public class LocationDataXY implements Serializable {

    private String localDate;
    private double temp;

    public LocationDataXY() {
    }

    public LocationDataXY(String localDate, double temp) {
        this.localDate = localDate;
        this.temp = temp;
    }

    public String getLocalDate() {
        return localDate;
    }

    public Double getTemp() {
        return temp;
    }


    @Override
    public String toString() {
        return "LocationDataXY{" +
                "localDate=" + localDate +
                ", temp=" + temp +
                '}';
    }
}
