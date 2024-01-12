package com.wss.websoso.platform;

import com.wss.websoso.novel.Novel;
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
@Table(name = "platform")
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "platform_id", nullable = false)
    private Long platformId;

    @Column(name = "platform_name", nullable = false)
    private String platformName;

    @Column(name = "platform_url", nullable = false)
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

    @Builder(builderMethodName = "builderWithUserNovel", buildMethodName = "buildWithUserNovel")
    public Platform(String platformName, String platformUrl, UserNovel userNovel) {
        this.platformName = platformName;
        this.platformUrl = platformUrl;
        this.userNovel = userNovel;
    }
}