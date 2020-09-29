package HIPPUtils;
/**
 * @author Mohit Gupta
 */

import java.util.HashMap;
import java.util.Map;

public enum User {
    INOVOICER1,
    INOVOICER2,
    PMA,
    ADMIN,
    INVALID;

    final Map<String, String> credentials = new HashMap<>();
    static final HippConfig hippConfig = HippConfig.instance();

    public Map<String, String> getCredentials() {
        switch (this) {
            case INOVOICER1:
                credentials.put("USERNAME", readCredentialsFromConfig("USERNAME"));
                credentials.put("PASSWORD", readCredentialsFromConfig("PASSWORD"));
                break;
            case INOVOICER2:
                credentials.put("USERNAME", readCredentialsFromConfig("INVOICER2_USERNAME"));
                credentials.put("PASSWORD", readCredentialsFromConfig("INVOICER2_PASSWORD"));
                break;
            case PMA:
                credentials.put("USERNAME", readCredentialsFromConfig("PMA_USERNAME"));
                credentials.put("PASSWORD", readCredentialsFromConfig("PMA_PASSWORD"));
                break;
            case INVALID:
                credentials.put("USERNAME", readCredentialsFromConfig("INVALID_USERNAME"));
                credentials.put("PASSWORD", readCredentialsFromConfig("INVALID_PASSWORD"));
                break;
            case ADMIN:
                credentials.put("USERNAME", readCredentialsFromConfig("ADMIN_USERNAME"));
                credentials.put("PASSWORD", readCredentialsFromConfig("ADMIN_PASSWORD"));
                break;
        }
        return credentials;
    }


    private static String readCredentialsFromConfig(String configName) {
        if (hippConfig.getConfig(configName).isEmpty())
            throw new RuntimeException("User configuration " + configName + " is missing in configuration file. " +
                    "Please add configuration in the file in order to run tests using the user");
        else
            return hippConfig.getConfig(configName);
    }

}
