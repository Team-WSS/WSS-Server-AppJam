package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.platform.Platform;
import com.wss.websoso.user.User;
import com.wss.websoso.userNovelKeyword.UserNovelKeyword;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "userNovel", cascade = CascadeType.ALL)
    private List<UserNovelKeyword> userNovelKeywords;

    @OneToMany(mappedBy = "userNovel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Platform> platforms = new ArrayList<>();

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