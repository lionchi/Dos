package ru.gavrilov.protocols.implement;


import ru.gavrilov.common.Controller;
import ru.gavrilov.protocols.abstracts.UDP;
import ru.gavrilov.protocols.entry.DosEntry;

import java.io.IOException;

public class DosUdp extends UDP {

    public DosUdp(DosEntry dosEntry, Controller controller) {
        super(dosEntry, controller);
    }

    @Override
    public void writeLineToSocket(String message) {
        byte[] data = message.getBytes();
        getPacket().setData(data);
        getPacket().setLength(data.length);
        try {
            getSocket().send(getPacket());
        } catch (IOException ex) {
            getController().appendToLog("Ошибка при подключении к сокету!");
            ex.printStackTrace();
        }
    }
}
