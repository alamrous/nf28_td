package controller;

import model.Model;
import view.RootViewElement;

public class ApplicationController {

    private RootViewElement view;
    private Model modele;


    public ApplicationController(RootViewElement root) {
        this.view = root;
    }

    public void initialize() {
        this.modele = new Model();

        modele.intervalProperty().addListener((obs, oldv, newv) -> view.setInterval(newv));
        modele.imageObjectProperty().addListener((obs, oldv, newv) -> view.setImageView(newv));
        modele.timerIsOverBooleanProperty().addListener((obs, oldv, newv) -> view.updateTimerControlsOnAction(!newv));
    }

    public void sliderValueHasChanged(boolean isNowChanging, double newvalue) {
        if (!isNowChanging && modele.intervalProperty().getValue() != newvalue) {
//            System.out.println("Slider VALUE HAS CHANGED! : " + newvalue);
            modele.intervalProperty().setValue(newvalue);
        }
    }

    public void textFieldValueHasChanged(String newvalue, boolean fireStartButton) {
        int newVal = (newvalue.isEmpty() || !newvalue.matches("\\d+")) ? 0 : Integer.parseInt(newvalue);
//        System.out.println("TextField VALUE HAS CHANGED! : " + newvalue);

        if (newVal == 0) {
            view.setTextField("0");
            view.disableStartButton();
        }

        newVal = Math.max(Math.min(newVal, 10000), 0);
        modele.intervalProperty().setValue(((double) newVal) / 1000.0);

        if (fireStartButton) beginTimer();
    }

    public void beginTimer() {
        textFieldValueHasChanged(view.getTextField().getText(), false);
        if (modele.intervalProperty().getValue() != 0.0)
            modele.startTimer((long) (modele.intervalProperty().getValue() * 1000));
    }


    public void endTimer() {
        modele.stopTimer();
    }

}
