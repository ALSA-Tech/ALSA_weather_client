package asla_client.models;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author Sebastian Norén <s.norén@gmail.com>
 */
public class Client {
    private int id; // string id  instead?
    private String name;
    private String password;
    private String email;

    public Client (){
    }

    public Client(int id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }


    public int getId() {
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

}
