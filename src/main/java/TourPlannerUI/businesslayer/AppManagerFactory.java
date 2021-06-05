package TourPlannerUI.businesslayer;

public final class AppManagerFactory {

    private static AppManager manager;

    public static  AppManager GetManager() {
        if (manager == null){
            manager = new AppManagerImpl();
        }
        return manager;
    }
}
