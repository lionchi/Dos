package ru.gavrilov.protocols.implement;

import ru.gavrilov.common.Controller;
import ru.gavrilov.protocols.abstracts.TCP;
import ru.gavrilov.protocols.entry.DosEntry;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DosTcp extends TCP {

    public DosTcp(DosEntry dosEntry, Controller controller) {
        super(dosEntry,controller);
    }

    @Override
    public void writeLineToSocket(String message) {
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(getSocket().getOutputStream()))){
            bw.write(getDosEntry().getMessage());
            bw.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
