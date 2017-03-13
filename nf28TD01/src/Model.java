import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class Model {

    private ArrayList<String> imageNames = null;
   	private DoubleProperty intervalle;
    private ObjectProperty<Image> image;
    private Timeline timer;
    private int index;


	Model() {
        super();
        intervalle = new SimpleDoubleProperty();
        image = new SimpleObjectProperty<>();
        initializeImages();
        index = 0;
        timer = new Timeline();
    }
	public void setTimer(Timeline timer){
		this.timer = timer;
	}
	public Timeline getTimer(){
		return timer;

	}

    public void setIndex(int index) {
		this.index = index;
	}
    DoubleProperty intervalleProperty() {
        return intervalle;
    }
   public int getCurrentImageViewIndex()
   {
	   return  index;
   }
    private void initializeImages() {
        File f = new File("image");
        imageNames = new ArrayList<>(Arrays.asList(f.list()));
        for (String imageName : imageNames) {
            System.out.println("Nom : " + imageName);
        }
    }
    public ArrayList<String> getImageNames() {
		return imageNames;
	}




}
