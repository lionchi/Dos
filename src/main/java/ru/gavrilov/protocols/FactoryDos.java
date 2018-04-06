package ru.gavrilov.protocols;

import ru.gavrilov.protocols.abstracts.DOS;
import ru.gavrilov.protocols.implement.DosTcp;
import ru.gavrilov.protocols.implement.DosUdp;

public final class FactoryDos {

    public static DOS createDosAttack(ProtocolEnum protocolEnum){
        if(protocolEnum.equals(ProtocolEnum.TCP)){
            return  new DosTcp();
        } else if (protocolEnum.equals(ProtocolEnum.UDP)) {
            return new DosUdp();
        } else {
            return null;
        }
    }

}
