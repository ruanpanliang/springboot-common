package com.lc.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lc.springboot.common.auth.AuthConstant;
import com.lc.springboot.common.auth.token.AccessToken;
import com.lc.springboot.user.dto.request.RefreshTokenRequest;
import com.lc.springboot.user.dto.request.UserLoginRequest;
import com.lc.springboot.user.dto.response.UserLoginDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

/** 用户认证 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class AuthControllerTest {

  /** url跟路径 */
  private static final String BASE_PATH = "/v1/auth";

  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  /** 用户登录请求对象 */
  UserLoginRequest userLoginRequest;

  @Rule public ExpectedException expectedEx = ExpectedException.none();

  private static final String USER_ACCOUNT = "c5orkubs72";
  private static final String USER_PASSWORD = "123456";
  private static String tokenVal = "czi8ey6yiylm5hzsuxwbz5n334zimwgoo5v15u7ygtm4dzm7e6";
  private static String refreshTokenVal = "r6g1jtopqythwo6dcphglxg5rcr2ygfjj6yqo55i0hmz5l14nf";

  @Before
  public void setUp() {
    userLoginRequest =
        UserLoginRequest.builder().userAccount(USER_ACCOUNT).userPassword(USER_PASSWORD).build();
  }

  private HttpHeaders getHttpHeaders(String tokenVal) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(AuthConstant.AUTHORIZATION_HEADER, tokenVal);
    return headers;
  }

  /**
   * 测试登录
   *
   * @throws Exception
   */
  @Test
  public void testUserLogin() throws Exception {
    TestBaseBeanResponse<AccessToken> result =
        new TestUtil<AccessToken>()
            .request(
                mockMvc,
                BASE_PATH + "/login",
                userLoginRequest,
                AccessToken.class,
                HttpMethod.POST);

    assertThat(result.isSuccess()).isTrue();
    assertThat(result.getInfo().getAccess_token()).isNotBlank();
    assertThat(result.getInfo().getRefresh_token()).isNotBlank();
    assertThat(result.getInfo().getExpires_in()).isGreaterThan(0);
  }

  /**
   * 获取用户授权信息
   *
   * @throws Exception
   */
  @Test
  public void testAuthorizationList() throws Exception {
    HttpHeaders headers = getHttpHeaders(tokenVal);
    TestBaseBeanResponse<UserLoginDetailResponse> result =
        new TestUtil<UserLoginDetailResponse>()
            .request(
                mockMvc,
                BASE_PATH + "/authorizationList",
                null,
                headers,
                UserLoginDetailResponse.class,
                HttpMethod.GET);

    assertThat(result.isSuccess()).isTrue();
  }

  /**
   * 刷新令牌
   *
   * @throws Exception
   */
  @Test
  public void testRefreshToken() throws Exception {
    RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
    refreshTokenRequest.setRefreshToken(refreshTokenVal);
    TestBaseBeanResponse<AccessToken> result =
        new TestUtil<AccessToken>()
            .request(
                mockMvc,
                BASE_PATH + "/refreshToken",
                refreshTokenRequest,
                null,
                AccessToken.class,
                HttpMethod.POST);

    assertThat(result.isSuccess()).isTrue();
    assertThat(result.getInfo().getAccess_token()).isNotBlank();
    assertThat(result.getInfo().getRefresh_token()).isNotBlank();
    assertThat(result.getInfo().getExpires_in()).isGreaterThan(0);
  }

  /**
   * 测试登出
   *
   * @throws Exception
   */
  @Test
  public void testUserLogout() throws Exception {
    HttpHeaders headers = getHttpHeaders(tokenVal);
    TestBaseResponse result =
        new TestUtil().request(mockMvc, BASE_PATH + "/logout", null, headers, HttpMethod.POST);

    assertThat(result.isSuccess()).isTrue();
  }
}
