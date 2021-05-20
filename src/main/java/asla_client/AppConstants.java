package asla_client;

import asla_client.models.Client;
import asla_client.models.Location;
import asla_client.utils.HTTPController;
import asla_client.utils.StringResource;
import asla_client.utils.WriteReadFiles;

import java.io.File;
import java.security.PublicKey;
import java.util.ArrayList;

public class AppConstants {

    private static AppConstants instance = null;
    private volatile boolean pingFlag = true;
    private volatile boolean offlineMODE = true;
    private volatile boolean serverCon = false;

    private PublicKey serverPublicKey;
    private Client loggedInClient;
    private final HTTPController httpController;
    private ArrayList<Location> locations;
    private final WriteReadFiles writeReadFiles;

    public static AppConstants getInstance() {
        if (instance == null) {
            instance = new AppConstants();
        }
        return instance;
    }

    public AppConstants() {
        System.out.println("AppConstant Runs");
        httpController = new HTTPController();
        locations = new ArrayList<>();
        writeReadFiles = new WriteReadFiles();
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


    public void saveToCache(Location location){
        locations.add(location);
        writeReadFiles.writeObjectFile(locations, new File(StringResource.FILE_CACHE));

    }
}




