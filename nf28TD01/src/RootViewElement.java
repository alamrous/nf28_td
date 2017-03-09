import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

class RootViewElement extends GridPane {

    private ApplicationController controller;

    private TextField TextField;
    private Slider Slider;

    RootViewElement() {
        BorderPane mainBorderPane = new BorderPane();
        this.setGridLinesVisible(true);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(100);

        this.getColumnConstraints().addAll(col1, col2);
        this.getRowConstraints().add(row1);
        ImageView imageView = new ImageView("file:image/image0.jpg");
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        //Ajout boutons
        HBox ButtonContainer = new HBox();
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        ButtonContainer.getChildren().addAll(startButton, stopButton);
        mainBorderPane.setCenter(ButtonContainer);

        //Ajout textField + Label
        javafx.scene.control.Label label = new Label("Intervalle(millis): ");
        this.TextField = new TextField();
        HBox TextContainer = new HBox();
        TextContainer.getChildren().addAll(label, this.TextField);
        mainBorderPane.setTop(TextContainer);

        this.Slider = new Slider(0, 10, 0);
        this.Slider.setShowTickLabels(true);
        this.Slider.setShowTickMarks(true);
        this.Slider.setMajorTickUnit(1);
        this.Slider.setMinorTickCount(5);
        this.Slider.setBlockIncrement(1);

        //Binding
        this.Slider.valueChangingProperty().addListener((obs, wasChanging, isnowChanging) -> controller.sliderValueHasChanged(isnowChanging, this.Slider.getValue()));
        this.Slider.valueProperty().addListener((obs, oldValue, newValue) -> controller.sliderValueHasChanged(this.Slider.isValueChanging(), newValue.doubleValue()));


        mainBorderPane.setBottom(this.Slider);

        this.add(mainBorderPane, 0, 0);
        this.add(imageView, 1, 0);

    }

    void updateTextField(Number value) {
        int val = (int) (value.doubleValue() * 1000);
        this.TextField.setText(Integer.toString(val));
    }

    void setController(ApplicationController Controller) {
        this.controller = Controller;
    }

}
