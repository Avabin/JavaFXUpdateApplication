package tk.avabin.JavaUpdater.uppcode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Avabin on 03.02.2016.
 */
public class Util {

    public static void removeFile(File fileToRemove) throws IOException {
        System.out.println("Removing "+fileToRemove.getAbsolutePath());
        System.gc();
        fileToRemove.delete();
    }
    public static Path getCurrentWorkingDirectory() {
        return Paths.get(".").toAbsolutePath().normalize();
    }
}
