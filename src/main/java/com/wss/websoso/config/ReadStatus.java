package com.wss.websoso.config;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReadStatus {
    FINISH("FINISH"),
    READING("READING"),
    DROP("DROP"),
    WISH("WISH");

    @Column(name = "user_novel_read_status", nullable = false)
    private final String status;
}
