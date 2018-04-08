package ru.gavrilov.common;

public enum  StatusEnum {
    ATTACK("Атакую"),
    STOP("Атака остановлена"),
    FINISHED("Атака завершена"),
    OTHER("Подготовка к атаке");

    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
