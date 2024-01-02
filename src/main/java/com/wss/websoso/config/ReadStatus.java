package com.wss.websoso.config;

public enum ReadStatus {
    FINISH("읽음"),
    READING("읽는 중"),
    DROP("하차"),
    WISH("읽고 싶음");

    private String status;

    ReadStatus(String status) {
        this.status = status;
    }
}
