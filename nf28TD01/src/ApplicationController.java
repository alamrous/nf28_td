

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ApplicationController {

	private RootViewElement view;
	private Model modele;


	public ApplicationController(RootViewElement root) {
		this.view = root;
	}

	public void initialize() {
		this.modele = new Model();
		modele.intervalleProperty().addListener((obs, oldv, newv) -> view.updateTextField(newv));

		//TODO : créer le modèle
	}
	public void sliderValueHasChanged(boolean isNowChanging, double newvalue){
		if(!isNowChanging)
		{
			System.out.println("VALUE HAS CHANGED! : " + newvalue);
			modele.intervalleProperty().setValue(newvalue);


		}
//TODO : a faire
	}



}
