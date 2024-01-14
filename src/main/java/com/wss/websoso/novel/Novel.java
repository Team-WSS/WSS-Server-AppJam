package com.wss.websoso.novel;

import com.wss.websoso.platform.Platform;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "novel")
public class Novel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "novel_id", nullable = false)
    private Long novelId;

    @Column(name = "novel_title", nullable = false)
    private String novelTitle;

    @Column(name = "novel_author", nullable = false)
    private String novelAuthor;

    @Column(name = "novel_genre", nullable = false)
    private String novelGenre;

    @Column(name = "novel_img", nullable = false)
    private String novelImg;

    @Column(name = "novel_description", columnDefinition = "text", nullable = false)
    private String novelDescription;

    @OneToMany(mappedBy = "novel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Platform> platforms = new ArrayList<>();
}