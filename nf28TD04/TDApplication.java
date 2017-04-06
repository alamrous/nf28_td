import controller.ApplicationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TDApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/interface.fxml"));

        Pane root = fxmlLoader.load();

        ((ApplicationController) fxmlLoader.getController()).setStage(stage);

        Scene scene = new Scene(root, 750, 495);
        stage.setScene(scene);
        stage.setTitle("TD04 - Liste de contacts");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
