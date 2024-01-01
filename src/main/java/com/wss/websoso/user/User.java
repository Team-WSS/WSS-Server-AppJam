package com.wss.websoso.user;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long userId;
      private String userNickname;
}
