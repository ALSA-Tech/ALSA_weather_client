package asla_client;

public class AppConstants {

    public static AppConstants instance = null;



    public static AppConstants getInstance() {
        if (instance == null) {
            instance = new AppConstants();
        }
        return instance;
    }

    public AppConstants() {
        System.out.println("AppConstant Runs");
    }
}




