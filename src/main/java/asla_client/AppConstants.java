package asla_client;

import asla_client.models.Client;
import asla_client.utils.HTTPController;

import java.security.PublicKey;

public class AppConstants {

    private static AppConstants instance = null;
    private volatile boolean pingFlag = true;
    private volatile boolean offlineMODE = true;
    private volatile boolean serverCon = false;

    private PublicKey serverPublicKey;
    private Client loggedInClient;
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
        loggedInClient = null;
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

    public Client getLoggedInClient() {
        return loggedInClient;
    }

    public void setLoggedInClient(Client loggedInClient) {
        this.loggedInClient = loggedInClient;
    }

    public void setPingFlag(boolean pingFlag) {
       this.pingFlag = pingFlag;
    }

    public boolean isPingFlag() {
        return pingFlag;
    }

    public boolean isOfflineMODE() {
        return offlineMODE;
    }

    public void setOfflineMODE(boolean offlineMODE) {
        this.offlineMODE = offlineMODE;
    }

    public boolean isServerCon() {
        return serverCon;
    }

    public void setServerCon(boolean serverCon) {
        this.serverCon = serverCon;
    }
}




