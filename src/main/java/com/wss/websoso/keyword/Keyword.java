package com.wss.websoso.keyword;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "keyword")
public class Keyword {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long keywordId;
      private String keywordName;
}