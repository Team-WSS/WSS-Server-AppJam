package com.wss.websoso.config;

public enum ReadStatus {
      finish("읽음"),
      reading("읽는 중"),
      drop("하차"),
      wish("읽고 싶음");

      private String status;

      ReadStatus(String status) {
            this.status = status;
      }
}
