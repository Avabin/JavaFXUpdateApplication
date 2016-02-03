package tk.avabin.JavaUpdater.uppcode.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by Avabin on 01.02.2016.
 */
public class FileDownloader {
    public FileDownloader(){}

    public File downloadFileFromURL(URL URLToFile, String fileName) throws IOException {
        File outputFile = new File(fileName);
        System.out.println("Downoading "+fileName+" from "+URLToFile);
        ReadableByteChannel in = Channels.newChannel(URLToFile.openStream());
        FileChannel out = new FileOutputStream(outputFile).getChannel();
        out.transferFrom(in, 0, Long.MAX_VALUE);
        return outputFile;
    }
}
