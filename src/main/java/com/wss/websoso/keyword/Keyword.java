package com.wss.websoso.keyword;

import com.wss.websoso.userNovelKeyword.UserNovelKeyword;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "keyword")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;
    private String keywordName;

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL)
    private List<UserNovelKeyword> userNovelKeywords = new ArrayList<>();
}