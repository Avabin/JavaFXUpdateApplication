package tk.avabin.JavaUpdater.uppcode.config;

import tk.avabin.JavaUpdater.uppcode.Update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Created by Avabin on 03.02.2016.
 */
public class PropertiesUtils {
    private Properties properties;
    private File propertiesFile;
    private Update update;
    public PropertiesUtils() {}
    public PropertiesUtils(File propertiesFile) {
        properties = new Properties();
        this.propertiesFile = propertiesFile;
    }
    public PropertiesUtils(String pathToPropertiesFile) {
        properties = new Properties();
        propertiesFile = new File(pathToPropertiesFile);
    }

    public String getPropertyByKey (String propertyKey, String pathToPropertyFile) throws IOException {
        String value;
        loadPropFile(pathToPropertyFile);
        value = properties.getProperty(propertyKey);
        return value;
    }
    public String getPropertyByKey (String propertyKey) throws IOException {
        return getPropertyByKey(propertyKey, propertiesFile.getAbsolutePath());
    }

    public void printPropertiesFromFile(String pathToPropertyFile) throws IOException {
        properties.load(new FileInputStream(pathToPropertyFile));
        properties.list(System.out);
    }
    public void printPropertiesFromFile() throws IOException {
        printPropertiesFromFile(propertiesFile.getAbsolutePath());
    }

    private void loadPropFile(File propertiesFile) throws IOException {
        try {
            properties.load(new FileInputStream(propertiesFile));
        } catch (IOException e) {
            update = new Update();
            System.out.println("LOCAL CONFIG FILE NOT FOUND, DOWNLOADING SERVER CONFIG");
            update.updateConfig();
            propertiesFile = new File(System.getenv("APPDATA") +
                    "/.minecraft/config.properties");
            e.printStackTrace();
        }
        properties.load(new FileInputStream(propertiesFile));
    }
    private void loadPropFile(String pathToPropertiesFile) throws IOException {
        loadPropFile(new File(pathToPropertiesFile));
    }
}
