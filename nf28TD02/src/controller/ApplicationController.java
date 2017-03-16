package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Model;

public class ApplicationController implements Initializable {
	
    private Model modele;
    
    @FXML
    private TextField textField;
    @FXML
    private Slider slider;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    @FXML
    private ImageView imageView;
    
    void setInterval(Number value) {
        Double val = (value.doubleValue() * 1000.0);
        setTextField(Long.toString(Math.round(val)));
        slider.setValue(value.doubleValue());
        startButton.setDisable(value.doubleValue() == 0.0);
    }
    
    void setImageView(Image image) {
        imageView.setImage(image);
    }

    TextField getTextField() {
        return textField;
    }

    void updateTimerControlsOnAction(boolean timerIsRunning) {
        this.startButton.setDisable(timerIsRunning);
        this.slider.setDisable(timerIsRunning);
        this.textField.setDisable(timerIsRunning);

        this.stopButton.setDisable(!timerIsRunning);
    }
    
    
    void setTextField(String str) {
        textField.setText(str);
    }
    void disableStartButton() {
        this.startButton.setDisable(true);
    }

    void sliderValueHasChanged(boolean isNowChanging, double newvalue) {
        if (!isNowChanging && modele.intervalProperty().getValue() != newvalue) {
//            System.out.println("Slider VALUE HAS CHANGED! : " + newvalue);
            modele.intervalProperty().setValue(newvalue);
        }
    }

    void textFieldValueHasChanged(String newvalue, boolean fireStartButton) {
    	int newVal = 0;
    	
    	try {
        newVal = (newvalue.isEmpty() || !newvalue.matches("\\d+")) ? 0 : Integer.parseInt(newvalue);
//        System.out.println("TextField VALUE HAS CHANGED! : " + newvalue);
    	} catch (NumberFormatException e) {
    		newVal = 10000;
    	}

        if (newVal == 0) {
            setTextField("0");
            disableStartButton();
        }

        newVal = Math.max(Math.min(newVal, 10000), 0);
        modele.intervalProperty().setValue(((double) newVal) / 1000.0);

        if (fireStartButton) beginTimer();
    }

    void beginTimer() {
        textFieldValueHasChanged(getTextField().getText(), false);
        if (modele.intervalProperty().getValue() != 0.0)
            modele.startTimer((long) (modele.intervalProperty().getValue() * 1000));
    }


    void endTimer() {
        modele.stopTimer();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
        this.modele = new Model();
        
        

        modele.intervalProperty().addListener((obs, oldv, newv) -> setInterval(newv));
        modele.imageObjectProperty().addListener((obs, oldv, newv) -> setImageView(newv));
        modele.timerIsOverBooleanProperty().addListener((obs, oldv, newv) -> updateTimerControlsOnAction(!newv));
        
        
        // Bindings
        slider.valueChangingProperty().addListener((obs, wasChanging, isnowChanging) -> sliderValueHasChanged(isnowChanging, slider.getValue()));
        slider.valueProperty().addListener((obs, oldValue, newValue) -> sliderValueHasChanged(slider.isValueChanging(), newValue.doubleValue()));

        textField.setOnAction(evt -> textFieldValueHasChanged(textField.getText(), true));

        startButton.setOnAction(evt -> beginTimer());
        stopButton.setOnAction(evt -> endTimer());
        
        
		
		
	}

}
