package com.wss.websoso.novel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "novel")
public class Novel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long novelId;
    private String novelTitle;
    private String novelAuthor;
    private String novelGenre;
    private String novelImg;
    private String novelDescription;
}