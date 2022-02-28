package com.ssafy.websns.service.user;

import com.ssafy.websns.model.dto.user.FollowDto.DeleteFollowRes;
import com.ssafy.websns.model.dto.user.FollowDto.FollowReq;
import com.ssafy.websns.model.dto.user.FollowDto.FollowRes;
import com.ssafy.websns.model.dto.user.UserProfileDto.UserProfileRes;
import com.ssafy.websns.model.entity.user.Follow;
import com.ssafy.websns.model.entity.user.User;
import com.ssafy.websns.model.entity.user.UserFollowCnt;
import com.ssafy.websns.model.entity.user.UserProfile;
import com.ssafy.websns.repository.user.FollowRepository;
import com.ssafy.websns.repository.user.UserFollowCntRepository;
import com.ssafy.websns.repository.user.UserProfileRepository;
import com.ssafy.websns.repository.user.UserRepository;
import com.ssafy.websns.service.validation.ValidateExist;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class FollowService {

  private final UserRepository userRepository;
  private final FollowRepository followRepository;
  private final UserFollowCntRepository userfollowCntRepository;
  private final UserProfileRepository userProfileRepository;

  ValidateExist validateExist = new ValidateExist();

  @Transactional
  public FollowRes createFollow(FollowReq request) {

    String userId = request.getUserId();
    String FollowingId = request.getFollowingId();

    Optional<User> userOp = userRepository.findByUserId(userId);
    Optional<User> followingOp = userRepository.findByUserId(FollowingId);
    User user = validateExist.findUser(userOp);
    User followingUser = validateExist.findUser(followingOp);

    Follow follow = new Follow();
    // 나를 팔로우 한 사람 (User), 내가 팔로우 한 사람
    follow.createFollow(user, followingUser);
    followRepository.save(follow);

    UserFollowCnt userFollowCnt = userfollowCntRepository.findByUser(user);
    UserFollowCnt followingUserCnt = userfollowCntRepository.findByUser(followingUser);

    userFollowCnt.plusFollowing();
    followingUserCnt.plusFollower();

    FollowRes response = new FollowRes(userFollowCnt, followingUserCnt);

    return response;

  }

  @Transactional
  public DeleteFollowRes cancelFollow(String userId, String followingId) {



    Optional<User> userOp = userRepository.findByUserId(userId);
    Optional<User> followingOp = userRepository.findByUserId(followingId);
    User user = validateExist.findUser(userOp);
    User followingUser = validateExist.findUser(followingOp);

    followRepository.deleteByUserFollowerNoAndAndUserFollowingNo(user,followingUser);

//    Follow follow = followRepository.findByNo(followNo);
//    User userFollower = follow.getUserFollowerNo();
//    User userFollowing = follow.getUserFollowingNo();

    UserFollowCnt userFollowCnt = userfollowCntRepository.findByUser(user);
    UserFollowCnt followingUserCnt = userfollowCntRepository.findByUser(followingUser);

    userFollowCnt.minusFollowing();
    followingUserCnt.minusFollower();

    DeleteFollowRes response = new DeleteFollowRes(user.getUserId(), followingUser.getUserId());

    return response;

  }

  public List<UserProfileRes> searchFollower(String userId) {

    Optional<User> userOp = userRepository.findByUserId(userId);
    User user = validateExist.findUser(userOp);
    List<Follow> follows = followRepository.findByUserFollowingNo(user);

    List<UserProfileRes> response = new ArrayList<>();
    follows.stream().forEach(follow -> {
      Optional<UserProfile> userProfileOp =
          userProfileRepository.findByUser(follow.getUserFollowerNo());
      UserProfile userProfile = validateExist.findUserProfile(userProfileOp);

      response.add(new UserProfileRes(userProfile));
    });

    return response;

  }

  public List<UserProfileRes> searchFollowing(String userId) {

    Optional<User> userOp = userRepository.findByUserId(userId);
    User user = validateExist.findUser(userOp);
    List<Follow> follows = followRepository.findByUserFollowerNo(user);

    List<UserProfileRes> response = new ArrayList<>();
    follows.stream().forEach(follow -> {
      Optional<UserProfile> userProfileOp =
          userProfileRepository.findByUser(follow.getUserFollowingNo());
      UserProfile userProfile = validateExist.findUserProfile(userProfileOp);

      response.add(new UserProfileRes(userProfile));
    });

    return response;

  }

}
