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

        stage.setTitle("Affichage d'images");

        Scene scene = new Scene(root, 600, 300);
        stage.setScene(scene);
        stage.setTitle("Affichage d'images");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
