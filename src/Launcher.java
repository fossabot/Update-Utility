import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import updater.Updater;

public class Launcher extends Application {

    /*
     * Program Shortcodes:
     * Edge Eye - edge-eye (new Updater().getProgram() returns "Edge Eye"
     */

    private static String programToUpdate;

    public static void main(String[] args) {
        programToUpdate = args[0];
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/interface.fxml"));
        Parent root = loader.load();

        new Updater(programToUpdate, loader.getController());

        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.getIcons().add(new Image("file:src/gui/img/program-icon.png"));
        primaryStage.setTitle("Update Utility");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
