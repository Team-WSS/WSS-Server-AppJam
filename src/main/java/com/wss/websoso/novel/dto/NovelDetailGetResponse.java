package com.wss.websoso.novel.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.wss.websoso.config.ReadStatus;
import com.wss.websoso.platform.dto.PlatformGetResponse;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class NovelDetailGetResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long novelId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long userNovelId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String novelTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String userNovelTitle;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String novelAuthor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String userNovelAuthor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String novelGenre;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String userNovelGenre;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String novelImg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String userNovelImg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String novelDescription;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String userNovelDescription;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Float userNovelRating;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ReadStatus userNovelReadStatus;
    List<PlatformGetResponse> platforms;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonUnwrapped
    Map<String, String> userNovelReadDate;

    public NovelDetailGetResponse(Long novelId, Long userNovelId, String novelTitle, String userNovelTitle, String novelAuthor, String userNovelAuthor, String novelGenre, String userNovelGenre, String novelImg, String userNovelImg, String novelDescription, String userNovelDescription, Float userNovelRating, ReadStatus userNovelReadStatus, List<PlatformGetResponse> platforms) {
        this.novelId = novelId;
        this.userNovelId = userNovelId;
        this.novelTitle = novelTitle;
        this.userNovelTitle = userNovelTitle;
        this.novelAuthor = novelAuthor;
        this.userNovelAuthor = userNovelAuthor;
        this.novelGenre = novelGenre;
        this.userNovelGenre = userNovelGenre;
        this.novelImg = novelImg;
        this.userNovelImg = userNovelImg;
        this.novelDescription = novelDescription;
        this.userNovelDescription = userNovelDescription;
        this.userNovelRating = userNovelRating;
        this.userNovelReadStatus = userNovelReadStatus;
        this.platforms = platforms;
    }

    @JsonAnySetter
    public void setUserNovelReadEndDate(String userNovelReadStartDate, String userNovelReadEndDate) {
        userNovelReadDate = new HashMap<>();
        userNovelReadDate.put("userNovelReadStartDate", userNovelReadStartDate);
        userNovelReadDate.put("userNovelReadEndDate", userNovelReadEndDate);
    }
}
