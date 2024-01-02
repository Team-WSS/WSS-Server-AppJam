package com.wss.websoso.memo;

import com.wss.websoso.config.BaseTimeEntity;
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
@Table(name = "memo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;
    private String memoContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_novel_id")
    private UserNovel userNovel;

    @Builder
    private Memo(String memoContent, UserNovel userNovel) {
        this.memoContent = memoContent;
        this.userNovel = userNovel;
    }
}