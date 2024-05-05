package com.tencent.wxcloudrun.dto;

public enum NumOutIdType {
    ONE(1),
    TWO(2),
    THREE(3);

    private final int value;

    NumOutIdType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}