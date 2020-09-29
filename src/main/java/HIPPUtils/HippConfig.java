package HIPPUtils;

/**
 * @author Mohit Gupta
 */
public class HippConfig {
    private static HippConfig instance = null;
    private ConfigManager manager = ConfigManager.instance();

    public HippConfig() {
    }

    public static synchronized HippConfig instance() {
        if (instance == null) {
            instance = new HippConfig();
        }

        return instance;
    }

    public String getEnv() {
        return this.getConfig("HIPP_TEST");
    }

    public String getConfig(String configName) {
        return this.manager.getByKey(configName);
    }

    private static String getEnvConfig(String configName) {
        HippConfig hippConfig = new HippConfig();
        if (hippConfig.getConfig(configName).isEmpty())
            throw new RuntimeException("User configuration " + configName + " is missing in configuration file. " +
                    "Please add configuration in the file in order to run tests using the user");
        else
            return hippConfig.getConfig(configName);
    }

}
