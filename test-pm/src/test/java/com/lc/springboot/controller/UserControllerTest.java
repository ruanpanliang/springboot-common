package com.lc.springboot.controller;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lc.springboot.common.api.ResultCode;
import com.lc.springboot.common.auth.AuthConstant;
import com.lc.springboot.common.crypto.Sha256;
import com.lc.springboot.user.dto.request.UserAddRequest;
import com.lc.springboot.user.dto.request.UserLoginRequest;
import com.lc.springboot.user.dto.request.UserUpdateRequest;
import com.lc.springboot.user.dto.response.UserLoginDetailResponse;
import com.lc.springboot.user.enums.UserStatus;
import com.lc.springboot.user.model.User;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Controller基础功能测试 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {

  /** url跟路径 */
  private static final String BASE_PATH = "/v1/user/user";

  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  /** 用户新增请求对象 */
  UserAddRequest userAddRequest;

  /** 用户更新请求对象 */
  UserUpdateRequest userUpdateRequest;

  /** 用户登录请求对象 */
  UserLoginRequest userLoginRequest;

  @Rule public ExpectedException expectedEx = ExpectedException.none();

  private static final String USER_ACCOUNT = "e150lotbsb";
  private static final String USER_PASSWORD = "123456";
  private static String tokenVal = "bw7rlvc3etdxxv6w461t5evmih1lcq4vvi03z48r7g7hw8poin";

  @Before
  public void setUp() {
    userLoginRequest =
        UserLoginRequest.builder().userAccount(USER_ACCOUNT).userPassword(USER_PASSWORD).build();

    userAddRequest =
        UserAddRequest.builder()
            .userName("superMan")
            .userPassword(Sha256.getSHA256Str("111111"))
            .email("ruanpanliang@126.com")
            .phone("12300000000")
            .userType("admin")
            .userAccount("liangc")
            .build();

    userUpdateRequest = new UserUpdateRequest();
    userUpdateRequest.setId(3L);
    userUpdateRequest.setUserName(RandomUtil.randomString(10) + "-update");
    userUpdateRequest.setEmail(
        RandomUtil.randomString(10) + "@" + RandomUtil.randomNumbers(3) + ".com");
    userUpdateRequest.setPhone("1" + RandomUtil.randomNumbers(10));
    userUpdateRequest.setStatus(UserStatus.LOGOUT.getCode());
    userUpdateRequest.setUserType("normal");
    userUpdateRequest.setUserAccount(RandomUtil.randomString(10));
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
    TestBaseBeanResponse<UserLoginDetailResponse> result =
        new TestUtil<UserLoginDetailResponse>()
            .request(
                mockMvc,
                BASE_PATH + "/login",
                userLoginRequest,
                UserLoginDetailResponse.class,
                HttpMethod.POST);

    assertThat(result.isSuccess()).isTrue();
    assertThat(result.getInfo().getUserAccount()).isEqualTo(USER_ACCOUNT);
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

  /**
   * 测试用户创建，无请求头校验信息
   *
   * @throws Exception
   */
  @Test
  public void testCreateUserAuthorizeMissing() throws Exception {
    TestBaseBeanResponse<UserUpdateRequest> result =
        new TestUtil<UserUpdateRequest>()
            .request(
                mockMvc,
                BASE_PATH + "/create",
                userAddRequest,
                UserUpdateRequest.class,
                HttpMethod.POST);

    assertThat(result.isSuccess()).isFalse();
    assertThat(result.getCode()).isEqualTo(ResultCode.UN_AUTHORIZED);
  }

  /**
   * 测试用户创建，请求头校验信息的值为空
   *
   * @throws Exception
   */
  @Test
  public void testCreateUserPermissionDeniedException() throws Exception {
    HttpHeaders headers = getHttpHeaders("");
    TestBaseBeanResponse<UserUpdateRequest> result =
        new TestUtil<UserUpdateRequest>()
            .request(
                mockMvc,
                BASE_PATH + "/create",
                userAddRequest,
                headers,
                UserUpdateRequest.class,
                HttpMethod.POST);

    assertThat(result.isSuccess()).isFalse();
    assertThat(result.getCode()).isEqualTo(ResultCode.UN_AUTHORIZED);
  }

  /**
   * 测试创建用户，令牌合法，传的参数为后台不能为空的参数
   *
   * @throws Exception
   */
  @Test
  public void testCreateUserEmptyName() throws Exception {
    // 设置用户账号为空
    userAddRequest.setUserAccount("");
    // userAddRequest.setUserPassword("");

    HttpHeaders headers = getHttpHeaders(tokenVal);
    TestBaseBeanResponse<UserUpdateRequest> result =
        new TestUtil<UserUpdateRequest>()
            .request(
                mockMvc,
                BASE_PATH + "/create",
                userAddRequest,
                headers,
                UserUpdateRequest.class,
                HttpMethod.POST);

    assertThat(result.isSuccess()).isFalse();
    assertThat(result.getCode()).isEqualTo(ResultCode.PARAM_VALID_ERROR);
  }

  /**
   * 测试创建用户，成功
   *
   * @throws Exception
   */
  @Test
  public void testCreateUserSuccessfully() throws Exception {
    // 设置用户登录账号
    userAddRequest.setUserAccount(RandomUtil.randomString(12));
    HttpHeaders headers = getHttpHeaders(tokenVal);
    TestBaseBeanResponse<UserUpdateRequest> result =
        new TestUtil<UserUpdateRequest>()
            .request(
                mockMvc,
                BASE_PATH + "/create",
                userAddRequest,
                headers,
                UserUpdateRequest.class,
                HttpMethod.POST);

    assertThat(result.isSuccess()).isTrue();
  }

  /**
   * 查询用户列表信息
   *
   * @throws Exception
   */
  @Test
  public void testListUserSuccessfully() throws Exception {
    HttpHeaders headers = getHttpHeaders(tokenVal);
    TestBaseListResponse<User> result =
        new TestUtil<User>()
            .requestList(
                mockMvc,
                BASE_PATH
                    + "/list/1/2?queryStartDate=2020-07-01 11:03:09&queryEndDate=2020-10-01 12:45:46",
                null,
                headers,
                User.class,
                HttpMethod.GET);

    assertThat(result.isSuccess()).isTrue();
  }

  @Test
  public void testGetUserSuccessfully() throws Exception {
    HttpHeaders headers = getHttpHeaders(tokenVal);
    TestBaseBeanResponse<User> result =
        new TestUtil<User>()
            .request(
                mockMvc,
                BASE_PATH + "/get/" + userUpdateRequest.getId(),
                null,
                headers,
                User.class,
                HttpMethod.GET);

    assertThat(result.isSuccess()).isTrue();
  }

  @Test
  public void testGetUserMissingPathVariable() throws Exception {
    HttpHeaders headers = getHttpHeaders(tokenVal);
    MvcResult mvcResult =
        mockMvc
            .perform(
                get(BASE_PATH + "/get").contentType(MediaType.APPLICATION_JSON).headers(headers))
            .andExpect(status().is4xxClientError())
            .andReturn();

    assertThat("").isEqualTo(mvcResult.getResponse().getContentAsString());
  }

  /**
   * 测试用户更新
   *
   * @throws Exception
   */
  @Test
  public void testUpdateUserSuccessfully() throws Exception {
    HttpHeaders headers = getHttpHeaders(tokenVal);
    TestBaseBeanResponse<UserUpdateRequest> result =
        new TestUtil<UserUpdateRequest>()
            .request(
                mockMvc,
                BASE_PATH + "/update",
                userUpdateRequest,
                headers,
                UserUpdateRequest.class,
                HttpMethod.PUT);

    assertThat(result.isSuccess()).isTrue();
  }

  /**
   * 测试删除用户
   *
   * @throws Exception
   */
  @Test
  public void testDeleteUserSuccessfully() throws Exception {
    HttpHeaders headers = getHttpHeaders(tokenVal);
    TestBaseResponse result =
        new TestUtil().request(mockMvc, BASE_PATH + "/delete/5", null, headers, HttpMethod.DELETE);

    assertThat(result.isSuccess()).isTrue();
  }
}
