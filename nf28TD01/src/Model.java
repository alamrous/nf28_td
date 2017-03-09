import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

class Model {

    private ArrayList<String> imageNames = null;
    private DoubleProperty intervalle;
    private ObjectProperty<Image> image;

    Model() {
        super();
        intervalle = new SimpleDoubleProperty();
        image = new SimpleObjectProperty<>();
        initializeImages();
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
}
