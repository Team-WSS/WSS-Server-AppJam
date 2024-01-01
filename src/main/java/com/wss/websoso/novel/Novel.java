package com.wss.websoso.novel;

import jakarta.persistence.*;
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