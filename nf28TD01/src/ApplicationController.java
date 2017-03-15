class ApplicationController {

    private RootViewElement view;
    private Model modele;


    ApplicationController(RootViewElement root) {
        this.view = root;
    }

    void initialize() {
        this.modele = new Model();

        modele.intervalProperty().addListener((obs, oldv, newv) -> view.setInterval(newv));
        modele.imageObjectProperty().addListener((obs, oldv, newv) -> view.setImageView(newv));
        modele.timerIsOverBooleanProperty().addListener((obs, oldv, newv) -> view.updateTimerControlsOnAction(!newv));
    }

    void sliderValueHasChanged(boolean isNowChanging, double newvalue) {
        if (!isNowChanging && modele.intervalProperty().getValue() != newvalue) {
//            System.out.println("Slider VALUE HAS CHANGED! : " + newvalue);
            modele.intervalProperty().setValue(newvalue);
        }
    }

    void textFieldValueHasChanged(String newvalue, boolean fireStartButton) {
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

    void beginTimer() {
        textFieldValueHasChanged(view.getTextField().getText(), false);
        if (modele.intervalProperty().getValue() != 0.0)
            modele.startTimer((long) (modele.intervalProperty().getValue() * 1000));
    }


    void endTimer() {
        modele.stopTimer();
    }

}
