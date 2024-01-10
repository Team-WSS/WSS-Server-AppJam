package com.wss.websoso.genreBadge;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "genre_badge")
public class GenreBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_badge_id", nullable = false)
    private Long genreBadgeId;

    @Column(name = "genre_badge_name", nullable = false)
    private String genreBadgeName;

    @Column(name = "genre_badge_img", nullable = false)
    private String genreBadgeImg;
}
