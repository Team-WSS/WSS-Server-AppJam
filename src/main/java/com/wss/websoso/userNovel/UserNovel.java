package com.wss.websoso.userNovel;

import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.platform.Platform;
import com.wss.websoso.user.User;
import com.wss.websoso.userNovelKeyword.UserNovelKeyword;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
    @Column(name = "user_novel_id")
    private Long userNovelId;

    @Column(name = "novel_id")
    private Long novelId;

    @Column(name = "user_novel_title", nullable = false)
    private String userNovelTitle;

    @Column(name = "user_novel_author", nullable = false)
    private String userNovelAuthor;

    @Column(name = "user_novel_genre", nullable = false)
    private String userNovelGenre;

    @Column(name = "user_novel_img", nullable = false)
    private String userNovelImg;

    @Column(name = "user_novel_description", nullable = false)
    private String userNovelDescription;

    @Column(name = "user_novel_rating", nullable = false)
    private float userNovelRating;

    @Column(name = "user_novel_read_status", nullable = false)
    private ReadStatus userNovelReadStatus;

    @Column(name = "user_novel_read_start_date")
    private String userNovelReadStartDate;

    @Column(name = "user_novel_read_end_date")
    private String userNovelReadEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "userNovel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserNovelKeyword> userNovelKeywords;

    @OneToMany(mappedBy = "userNovel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Platform> platforms = new ArrayList<>();

    @Builder
    public UserNovel(Long novelId, String userNovelTitle, String userNovelAuthor, String userNovelGenre,
                     String userNovelImg, String userNovelDescription, float userNovelRating,
                     ReadStatus userNovelReadStatus, String userNovelReadStartDate, String userNovelReadEndDate,
                     User user) {
        this.novelId = novelId;
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