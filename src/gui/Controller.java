package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import updater.Updater;

import java.io.File;

public class Controller {

    // Installing Pane
    @FXML
    private Pane installingUpdatePane;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label statusText;
    @FXML
    private Label percent;

    // Successful Update Pane
    @FXML
    private Pane successInstallPane;

    // Error Pane
    @FXML
    private Pane errorInstallPane;
    @FXML
    private Label errorMessageLabel;

    @FXML
    public void initialize() {
        new Thread(() -> {
            try {
                Updater updater = new Updater();

                while (!updater.isControllerLoaded())
                    Thread.sleep(1000); // Wait until GUI is loaded.

                updater.startUpdate();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    public void relaunchProgram() {
        String location = new File("").getAbsolutePath();

        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("powershell.exe " + ".\\Application.exe").start();

            // else if (System.getProperty("os.name").contains("Linux"))

            // else if (System.getProperty("os.name").contains("OSX"))

            this.exit();
        } catch (Exception e) {
            this.successInstallPane.setVisible(false);
            this.errorMessageLabel.setText(e.getMessage());
            this.errorInstallPane.setVisible(true);
        }
    }

    public void updateStatus(double percent, String detail) {
        this.progressBar.setProgress(percent);

        Platform.runLater(() -> {
            this.percent.setText(((int) (this.progressBar.getProgress() * 100)) + "% Complete");
            this.statusText.setText(detail);
        });
    }

    public void successfulUpgrade() {
        this.installingUpdatePane.setVisible(false);
        this.successInstallPane.setVisible(true);
    }

    public void displayUpgradeError(String errorMessage) {
        this.installingUpdatePane.setVisible(false);
        Platform.runLater(() -> this.errorMessageLabel.setText("Error Message: " + errorMessage));
        this.errorInstallPane.setVisible(true);
    }

    @FXML
    public void exit() {
        Stage stage = (Stage) this.installingUpdatePane.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

}
