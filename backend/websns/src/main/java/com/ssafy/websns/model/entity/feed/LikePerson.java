package com.ssafy.websns.model.entity.feed;

import static javax.persistence.FetchType.LAZY;

import com.ssafy.websns.model.entity.user.User;
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
@ApiModel(value = "좋아요", description = "좋아요에 관한 정보를 나타냅니다.")
public class LikePerson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "LIKE_NO")
  private Integer no;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "USER_NO", nullable = false)
  private User user;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "FEED_NO", nullable = false)
  private Feed feed;

  public void createLikePerson(User user, Feed feed) {
    this.user = user;
    this.feed = feed;
  }

}
