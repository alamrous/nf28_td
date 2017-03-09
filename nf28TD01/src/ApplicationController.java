import java.util.Objects;

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

}
