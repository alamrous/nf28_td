import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

class RootViewElement extends GridPane {

    private ApplicationController controller;

    private TextField textField;
    private Slider slider;
    private Button startButton;
    private Button stopButton;

    private ImageView imageView;

    RootViewElement() {

        BorderPane mainBorderPane = new BorderPane();
        mainBorderPane.setPadding(new Insets(20));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col1.setMinWidth(600);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(100);

        this.getColumnConstraints().addAll(col1, col2);
        this.getRowConstraints().add(row1);

        /* ------------------ */
        /* Add mainBorderPane */
        /* ------------------ */

        VBox controlsBox = new VBox();
        controlsBox.setSpacing(40);
        controlsBox.setAlignment(Pos.CENTER);

        // Ajout textField + Label
        Label label = new Label("Intervalle\n(millis) :");
        label.setMinWidth(100);

        this.textField = new TextField();
        textField.setMinWidth(100);

        HBox TextContainer = new HBox();
        TextContainer.setSpacing(5);
        TextContainer.setMinWidth(100);
        TextContainer.setPadding(new Insets(10));
        TextContainer.setAlignment(Pos.CENTER);
        TextContainer.setBackground(new Background(new BackgroundFill(Color.web("#" + "E8E8E8"), new CornerRadii(15), Insets.EMPTY)));
        TextContainer.getChildren().addAll(label, this.textField);

        // Ajout boutons
        startButton = new Button("Start");
        startButton.setPadding(new Insets(6));
        startButton.setMinWidth(80);
        startButton.setDisable(true);

        stopButton = new Button("Stop");
        stopButton.setPadding(new Insets(6));
        stopButton.setMinWidth(80);
        stopButton.setDisable(true);

        HBox ButtonContainer = new HBox();
        ButtonContainer.setSpacing(20);
        ButtonContainer.setAlignment(Pos.CENTER);
        ButtonContainer.getChildren().addAll(startButton, stopButton);

        // Ajout slider
        slider = new Slider(0, 10, 0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(1);

        controlsBox.getChildren().addAll(TextContainer ,ButtonContainer, slider);

        mainBorderPane.setCenter(controlsBox);

        this.add(mainBorderPane, 0, 0);

        /* ------------- */
        /* Add imagePane */
        /* ------------- */

        // Add imageView
        imageView = new ImageView("file:image/image0.jpg");
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        StackPane imagePane = new StackPane();
        imagePane.setPrefSize(400,300);
        imagePane.setBackground(new Background(new BackgroundFill(Color.web("#" + "E8E8E8"), CornerRadii.EMPTY, Insets.EMPTY)));
        imagePane.getChildren().add(imageView);

        StackPane.setAlignment(imageView, Pos.CENTER);

        this.add(imagePane, 1, 0);

        // Bindings
        slider.valueChangingProperty().addListener((obs, wasChanging, isnowChanging) -> controller.sliderValueHasChanged(isnowChanging, slider.getValue()));
        slider.valueProperty().addListener((obs, oldValue, newValue) -> controller.sliderValueHasChanged(slider.isValueChanging(), newValue.doubleValue()));

        textField.setOnAction(evt -> controller.textFieldValueHasChanged(textField.getText(), true));

        startButton.setOnAction(evt -> controller.beginTimer());
        stopButton.setOnAction(evt -> controller.endTimer());
    }

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

    void setTextField(String str) {
        textField.setText(str);
    }


    void setController(ApplicationController controller) {
        this.controller = controller;
    }

    void updateTimerControlsOnAction(boolean timerIsRunning) {
        this.startButton.setDisable(timerIsRunning);
        this.slider.setDisable(timerIsRunning);
        this.textField.setDisable(timerIsRunning);

        this.stopButton.setDisable(!timerIsRunning);
    }

    void disableStartButton() {
        this.startButton.setDisable(true);
    }
}
