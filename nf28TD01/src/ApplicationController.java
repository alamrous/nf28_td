import java.util.ArrayList;
import java.util.Objects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.util.Duration;

class ApplicationController {

    private RootViewElement view;
    private Model modele;


    ApplicationController(RootViewElement root) {
        this.view = root;
    }

    void initialize() {
        this.modele = new Model();
        modele.intervalleProperty().addListener((obs, oldv, newv) -> view.updateIntervalle(newv));

        //TODO : créer le modèle
    }

    void sliderValueHasChanged(boolean isNowChanging, double newvalue) {
        if (!isNowChanging && modele.intervalleProperty().getValue() != newvalue) {
//            System.out.println("Slider VALUE HAS CHANGED! : " + newvalue);
            modele.intervalleProperty().setValue(newvalue);
        }
    }

    void textFieldValueHasChanged(String newvalue) {
        int intervalleVal = (int)(modele.intervalleProperty().getValue() * 1000);
        int newVal = Integer.parseInt(newvalue);
        if (intervalleVal != newVal) {
//            System.out.println("TextField VALUE HAS CHANGED! : " + newvalue);
//            System.out.println("0 " + ((double)newVal)/1000.0);
            modele.intervalleProperty().setValue(((double)newVal)/1000.0);
        }
    }
    void beginTimer(){
    	view.getStopButton().setDisable(false);
    	ArrayList<String> imageList = modele.getImageNames();
    	Timeline timer = new Timeline(new KeyFrame(
    	        Duration.millis(modele.intervalleProperty().getValue() * 1000),
    	        ae -> switchPicture(imageList,modele.getCurrentImageViewIndex(),imageList.size())));
    	timer.setCycleCount(imageList.size());
    	timer.setOnFinished(e -> view.switchStopButton(true));
    	modele.setTimer(timer);
    	timer.play();

    	//timer.stop();
    }
    void switchPicture(ArrayList<String> imageList, int i, int size)
    {
    	i = i + 1;
    	if (i >= size) i = 0;
    	modele.setIndex(i);
    	System.out.println("file:image/"+imageList.get(i));
    	view.setImageViewValue("file:image/"+imageList.get(i));

    }

	 void endTimer() {
		// TODO Auto-generated method stub
modele.getTimer().stop();
	 	}

}
