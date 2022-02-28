package com.ssafy.websns.model.dto.feed;

import com.ssafy.websns.model.entity.feed.Comment;
import com.ssafy.websns.model.entity.user.UserProfile;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CreateReq {

    private String userId;
    private Integer parent;
    private String content;
    private Boolean privateMode;
    private Boolean deleteMode;

    public CreateReq(String userId, Integer parent, String content,
        Boolean privateMode, Boolean deleteMode) {
      this.userId = userId;
      this.parent = parent;
      this.content = content;
      this.privateMode = privateMode;
      this.deleteMode = deleteMode;
    }

  }

  @Getter
  public static class CommentRes {

    private Integer no;
    private String userId;
    private String nickname;
    private String profileImg;
    private Integer feedNo;
    private Integer parent;
    private String content;
    private LocalDateTime createdAt;

    public CommentRes(UserProfile userProfile, Comment comment) {
      this.no = comment.getNo();
      this.userId = userProfile.getUser().getUserId();
      this.nickname = userProfile.getNickname();
      this.profileImg = userProfile.getProfileImg();
      this.feedNo = comment.getFeed().getNo();
      this.parent = comment.getParent() != null ? comment.getParent().getNo() : null;
      this.content = comment.getContent();
      this.createdAt = comment.getCreatedAt();
    }

  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class UpdateReq {

    private String content;
    private Boolean privateMode;
    private LocalDateTime updateAt;

    public UpdateReq(String content,
        Boolean privateMode, LocalDateTime updateAt) {
      this.content = content;
      this.privateMode = privateMode;
      this.updateAt = updateAt;
    }

  }

  @Getter
  public static class UpdateRes {

    private Integer no;
    private String nickname;
    private String profileImg;
    private String content;
    private Boolean privateMode;
    private LocalDateTime updateAt;

    public UpdateRes(UserProfile userProfile, Comment comment) {
      this.no = comment.getNo();
      this.nickname = userProfile.getNickname();
      this.profileImg = userProfile.getProfileImg();
      this.content = comment.getContent();
      this.privateMode = comment.getPrivateMode();
      this.updateAt = comment.getUpdatedAt();
    }

  }

}
