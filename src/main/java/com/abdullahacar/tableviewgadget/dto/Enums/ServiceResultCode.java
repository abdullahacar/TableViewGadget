package com.abdullahacar.tableviewgadget.dto.Enums;

public enum ServiceResultCode {
    ERROR(-1),
    SUCCESSFUL(1),
    WARNING(2),
    EXCEPTION(3),
    NOT_VALID(4);

    private int value;

    ServiceResultCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
