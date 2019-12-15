package updater.utils.tools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileInstaller {

    public void installFiles(String updateDirectory, String programDirectory) throws Exception {
        File[] content = new File(updateDirectory).listFiles();

        assert content != null;
        for (File file : content) {
            // Don't move zip file
            if (!file.getName().contains("zip")) {
                if (System.getProperty("os.name").contains("Windows"))
                    this.moveFile(file.getAbsolutePath(), programDirectory + "\\" + file.getName());

                else if (System.getProperty("os.name").contains("Linux"))
                    this.moveFile(file.getAbsolutePath(), programDirectory + "/" + file.getName());
            }
        }
    }

    private void moveFile(String source, String destination) throws Exception {
        Files.move(Paths.get(source), Paths.get(destination));
    }

    public boolean deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();

        if (allContents != null) {
            for (File file : allContents)
                this.deleteDirectory(file);
        }

        return directory.delete();
    }

}
