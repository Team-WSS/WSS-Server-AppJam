package com.wss.websoso.avatar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "avatar")
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id", nullable = false)
    private Long avatarId;

    @Column(name = "avatar_tag", nullable = false)
    private String avatarTag;

    @Column(name = "avatar_genre_badge_img", nullable = false)
    private String avatarGenreBadgeImg;

    @Column(name = "avatar_acquired_img", nullable = false)
    private String avatarAcquiredImg;

    @Column(name = "avatar_unacquired_img", nullable = false)
    private String avatarUnacquiredImg;

    @Column(name = "avatar_acquired_ment", nullable = false)
    private String avatarAcquiredMent;

    @Column(name = "avatar_unacquired_ment", nullable = false)
    private String avatarUnacquiredMent;

    @Column(name = "avatar_acquired_condition", nullable = false)
    private String avatarAcquiredCondition;

    @Column(name = "avatar_unacquired_condition", nullable = false)
    private String avatarUnacquiredCondition;
}