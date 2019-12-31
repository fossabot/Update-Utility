package updater.utils.tools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    public void installFiles(String updateDirectory, String programDirectory) throws Exception {
        File[] content = new File(updateDirectory).listFiles();

        assert content != null;
        for (File file : content) {
            if (!file.getName().contains("zip")) { // Don't move zip file
                if (System.getProperty("os.name").contains("Windows"))
                    this.moveFile(file.getAbsolutePath(), programDirectory + "\\" + file.getName());

                else if (System.getProperty("os.name").contains("Linux"))
                    this.moveFile(file.getAbsolutePath(), programDirectory + "/" + file.getName());
            }
        }
    }

    private void moveFile(String source, String destination) throws Exception {
        if (this.dirExists(destination))
            new File(destination).delete();

        Files.move(Paths.get(source), Paths.get(destination));
    }

    public boolean dirExists(String dir) {
        return new File(dir).exists();
    }

    public void deleteDirectory(File outputFolder) {
        File[] files = outputFolder.listFiles();

        assert files != null;
        for (File f : files) f.delete();

        outputFolder.delete();
    }

}
