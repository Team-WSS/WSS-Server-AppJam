package com.wss.websoso.avatar;

import com.wss.websoso.avatarLine.AvatarLine;
import com.wss.websoso.userAvatar.UserAvatar;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "avatar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AvatarLine> avatarLines = new ArrayList<>();

    @OneToMany(mappedBy = "avatar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAvatar> userAvatars = new ArrayList<>();
}