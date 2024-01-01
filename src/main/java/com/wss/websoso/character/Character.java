package com.wss.websoso.character;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "character")
public class Character {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long characterId;
      private String characterTag;
      private String characterImg;
      private String characterComment;
      private String characterDescription;
      private String characterBadge;
}