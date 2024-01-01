package com.wss.websoso.userNovelKeyword;

import com.wss.websoso.keyword.Keyword;
import com.wss.websoso.userNovel.UserNovel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_novel_keyword")
public class UserNovelKeyword {

      @Id
      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "user_novel_id")
      private UserNovel userNovel;

      @Id
      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "keyword_id")
      private Keyword keyword;

      @Builder
      public UserNovelKeyword(UserNovel userNovel, Keyword keyword) {
            this.userNovel = userNovel;
            this.keyword = keyword;
      }
}
