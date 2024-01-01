package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_novel")
public class UserNovel {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long userNovelId;
      private String userNovelTitle;
      private String userNovelAuthor;
      private String userNovelGenre;
      private String userNovelImg;
      private String userNovelDescription;
      private float userNovelRating;
      private ReadStatus userNovelReadStatus;
      private String userNovelReadStartDate;
      private String userNovelReadEndDate;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "user_id")
      private User user;

      @Builder
      public UserNovel(String userNovelTitle, String userNovelAuthor, String userNovelGenre, String userNovelImg, String userNovelDescription, float userNovelRating, ReadStatus userNovelReadStatus, String userNovelReadStartDate, String userNovelReadEndDate, User user) {
            this.userNovelTitle = userNovelTitle;
            this.userNovelAuthor = userNovelAuthor;
            this.userNovelGenre = userNovelGenre;
            this.userNovelImg = userNovelImg;
            this.userNovelDescription = userNovelDescription;
            this.userNovelRating = userNovelRating;
            this.userNovelReadStatus = userNovelReadStatus;
            this.userNovelReadStartDate = userNovelReadStartDate;
            this.userNovelReadEndDate = userNovelReadEndDate;
            this.user = user;
      }
}