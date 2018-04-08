package ru.gavrilov.protocols.abstracts;

import ru.gavrilov.common.Controller;
import ru.gavrilov.protocols.entry.DosEntry;

import java.net.SocketAddress;

public abstract class DOS implements Runnable {

    private DosEntry dosEntry;
    private SocketAddress address;
    private Controller controller;

    public DOS() {
    }

    public DOS(DosEntry dosEntry, Controller controller) {
        this.dosEntry = dosEntry;
        this.controller = controller;
    }

    protected abstract void writeLineToSocket(String message);
    protected abstract void createSocket();
    protected abstract void connectToSocket();
    protected abstract void closeSocket();

    public DosEntry getDosEntry() {
        return dosEntry;
    }

    public void setDosEntry(DosEntry dosEntry) {
        this.dosEntry = dosEntry;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }
}
