package updater.utils.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        // Prevents zip slip venerability
        // https://snyk.io/research/zip-slip-vulnerability
        if (!destFilePath.startsWith(destDirPath + File.separator))
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());

        return destFile;
    }

    public void unzip(String zipFile, String destDir) throws Exception {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            File newFile = newFile(new File(destDir), zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;

            while ((len = zis.read(buffer)) > 0)
                fos.write(buffer, 0, len);

            fos.close();
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }

}
