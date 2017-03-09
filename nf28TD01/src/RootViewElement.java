


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import sun.applet.Main;
import javafx.scene.control.Label ;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class RootViewElement extends GridPane {

	private ApplicationController controller;

	private BorderPane MainBorderPane;
	private ImageView ImageView;
	private Button StartButton;
	private Button StopButton;
	private Label Label;
	private TextField TextField;
	private Slider Slider;


	public  RootViewElement() {
		this.MainBorderPane = new BorderPane();
		this.setGridLinesVisible(true);

	     ColumnConstraints col1 = new ColumnConstraints();
	     col1.setPercentWidth(50);
	     ColumnConstraints col2 = new ColumnConstraints();
	     col2.setPercentWidth(50);
//	     col2.fillWidthProperty()
	     RowConstraints row1 = new RowConstraints();
	     row1.setPercentHeight(100);

	     this.getColumnConstraints().addAll(col1,col2);
	     this.getRowConstraints().add(row1);
		this.ImageView = new ImageView("file:image/image0.jpg");
		this.ImageView.setFitHeight(200);
		this.ImageView.setFitWidth(200);
		//Ajout boutons
		HBox ButtonContainer = new HBox();
		this.StartButton= new Button("Start");
		this.StopButton= new Button("Stop");
	     ButtonContainer.getChildren().addAll(this.StartButton,this.StopButton);
		MainBorderPane.setCenter(ButtonContainer);

		//Ajout textField + Label
		this.Label=new Label("Intervalle(millis): ");
		this.TextField= new TextField();
		HBox TextContainer = new HBox();
		TextContainer.getChildren().addAll(this.Label,this.TextField);
		MainBorderPane.setTop(TextContainer);

		this.Slider = new Slider(0, 10, 0);
		this.Slider.setShowTickLabels(true);
		this.Slider.setShowTickMarks(true);
		this.Slider.setMajorTickUnit(1);
		this.Slider.setMinorTickCount(5);
		this.Slider.setBlockIncrement(1);
//Binding
		this.Slider.valueChangingProperty().addListener((obs, wasChanging, isnowChanging) -> controller.sliderValueHasChanged(isnowChanging,this.Slider.getValue()));
		this.Slider.valueProperty().addListener((obs, oldValue, newValue) -> controller.sliderValueHasChanged(this.Slider.isValueChanging(), newValue.doubleValue()));



		MainBorderPane.setBottom(this.Slider);

		this.add(this.MainBorderPane,0,0);
		this.add(this.ImageView,1,0);

	}

public void updateTextField(Number value) {
	int val = (int) (value.doubleValue() * 1000);
	this.TextField.setText(Integer.toString(val));
}

	public void setController(ApplicationController Controller) {
		this.controller = Controller;

	}
}
