package ru.gavrilov.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import ru.gavrilov.common.Controller;
import ru.gavrilov.common.StatusEnum;
import ru.gavrilov.protocols.ProtocolEnum;

public class DosFormController implements Controller {

    @FXML
    private ComboBox<ProtocolEnum> protocols;
    @FXML
    private ProgressBar progressBar = new ProgressBar(0);
    @FXML
    private Label status;

    @FXML
    private Spinner<Integer> hour = new Spinner<>();
    @FXML
    private Spinner<Integer> minute = new Spinner<>();
    @FXML
    private Spinner<Integer> threads = new Spinner<>();
    @FXML
    private Spinner<Integer> timeoutAttack = new Spinner<>();
    @FXML
    private Spinner<Integer> timeoutSocket = new Spinner<>();

    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;

    @FXML
    private TextField host;
    @FXML
    private TextField port;
    @FXML
    private TextField message;
    @FXML
    private TextArea log;

    private final int initValue = 1;
    private DoubleProperty progress = new SimpleDoubleProperty(0.0);
    private ObservableList<ProtocolEnum> protocolEnums = FXCollections.observableArrayList();
    private Stage stage;

    @FXML
    private void initialize() {
        this.protocolEnums.addAll(ProtocolEnum.values());
        this.protocols.setItems(protocolEnums);
        this.startButton.setOnAction(event -> start());
        this.stopButton.setOnAction(event -> stopAll());
        this.initSpiner();
        this.status.setText(StatusEnum.OTHER.getStatus());
        this.progressBar.progressProperty().bind(progress);
    }

    private void initSpiner() {
        SpinnerValueFactory<Integer> valueFactoryForThreads = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, this.initValue);
        SpinnerValueFactory<Integer> valueFactoryForTimeoutAttack = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, this.initValue);
        SpinnerValueFactory<Integer> valueFactoryForTimeoutSocket = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, this.initValue);
        SpinnerValueFactory<Integer> valueFactoryForHour = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24);
        SpinnerValueFactory<Integer> valueFactoryForMinute = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, this.initValue);

        this.hour.setValueFactory(valueFactoryForHour);
        this.minute.setValueFactory(valueFactoryForMinute);
        this.threads.setValueFactory(valueFactoryForThreads);
        this.timeoutAttack.setValueFactory(valueFactoryForTimeoutAttack);
        this.timeoutSocket.setValueFactory(valueFactoryForTimeoutSocket);
    }

    private void start(){

    }

    private void stopAll(){

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public double getProgress() {
        return progress.get();
    }

    public DoubleProperty progressProperty() {
        return progress;
    }

    public TextField getHost() {
        return host;
    }

    public TextField getPort() {
        return port;
    }

    public TextField getMessage() {
        return message;
    }

    public Spinner<Integer> getHour() {
        return hour;
    }

    public Spinner<Integer> getMinute() {
        return minute;
    }

    public Spinner<Integer> getThreads() {
        return threads;
    }

    public Spinner<Integer> getTimeoutAttack() {
        return timeoutAttack;
    }

    public Spinner<Integer> getTimeoutSocket() {
        return timeoutSocket;
    }

    public TextArea getLog() {
        return log;
    }

    public void setLog(TextArea log) {
        this.log = log;
    }
}
