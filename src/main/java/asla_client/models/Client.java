package asla_client.models;

import java.util.List;


public class Client {
    private int id;
    private String name;
    private String password;
    private String email;
    private List<String> locationSubscriptions;


    public Client() {
    }

    // All args constructor
    public Client(int id, String name, String password, String email, List<String> locationSubscriptions) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.locationSubscriptions = locationSubscriptions;
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getLocationSubscriptions() {
        return locationSubscriptions;
    }

    public void setLocationSubscriptions(List<String> locationSubscriptions) {
        this.locationSubscriptions = locationSubscriptions;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", locationSubscriptions=" + locationSubscriptions +
                '}';
    }

}