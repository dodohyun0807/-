package com.ssafy.websns.config.auth.provider;

import com.ssafy.websns.model.entity.user.User;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientKakao implements ClientProxy {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Res {
    String access_token;
  }

  // TODO ADMIN 유저 생성 시 getAdminUserData 메소드 생성 필요

  public User getUserData(String code) {

    MultiValueMap<String,String> req = new LinkedMultiValueMap<>();
    req.add("grant_type","authorization_code");
    req.add("client_id","beb77160d5b0e9d94c528d637f815ea5");
    req.add("redirect_uri","http://localhost:3000/callback/kakao");
    req.add("code",code);

    Res res = WebClient.builder().baseUrl("kauth.kakao.com").defaultHeader(HttpHeaders.CONTENT_TYPE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .build().post()
        .uri("https://kauth.kakao.com/oauth/token")
        .body(BodyInserters.fromFormData(req))
        .retrieve()
        .bodyToMono(Res.class)
        .block();

    String accessToken = res.getAccess_token();
    UserResponse userResponse = WebClient.builder().build().get()
        .uri("https://kapi.kakao.com/v2/user/me")
        .headers(h -> h.setBearerAuth(accessToken))
        .retrieve()
        .bodyToMono(UserResponse.class)
        .block();

    User user = new User();
    for(Map.Entry<String,Object> entry : userResponse.getKakao_account().entrySet()) {
      System.out.println(entry.getKey() + " : " + entry.getValue().toString());
    }
    user.createKakaoUser(userResponse);

    return user;

  }

  public void logout(String accessToken) {
    WebClient.builder().baseUrl("kapi.kakao.com").build().post()
        .uri("https://kapi.kakao.com/v1/user/logout")
        .headers(h -> h.setBearerAuth(accessToken)) // JWT 토큰을 Bearer 토큰으로 지정
        .retrieve()
        //     아래의 onStatus는 error handling
        .bodyToMono(UserResponse.class) // KAKAO의 유저 정보를 넣을 Dto 클래스
        .block();
  }

  @Data
  public static class UserResponse {

    String id;
    Map<String, Object> kakao_account;

  }
}
