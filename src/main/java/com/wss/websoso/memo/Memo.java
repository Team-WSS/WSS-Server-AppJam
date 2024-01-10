package com.wss.websoso.memo;

import com.wss.websoso.config.BaseTimeEntity;
import com.wss.websoso.userNovel.UserNovel;
import jakarta.persistence.Column;
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
@Table(name = "memo")
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id", nullable = false)
    private Long memoId;

    @Column(name = "memo_content", length = 2000, nullable = false)
    private String memoContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_novel_id", nullable = false)
    private UserNovel userNovel;

    @Builder
    private Memo(String memoContent, UserNovel userNovel) {
        this.memoContent = memoContent;
        this.userNovel = userNovel;
    }

    public void updateContent(String memoContent) {
        this.memoContent = memoContent;
    }
}