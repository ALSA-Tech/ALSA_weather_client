package asla_client;

import asla_client.utils.HTTPController;

import java.security.PublicKey;

public class AppConstants {

    private static AppConstants instance = null;

    private PublicKey serverPublicKey;
    private final HTTPController httpController;

    public static AppConstants getInstance() {
        if (instance == null) {
            instance = new AppConstants();
        }
        return instance;
    }

    public AppConstants() {
        System.out.println("AppConstant Runs");
        httpController = new HTTPController();
    }

    public HTTPController getHttpController() {
        return httpController;
    }

    public PublicKey getServerPublicKey() {
        return serverPublicKey;
    }

    public void setServerPublicKey(PublicKey serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

}




