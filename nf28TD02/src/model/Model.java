package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.property.*;
import javafx.scene.image.Image;

public class Model {

    private ArrayList<String> imageNames = null;

    private DoubleProperty interval;
    private BooleanProperty timerIsOver;
    private ObjectProperty<Image> image;

    private int indexImage;
    private Timer timer;

    class ImageTimerTask extends TimerTask {

        @Override
        public void run() {
            if (indexImage >= imageNames.size() - 2) {
                indexImage = 0;
                stopTimer();
                return;
            }
            changeImage();
        }
    }


    public Model() {
        interval = new SimpleDoubleProperty();
        image = new SimpleObjectProperty<>();
        timerIsOver = new SimpleBooleanProperty(true);
        initializeImages();
        indexImage = 0;
    }

    private void changeImage() {
        indexImage++;
        System.out.println(getCurrentImageName());
        image.setValue(new Image("file:image/" + getCurrentImageName()));
    }

    public void startTimer(long periodms) {
        timer = new Timer();
        ImageTimerTask task = new ImageTimerTask();
        timer.schedule(task, 0, periodms);
        timerIsOver.setValue(false);
    }

    public void stopTimer() {
        indexImage = 0;
        image.setValue(new Image("file:image/" + getCurrentImageName()));
        timerIsOver.setValue(true);
        timer.cancel();
    }


    private void initializeImages() {
        try {
            File f = new File("image");
            if (f.list() == null) {
                throw new FileNotFoundException("Could not find files in image dir");
            }

            imageNames = new ArrayList<>(Arrays.asList(f.list()));
            for (String imageName : imageNames) {
                System.out.println("Nom : " + imageName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentImageName() {
        return imageNames.get(indexImage);
    }


    /************************************
     * Properties getters
     ***********************************/

    public DoubleProperty intervalProperty() {
        return interval;
    }

    public ObjectProperty<Image> imageObjectProperty() {
        return image;
    }

    public BooleanProperty timerIsOverBooleanProperty() {
        return timerIsOver;
    }

}
