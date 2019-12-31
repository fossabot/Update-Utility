package updater;

import gui.Controller;
import updater.utils.settings.DownloadLinks;
import updater.utils.tools.Downloader;
import updater.utils.tools.FileManager;
import updater.utils.tools.Unzipper;

import java.io.File;
import java.net.UnknownHostException;

public class Updater {

    private static String programToUpdate;
    private static Controller controller;

    public void setProgramToUpdate(String a) {
        programToUpdate = a;
    }

    public void setController(Controller a) {
        controller = a;
    }

    public boolean isControllerLoaded() {
        return controller != null;
    } // Returns true if loaded.

    public String getProgram() {
        if (programToUpdate.equalsIgnoreCase("edge-eye"))
            return "Edge Eye";

        return null;
    }

    public void startUpdate() {
        String programDirectory = new File("").getAbsolutePath();
        String upgradeFolder = null;

        if (System.getProperty("os.name").contains("Windows"))
            upgradeFolder = "\\Upgrade\\";

        else if (System.getProperty("os.name").contains("Linux"))
            upgradeFolder = "/Upgrade/";

        try {
            controller.updateStatus(0.1, "Connecting to update server");
            Thread.sleep(2000);

            controller.updateStatus(0.2, "Downloading updates");
            new Downloader().downloadFile(new DownloadLinks().getDownloadLink(), programDirectory + upgradeFolder);

            controller.updateStatus(0.4, "Unpacking files");
            new Unzipper().unzip(programDirectory + upgradeFolder + new DownloadLinks().getDownloadedFileName(), programDirectory + upgradeFolder);
            Thread.sleep(500);

            FileManager fileManager = new FileManager();

            controller.updateStatus(0.6, "Installing update");
            fileManager.installFiles(programDirectory + upgradeFolder, programDirectory);
            Thread.sleep(500);

            controller.updateStatus(0.8, "Cleaning Up");
            fileManager.deleteDirectory(new File(programDirectory + upgradeFolder));
            Thread.sleep(1000);

            controller.updateStatus(1, "Done!");
            Thread.sleep(500);

            controller.successfulUpgrade();
        } catch (UnknownHostException h) {
            controller.displayUpgradeError("Unable to connect to update server.");
            h.printStackTrace();
        } catch (Exception e) {
            controller.displayUpgradeError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
