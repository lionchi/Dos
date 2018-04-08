package ru.gavrilov.protocols.entry;

import ru.gavrilov.protocols.ProtocolEnum;

import java.io.Serializable;

public class DosEntry implements Serializable {
    private ProtocolEnum protocol;
    private String host;
    private int port;
    private int threads;
    private String message;
    private int minutes;
    private int hours;
    private int timeoutAttack;
    private int socketTimeout;

    public DosEntry() {
        this(ProtocolEnum.TCP, "0.0.0.0", 0, 1, "Hello", 0, 5, 5000, 5000);
    }

    public DosEntry(ProtocolEnum protocol, String host, int port, int threads, String message, int minutes, int hours,
                    int timeoutAttack, int socketTimeout) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.threads = threads;
        this.message = message;
        this.minutes = minutes;
        this.hours = hours;
        this.timeoutAttack = timeoutAttack;
        this.socketTimeout = socketTimeout;
    }

    public ProtocolEnum getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolEnum protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getTimeoutAttack() {
        return timeoutAttack;
    }

    public void setTimeoutAttack(int timeoutAttack) {
        this.timeoutAttack = timeoutAttack;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
