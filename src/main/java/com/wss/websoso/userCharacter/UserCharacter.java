package com.wss.websoso.userCharacter;

import com.wss.websoso.character.Character;
import com.wss.websoso.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_character")
public class UserCharacter {

      @Id
      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "user_id")
      private User user;

      @Id
      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "character_id")
      private Character character;

      @Builder
      public UserCharacter(User user, Character character) {
            this.user = user;
            this.character = character;
      }
}
