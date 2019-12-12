package updater;

import gui.Controller;
import updater.utils.settings.DownloadLinks;
import updater.utils.tools.Downloader;
import updater.utils.tools.FileInstaller;
import updater.utils.tools.Unzipper;

import java.io.File;

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
        try {
            controller.updateStatus(0.1, "Connecting to update server...");
            Thread.sleep(500);

            controller.updateStatus(0.2, "Downloading updates...");
            new Downloader().downloadFile(new DownloadLinks().getDownloadLink(), new File("").getAbsolutePath() + "\\Upgrade\\");

            controller.updateStatus(0.4, "Unpacking files...");
            new Unzipper().unzip(new File("").getAbsolutePath() + new DownloadLinks().getDownloadedFileName(), new File("").getAbsolutePath() + "\\Upgrade\\");

            controller.updateStatus(0.6, "Installing update...");
            new FileInstaller().installFiles(new File("").getAbsolutePath() + "\\Upgrade\\", new File("").getAbsolutePath());

            controller.updateStatus(0.8, "Cleaning Up...");
            new FileInstaller().cleanUpUpdateDir();

            controller.updateStatus(1, "Done!");
            controller.successfulUpgrade();
        } catch (Exception e) {
            controller.displayUpgradeError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
