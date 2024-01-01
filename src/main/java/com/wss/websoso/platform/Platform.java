package com.wss.websoso.platform;

import com.wss.websoso.novel.Novel;
import com.wss.websoso.userNovel.UserNovel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "platform")
public class Platform {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long platformId;
      private String platformName;
      private String platformUrl;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "novel_id")
      private Novel novel;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "user_novel_id")
      private UserNovel userNovel;

      @Builder
      public Platform(String platformName, String platformUrl, Novel novel) {
            this.platformName = platformName;
            this.platformUrl = platformUrl;
            this.novel = novel;
      }

      @Builder
      public Platform(String platformName, String platformUrl, UserNovel userNovel) {
            this.platformName = platformName;
            this.platformUrl = platformUrl;
            this.userNovel = userNovel;
      }
}