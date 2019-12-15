package updater;

import gui.Controller;
import updater.utils.settings.DownloadLinks;
import updater.utils.tools.Downloader;
import updater.utils.tools.FileInstaller;
import updater.utils.tools.Unzipper;

import java.io.File;
import java.net.UnknownHostException;

public class Updater {

    private static String programToUpdate;
    private static Controller controller;

    public Updater() {
        // Blank constructor
    }

    public Updater(String programParam, Controller controllerParam) {
        programToUpdate = programParam;
        controller = controllerParam;
    }

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
            controller.updateStatus(0.1, "Connecting to update server...");
            Thread.sleep(500);

            controller.updateStatus(0.2, "Downloading updates...");
            new Downloader().downloadFile(new DownloadLinks().getDownloadLink(), programDirectory + upgradeFolder);

            controller.updateStatus(0.4, "Unpacking files...");
            new Unzipper().unzip(new File("").getAbsolutePath() + upgradeFolder + new DownloadLinks().getDownloadedFileName(), programDirectory + upgradeFolder);

            controller.updateStatus(0.6, "Installing update...");
            new FileInstaller().installFiles(new File("").getAbsolutePath() + upgradeFolder, programDirectory);

            controller.updateStatus(0.8, "Cleaning Up...");
            new FileInstaller().deleteDirectory(new File(programDirectory + upgradeFolder));

            controller.updateStatus(1, "Done!");
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
