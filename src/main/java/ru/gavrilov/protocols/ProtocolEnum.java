package ru.gavrilov.protocols;

public enum ProtocolEnum {
    TCP("TCP"),
    UDP("UDP");

    private String nameProtocol;

    ProtocolEnum(String nameProtocol) {
        this.nameProtocol = nameProtocol;
    }

    @Override
    public String toString() {
        return nameProtocol;
    }
}
