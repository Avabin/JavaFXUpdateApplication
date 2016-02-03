package tk.avabin.JavaUpdater.uppcode.FileUtils;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.core.ZipFile;

/**
 * Created by Avabin on 01.02.2016.
 */
public class FileUnzip {
    public FileUnzip() {}

    public void unzip(String source, String target, String password) throws ZipException {
            ZipFile zipFile = new ZipFile(source);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
        System.out.println("Extracting "+source+" to "+target);
            zipFile.extractAll(target);
    }
}
