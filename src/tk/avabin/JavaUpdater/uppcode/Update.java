package tk.avabin.JavaUpdater.uppcode;

import net.lingala.zip4j.exception.ZipException;
import tk.avabin.JavaUpdater.uppcode.FileUtils.FileDownloader;
import tk.avabin.JavaUpdater.uppcode.FileUtils.FileUnzip;
import tk.avabin.JavaUpdater.uppcode.config.PropertiesUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Avabin on 01.02.2016.
 */
public class Update {
    private PropertiesUtils propertiesUtils;
    private FileDownloader fileDownloader;
    public Update() {

    }

    public void updateConfig() throws IOException {
        File oldConfig = new File(System.getenv("APPDATA")+"/.minecraft/config.properties");
        propertiesUtils = new PropertiesUtils(oldConfig);
        URL serverConfigURL = new URL("http://avabin.tk/updater/config.properties");
        fileDownloader = new FileDownloader();
        File newConfig = fileDownloader.downloadFileFromURL(serverConfigURL, "newconfig.properties");
        Util.removeFile(oldConfig);
        Files.copy(Paths.get(newConfig.getAbsolutePath()),
                Paths.get(System.getenv("APPDATA")+
                        "/.minecraft/config.properties"), REPLACE_EXISTING);
        Util.removeFile(newConfig);
        propertiesUtils = null;
        fileDownloader = null;
    }

    public void updatePack() throws IOException, ZipException {
        FileUnzip fileUnzip = new FileUnzip();
        propertiesUtils = new PropertiesUtils(new File(System.getenv("APPDATA")+
                "/.minecraft/config.properties"));
        fileDownloader = new FileDownloader();
        URL updateFileURL = new URL(propertiesUtils.getPropertyByKey("UpdateFileURL"));
        File updateFileZip;

        updateFileZip = fileDownloader.downloadFileFromURL(updateFileURL, "update.zip");
        System.out.println("Unzipping update file ato mods folder");
        fileUnzip.unzip(Util.getCurrentWorkingDirectory().toString()+"/update.zip",
                Util.getCurrentWorkingDirectory().toString()+"update", "");

        String[] oldMOds = propertiesUtils.getPropertyByKey("OldMods").split(",");
        for (String s : oldMOds) {
            System.out.println(s);
            File toRemove = new File(System.getenv("APPDATA")+"/.minecraft/mods/"+s);
            try {
                Util.removeFile(toRemove);
            } catch (FileNotFoundException e) {
                System.out.println("File not found, but that's ok.");
                e.printStackTrace();
            }
            Files.walk(Paths.get(Util.getCurrentWorkingDirectory().toString()+"update")).forEach(path -> {
                try {
                    Files.copy(path, Paths.get(System.getenv("APPDATA")+"/.minecraft/mods/"+path.getFileName()), REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


    }
}
