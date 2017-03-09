class ApplicationController {

    private RootViewElement view;
    private Model modele;


    ApplicationController(RootViewElement root) {
        this.view = root;
    }

    void initialize() {
        this.modele = new Model();
        modele.intervalleProperty().addListener((obs, oldv, newv) -> view.updateTextField(newv));

        //TODO : créer le modèle
    }

    void sliderValueHasChanged(boolean isNowChanging, double newvalue) {
        if (!isNowChanging) {
            System.out.println("VALUE HAS CHANGED! : " + newvalue);
            modele.intervalleProperty().setValue(newvalue);


        }

        //TODO : à faire
    }

}
