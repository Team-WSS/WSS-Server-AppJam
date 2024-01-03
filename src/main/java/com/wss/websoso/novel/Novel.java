package com.wss.websoso.novel;

import com.wss.websoso.platform.Platform;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "novel")
public class Novel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long novelId;
    private String novelTitle;
    private String novelAuthor;
    private String novelGenre;
    private String novelImg;
    private String novelDescription;

    @OneToMany(mappedBy = "novel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Platform> platforms = new ArrayList<>();
}