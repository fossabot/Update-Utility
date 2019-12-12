package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import updater.Updater;

public class Controller {

    @FXML
    private Pane installingUpdatePane;

    @FXML
    private Pane successInstallPane;

    @FXML
    private Pane errorInstallPane;

    @FXML
    private Label errorMessageLabel;

    @FXML
    private Label title;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label statusText;

    @FXML
    private Label percent;

    @FXML
    private Button exitButton;

    @FXML
    public void initialize() {
        new Thread(() -> {
            Updater updater = new Updater();
            title.setText(updater.getProgram() + " Software Update");
            updater.startUpdate();
        }).start();
    }

    @FXML
    public void relaunchProgram() {
        this.exit();
    }

    public void updateStatus(double percent, String detail) {
        this.progressBar.setProgress(percent);

        Platform.runLater(() -> {
            this.percent.setText(((int) (this.progressBar.getProgress() * 100)) + "%");
            this.statusText.setText(detail);
        });
    }

    public void successfulUpgrade() {
        this.installingUpdatePane.setVisible(false);
        this.successInstallPane.setVisible(true);
    }

    public void displayUpgradeError(String errorMessage) {
        this.installingUpdatePane.setVisible(false);

        Platform.runLater(() -> {
            this.errorMessageLabel.setText("Error Message: " + errorMessage);
        });

        this.errorInstallPane.setVisible(true);
    }

    public void manualUpgrade() {

    }

    public void exit() {
        Stage stage = (Stage) this.exitButton.getScene().getWindow();
        stage.close();
    }


}
