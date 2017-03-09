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

    private TextField textField;
    private Slider slider;

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
        this.textField = new TextField();
        HBox TextContainer = new HBox();
        TextContainer.getChildren().addAll(label, this.textField);
        mainBorderPane.setTop(TextContainer);

        slider = new Slider(0, 10, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(1);

        //Bindings
        slider.valueChangingProperty().addListener((obs, wasChanging, isnowChanging) -> controller.sliderValueHasChanged(isnowChanging, slider.getValue()));
        slider.valueProperty().addListener((obs, oldValue, newValue) -> controller.sliderValueHasChanged(slider.isValueChanging(), newValue.doubleValue()));

        textField.setOnAction(evt -> controller.textFieldValueHasChanged(textField.getText()));



        mainBorderPane.setBottom(slider);

        this.add(mainBorderPane, 0, 0);
        this.add(imageView, 1, 0);

    }

    void updateIntervalle(Number value) {
//        System.out.println("1 " + value.doubleValue());
        Double val = (value.doubleValue() * 1000.0);
//        System.out.println("2 " + Math.round(val));
        textField.setText(Long.toString(Math.round(val)));
        slider.setValue(value.doubleValue());
    }

    void setController(ApplicationController Controller) {
        controller = Controller;
    }

}
