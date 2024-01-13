package com.wss.websoso.user;

import com.wss.websoso.userAvatar.UserAvatar;
import com.wss.websoso.userNovel.UserNovel;
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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @Column(name = "user_rep_avatar_id", nullable = false)
    private Long userRepAvatarId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserAvatar> userAvatars = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserNovel> userNovels = new ArrayList<>();

    public int getMemoCount() {
        int memoCount = 0;
        for (UserNovel userNovel : getUserNovels()) {
            memoCount += userNovel.getMemos().size();
        }
        return memoCount;
    }
}
