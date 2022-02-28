package com.ssafy.websns.model.entity.feed;

import static javax.persistence.FetchType.LAZY;

import io.swagger.annotations.ApiModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
@ApiModel(value = "피드 댓글 수", description = "피드 댓글의 수를 나타냅니다.")
public class FeedCommentCnt {

  @Id
  @Column(name = "FEED_COMMENT_NO")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer no;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "FEED_NO", nullable = false)
  private Feed feed;

  @Column(nullable = false)
  private Integer commentCnt;

}
