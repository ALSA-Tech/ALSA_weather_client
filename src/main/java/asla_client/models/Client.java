package asla_client.models;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Sebastian Norén <s.norén@gmail.com>
 */
public class Client {
    private UUID id;
    private String name;
    private String password;
    private String email;
    private ArrayList<String> locationSubscriptions;

    public Client(UUID id, String name, String password, String email, ArrayList<String> locationSubscriptions) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.locationSubscriptions = locationSubscriptions;
    }


    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getLocationSubscriptions() {
        return this.locationSubscriptions;
    }
}
