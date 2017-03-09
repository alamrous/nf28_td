import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

class Model {

    private ArrayList<String> imageNames = null;
    private DoubleProperty intervalle;
    private ObjectProperty<Image> image;
    private Timer timer;

    Model() {
        super();
        intervalle = new SimpleDoubleProperty();
        image = new SimpleObjectProperty<>();
        initializeImages();
        timer = new Timer();
    }

    DoubleProperty intervalleProperty() {
        return intervalle;
    }

    private void initializeImages() {
        File f = new File("image");
        imageNames = new ArrayList<>(Arrays.asList(f.list()));
        for (String imageName : imageNames) {
            System.out.println("Nom : " + imageName);
        }
    }

    class ImageTimerTask extends TimerTask {

        @Override
        public void run() {
            changeImage();
        }
    }

    private void changeImage() {

        ImageTimerTask task = new ImageTimerTask();
        timer.schedule(task, 0, intervalle.longValue());

    }
}
