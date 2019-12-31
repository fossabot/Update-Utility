import javafx.application.Application;
import javafx.event.Event;
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

    private static Updater updater = new Updater();

    public static void main(String[] args) {
        updater.setProgramToUpdate(args[0]);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/interface.fxml"));
        Parent root = loader.load();

        updater.setController(loader.getController());

        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.getIcons().add(new Image("file:src/gui/img/program-icon.png"));
        primaryStage.setTitle("Update Utility");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setOnCloseRequest(Event::consume); // Prevent GUI from being closed.
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
