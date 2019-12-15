package updater.utils.tools;

import updater.utils.settings.DownloadLinks;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Downloader {

    public void downloadFile(String link, String location) throws Exception {

        // Create update directory
        if (!Files.isDirectory(Paths.get(location)))
            (new File(location)).mkdirs();

        URL website = new URL(link);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(location + new DownloadLinks().getDownloadedFileName());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}
