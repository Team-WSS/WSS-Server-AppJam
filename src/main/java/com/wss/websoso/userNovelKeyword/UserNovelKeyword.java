package com.wss.websoso.userNovelKeyword;

import com.wss.websoso.keyword.Keyword;
import com.wss.websoso.userNovel.UserNovel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserNovelKeywordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_novel_id")
    private UserNovel userNovel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Builder
    public UserNovelKeyword(UserNovel userNovel, Keyword keyword) {
        this.userNovel = userNovel;
        this.keyword = keyword;
    }
}
