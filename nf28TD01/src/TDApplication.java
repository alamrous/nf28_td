import controller.ApplicationController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.RootViewElement;

public class TDApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Affichage d'images");

        RootViewElement root = new RootViewElement();

        ApplicationController controller = new ApplicationController(root);

        // 3. binding View -> Controller
        root.setController(controller);

        // 4. binding model.Model -> Controller -> view
        controller.initialize();
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
