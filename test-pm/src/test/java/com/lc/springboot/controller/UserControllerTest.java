package com.lc.springboot.controller;

import cn.hutool.core.util.RandomUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lc.springboot.common.api.BaseListResponse;
import com.lc.springboot.common.api.BaseResponse;
import com.lc.springboot.common.api.ResultCode;
import com.lc.springboot.common.auth.AuthConstant;
import com.lc.springboot.common.crypto.Sha256;
import com.lc.springboot.user.dto.request.UserAddRequest;
import com.lc.springboot.user.dto.request.UserLoginRequest;
import com.lc.springboot.user.dto.request.UserUpdateRequest;
import com.lc.springboot.user.enums.UserStatus;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

  @Before
  public void setUp() {
    userLoginRequest =
        UserLoginRequest.builder().userAccount("e150lotbsb").userPassword("123456").build();

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
    userUpdateRequest.setUserName(RandomUtil.randomString(10) + "-update");
    userUpdateRequest.setUserPassword(Sha256.getSHA256Str("123456"));
    userUpdateRequest.setEmail(
        RandomUtil.randomString(10) + "@" + RandomUtil.randomNumbers(3) + ".com");
    userUpdateRequest.setPhone("1" + RandomUtil.randomNumbers(10));
    userUpdateRequest.setStatus(UserStatus.LOGOUT.getCode());
    userUpdateRequest.setUserType("normal");
    userUpdateRequest.setUserAccount(RandomUtil.randomString(10));
  }

  /**
   * 测试登录
   *
   * @throws Exception
   */
  @Test
  public void testUserLogin() throws Exception {
    TestResponseBean result = TestUtil.postReq(mockMvc, BASE_PATH + "/login", userLoginRequest);

    assertThat(result.isSuccess()).isTrue();
    // assertThat(baseBeanResponse.getCode()).isEqualTo(ResultCode.UN_AUTHORIZED);
  }

  /**
   * 测试用户创建，没有登录的情况下
   *
   * @throws Exception
   */
  @Test()
  public void testCreateUserAuthorizeMissing() throws Exception {
    TestResponseBean result = TestUtil.postReq(mockMvc, BASE_PATH + "/create", userAddRequest);

    assertThat(result.isSuccess()).isFalse();
    assertThat(result.getCode()).isEqualTo(ResultCode.UN_AUTHORIZED);
  }

  @Test
  public void testCreateUserPermissionDeniedException() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.add(AuthConstant.AUTHORIZATION_HEADER, "");
    TestResponseBean result =
        TestUtil.postReq(mockMvc, BASE_PATH + "/create", userAddRequest, headers);

    assertThat(result.isSuccess()).isFalse();
    assertThat(result.getCode()).isEqualTo(ResultCode.UN_AUTHORIZED);
  }

  @Test
  public void testCreateUserEmptyName() throws Exception {
    // userAddRequest.setGroupName(null);

    MvcResult mvcResult =
        mockMvc
            .perform(
                post(BASE_PATH + "/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(AuthConstant.AUTHORIZATION_HEADER, "test")
                    .content(objectMapper.writeValueAsBytes(userAddRequest)))
            .andExpect(status().isOk())
            .andReturn();

    log.info(mvcResult.getResponse().getContentAsString());

    TestResponseBean baseBeanResponse =
        objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), TestResponseBean.class);
    assertThat(baseBeanResponse.isSuccess()).isFalse();
    assertThat(baseBeanResponse.getCode()).isEqualTo(ResultCode.PARAM_VALID_ERROR);
  }

  @Test
  public void testCreateUserEmptyWxid() throws Exception {
    // userAddRequest.setGroupName("qqq");
    // userAddRequest.setGroupWxid(null);

    MvcResult mvcResult =
        mockMvc
            .perform(
                post(BASE_PATH + "/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(AuthConstant.AUTHORIZATION_HEADER, "test")
                    .content(objectMapper.writeValueAsBytes(userAddRequest)))
            .andExpect(status().isOk())
            .andReturn();

    log.info(mvcResult.getResponse().getContentAsString());

    TestResponseBean baseBeanResponse =
        objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), TestResponseBean.class);
    assertThat(baseBeanResponse.isSuccess()).isFalse();
    assertThat(baseBeanResponse.getCode()).isEqualTo(ResultCode.PARAM_VALID_ERROR);
  }

  @Test
  public void testCreateUserSuccessfully() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(
                post(BASE_PATH + "/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(AuthConstant.AUTHORIZATION_HEADER, "11111")
                    .content(objectMapper.writeValueAsString(userAddRequest)))
            .andExpect(status().isOk())
            .andReturn();

    log.info(mvcResult.getResponse().getContentAsString());

    TestResponseBean<Map> baseBeanResponse =
        objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), TestResponseBean.class);
    assertThat(baseBeanResponse.isSuccess()).isTrue();

    // assertThat(baseBeanResponse.getInfo().get("groupName"))
    //     .isEqualTo(userAddRequest.getGroupName());
    // assertThat(baseBeanResponse.getInfo().get("groupWxid"))
    //     .isEqualTo(userAddRequest.getGroupWxid());
  }

  @Test
  public void testListUserSuccessfully() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(
                get(BASE_PATH
                        + "/list/1/2?queryStartDate=2020-07-01 11:03:09&queryEndDate=2020-08-01 12:45:46")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(AuthConstant.AUTHORIZATION_HEADER, "11111111"))
            .andExpect(status().isOk())
            .andReturn();

    BaseListResponse baseListResponse =
        objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), BaseListResponse.class);
    log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(baseListResponse));

    assertThat(baseListResponse.isSuccess()).isTrue();
  }

  @Test
  public void testGetUserSuccessfully() throws Exception {
    userUpdateRequest.setId(8L);

    MvcResult mvcResult =
        mockMvc
            .perform(
                get(BASE_PATH + "/get/" + userUpdateRequest.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(AuthConstant.AUTHORIZATION_HEADER, "11111111"))
            .andExpect(status().isOk())
            .andReturn();

    TestResponseBean baseBeanResponse =
        objectMapper.readValue(
            mvcResult.getResponse().getContentAsString(), TestResponseBean.class);

    log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(baseBeanResponse));

    assertThat(baseBeanResponse.isSuccess()).isTrue();
  }

  @Test
  public void testGetUserMissingPathVariable() throws Exception {
    MvcResult mvcResult =
        mockMvc
            .perform(
                get(BASE_PATH + "/get")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(AuthConstant.AUTHORIZATION_HEADER, "111"))
            .andExpect(status().is4xxClientError())
            .andReturn();

    assertThat("").isEqualTo(mvcResult.getResponse().getContentAsString());
  }

  @Test
  public void testUpdateUserSuccessfully() throws Exception {
    userUpdateRequest.setId(10L);

    MvcResult mvcResult =
        mockMvc
            .perform(
                put(BASE_PATH + "/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(AuthConstant.AUTHORIZATION_HEADER, "11111111")
                    .content(objectMapper.writeValueAsString(userUpdateRequest)))
            .andExpect(status().isOk())
            .andReturn();

    log.info(mvcResult.getResponse().getContentAsString());

    BaseResponse baseResponse =
        objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BaseResponse.class);
    assertThat(baseResponse.isSuccess()).isTrue();
  }
}
