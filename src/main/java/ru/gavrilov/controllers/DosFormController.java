package ru.gavrilov.controllers;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.gavrilov.common.Controller;
import ru.gavrilov.common.StatusEnum;
import ru.gavrilov.protocols.FactoryDos;
import ru.gavrilov.protocols.ProtocolEnum;
import ru.gavrilov.protocols.entry.DosEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DosFormController implements Controller {

    @FXML
    private ComboBox<ProtocolEnum> protocols;
    @FXML
    private ProgressBar progressBar = new ProgressBar();
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
    private TextArea log = new TextArea();

    private int duration;
    private ScheduledExecutorService threadPool;
    private DosEntry dosEntry;
    private TimeChecker timeChecker;

    private final int initValue = 1;
    public static boolean stopThread = false;

    private DoubleProperty progress = new SimpleDoubleProperty(0.0);
    private ObservableList<ProtocolEnum> protocolEnums = FXCollections.observableArrayList();
    private Stage stage;

    @FXML
    private void initialize() {
        this.protocolEnums.addAll(ProtocolEnum.values());
        this.protocols.setItems(protocolEnums);
        this.startButton.setOnAction(event -> start());
        this.stopButton.setOnAction(event -> stopAll());
        this.stopButton.setDisable(true);
        this.initSpiner();
        this.status.setText(StatusEnum.OTHER.getStatus());
        this.progressBar.progressProperty().bind(progress);
        this.log.setEditable(false);
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

    private int createPattern() {
        this.dosEntry = new DosEntry();
        this.dosEntry.setMessage(this.message.getText());
        this.dosEntry.setHost(this.host.getText());
        this.dosEntry.setPort(Integer.parseInt(this.port.getText()));
        this.dosEntry.setProtocol(this.protocols.getValue());
        this.dosEntry.setHours(this.hour.getValue());
        this.dosEntry.setMinutes(this.minute.getValue());
        this.dosEntry.setThreads(this.threads.getValue());
        this.dosEntry.setSocketTimeout(this.timeoutSocket.getValue());
        this.dosEntry.setTimeoutAttack(this.timeoutAttack.getValue());

        return this.threads.getValue();
    }

    private void start() {
        stopThread = false;
        log.setText("");
        if (this.host.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Не указан хост!").showAndWait();
            //jTbStop.setSelected(true);
        } else {
            int threads = this.createPattern();
            duration = (this.dosEntry.getHours() * 3600) + (this.dosEntry.getMinutes() * 60);
            if (duration > 0) {
                this.status.setText(StatusEnum.ATTACK.getStatus());
                //this.progress.set(duration);
                this.stopButton.setDisable(false);
                threadPool = Executors.newScheduledThreadPool(threads);
                timeChecker = new TimeChecker();
                timeChecker.start();
                for (int i = 0; i < this.threads.getValue(); i++) {
                    threadPool.scheduleWithFixedDelay(FactoryDos.createDosAttack(dosEntry, this),
                            1, this.dosEntry.getTimeoutAttack(), TimeUnit.MILLISECONDS);
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Время должно быть больше 0 секунд!").showAndWait();
            }
        }
    }

    private void stopAll() {
        if (this.timeChecker != null) {
            this.timeChecker.interrupt();
        }
        this.progress.setValue(0);
        this.stopThread = true;
        if (this.threadPool != null) {
            this.threadPool.shutdownNow();
            this.threadPool = null;
        }
        this.status.setText(StatusEnum.STOP.getStatus());
        this.stopButton.setDisable(true);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void appendToLog(String str) {
/*        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        StringBuilder stringBuilder = new StringBuilder(dateFormat.format(date));
        stringBuilder.append(" " + str + "\n");
        Platform.runLater(() -> log.appendText(stringBuilder.toString()));*/
    }

    class TimeChecker extends Thread {
        @Override
        public void run() {
            while (!isInterrupted() && !stopThread && duration > 0) {
                try {
                    duration -= 1;
                    checkProgress(progress);
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    stopThread = true;
                    duration = 0;
                    ex.printStackTrace();
                    break;
                }
            }
            Platform.runLater(() -> {
                stopAll();
                status.setText(StatusEnum.FINISHED.getStatus());
            });
        }

        private void checkProgress(DoubleProperty progress) {
            if (1 < progress.getValue() || progress.getValue() == 1) {
                progress.setValue(0);
            } else {
                progress.setValue(progress.getValue() + 0.0164);
            }
        }
    }
}
