

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Model {

private ArrayList<String> imageNames = null;
 private DoubleProperty intervalle;
 private ObjectProperty<Image> image;
//	private StringProperty TextFieldProperty;

 public Model() {
		super();
		// TODO Auto-generated constructor stub
		intervalle = new SimpleDoubleProperty();
		image = new SimpleObjectProperty<>();
	}

 public DoubleProperty intervalleProperty() {
	 return intervalle;
 }

 private void initializeImages(){
	 File f = new File("image");
	 imageNames = new ArrayList<>(Arrays.asList(f.list()));
 }
}
