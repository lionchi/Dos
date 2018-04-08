package ru.gavrilov.protocols;

import ru.gavrilov.controllers.DosFormController;
import ru.gavrilov.protocols.abstracts.DOS;
import ru.gavrilov.protocols.entry.DosEntry;
import ru.gavrilov.protocols.implement.DosTcp;
import ru.gavrilov.protocols.implement.DosUdp;

public final class FactoryDos {

    public static DOS createDosAttack(DosEntry dosEntry, DosFormController controller){
        if(dosEntry.getProtocol().equals(ProtocolEnum.TCP)){
            return  new DosTcp(dosEntry, controller);
        } else if (dosEntry.getProtocol().equals(ProtocolEnum.UDP)) {
            return new DosUdp(dosEntry, controller);
        } else {
            return null;
        }
    }

}
