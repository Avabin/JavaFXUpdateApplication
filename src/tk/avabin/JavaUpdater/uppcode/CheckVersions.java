package tk.avabin.JavaUpdater.uppcode;

import tk.avabin.JavaUpdater.uppcode.FileUtils.FileDownloader;
import tk.avabin.JavaUpdater.uppcode.config.PropertiesUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Avabin on 01.02.2016.
 */
public class CheckVersions {
    public CheckVersions() {}

    public boolean checkVersion(boolean modsOrConfigs) throws IOException {
        String key;
        Update update = new Update();
        File propLocal = new File(System.getenv("APPDATA")+
                "/.minecraft/config.properties");
        File propServer = new File(Util.getCurrentWorkingDirectory().toString()+
                "/serverConfig.properties");
        if (!propLocal.exists()) {
            return false;
        }
        key = modsOrConfigs ? "ModsVersion" : "ConfigVersion";
        PropertiesUtils propertiesUtilsLocal = new PropertiesUtils(propLocal);
        PropertiesUtils propertiesUtilsServer = new PropertiesUtils(propServer);

        URL serverConfigURL = new URL(propertiesUtilsLocal.getPropertyByKey("ServerConfigFileURL"));
        FileDownloader fileDownloader = new FileDownloader();
        File serverConfig = fileDownloader.downloadFileFromURL(serverConfigURL, "serverConfig.properties");

        double localVersion = Double.parseDouble(propertiesUtilsLocal.getPropertyByKey(key));
        double serverVersion = Double.parseDouble(propertiesUtilsServer.getPropertyByKey(key));
        Util.removeFile(serverConfig);
        return localVersion == serverVersion;
    }
}
