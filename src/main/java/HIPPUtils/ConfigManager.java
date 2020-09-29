package HIPPUtils;
/**
 * @author Mohit Gupta
 */

import automation.base.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    private static ConfigManager instance;
    private static final Logger logger = LogManager.getLogger();
    private HashMap<String, String> configValues = new HashMap();
    private HashMap<String, String> framework = new HashMap();
    private static final String CONFIG = "configFilePath";
    private static final String FILE = "configuration.xml";
    private String errorMsg = "Duplicate Keys found in configuration:\n";

    public static ConfigManager instance() {
        if (instance == null) {
            instance = new ConfigManager();
        }

        return instance;
    }

    private ConfigManager() {
        this.init();
    }

    private void init() {
        try {
            String filePath = System.getProperty("configFilePath") == null ? "configuration.xml" : System.getProperty("configFilePath");
            Helper.checkExists((new File(filePath)).exists(), "cannot find config file {" + filePath + "}");
            this.loadUserConfig(filePath);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public String getByKey(String key) {
        return (String) this.configValues.getOrDefault(key, "");
    }

    public String getFrameworkSettings(String key) {
        return (String) this.framework.getOrDefault(key, "");
    }

    private void loadUserConfig(String filePath) {
        SAXBuilder builder = new SAXBuilder();

        try {
            Document xml = builder.build(new File(filePath));
            this.load(xml, this.configValues);
        } catch (IOException | JDOMException var4) {
            throw new RuntimeException(var4);
        }
    }

/*    private void loadFrameworkConfig(InputStream inputStream) {
        SAXBuilder builder = new SAXBuilder();

        try {
            Document xml = builder.build(inputStream);
            this.load(xml, this.framework);
        } catch (IOException | JDOMException var4) {
            throw new RuntimeException("framework settings error, please contact framework team");
        }
    }*/

    private void load(Document xmlFile, HashMap<String, String> handle) {
        Element rootNode = xmlFile.getRootElement();
        List<Element> list = rootNode.getChildren("appSettings");
        if (list.size() != 0) {
            Iterator var5 = ((Element) list.get(0)).getChildren("add").iterator();

            while (var5.hasNext()) {
                Element e = (Element) var5.next();
                String key = e.getAttributeValue("key");
                String value = e.getAttributeValue("value");
                if (handle.containsKey(key)) {
                    this.errorMsg = this.errorMsg + "{" + key + "}";
                    logger.error(this.errorMsg);
                    throw new RuntimeException(this.errorMsg);
                }

                handle.put(key, value);
            }

        }
    }

    public Map<String, String> getUserConfig() {
        return this.configValues;
    }

    public Map<String, String> getSystemConfig() {
        return this.framework;
    }
}
