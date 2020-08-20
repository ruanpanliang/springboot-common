package com.lc.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.validation.constraints.NotNull;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试用例工具类
 *
 * @author: liangc
 * @date: 2020-08-20 09:07
 * @version 1.0
 */
@Slf4j
public class TestUtil<T> {

  public static final String UTF8 = "UTF-8";
  private static ObjectMapper objectMapper = new ObjectMapper();

  /**
   * 设置编码
   *
   * @param mvcResult
   */
  public static void setCharsetUTF8(MvcResult mvcResult) {
    mvcResult.getResponse().setCharacterEncoding(UTF8);
  }

  /**
   * 设置请求对象信息
   *
   * @param path 请求路径
   * @param paramObj 参数请求对象
   * @return {@link MockHttpServletRequestBuilder}
   * @throws JsonProcessingException
   */
  public static TestResponseBean postReq(MockMvc mockMvc, String path, Object paramObj)
      throws Exception {
    return getTestResponseBean(mockMvc, path, paramObj, null);
  }

  /**
   * 设置请求对象信息
   *
   * @param path 请求路径
   * @param paramObj 参数请求对象
   * @param httpHeaders 请求头信息
   * @return {@link MockHttpServletRequestBuilder}
   * @throws JsonProcessingException
   */
  public static TestResponseBean postReq(
      MockMvc mockMvc, String path, Object paramObj, HttpHeaders httpHeaders) throws Exception {
    return getTestResponseBean(mockMvc, path, paramObj, httpHeaders);
  }

  private static TestResponseBean getTestResponseBean(
      MockMvc mockMvc, String path, Object paramObj, HttpHeaders httpHeaders) throws Exception {
    MockHttpServletRequestBuilder postBuilder =
        getMockHttpServletRequestBuilder(path, paramObj, httpHeaders);

    MvcResult mvcResult = mockMvc.perform(postBuilder).andExpect(status().isOk()).andReturn();
    setCharsetUTF8(mvcResult);

    String resultContent = mvcResult.getResponse().getContentAsString();

    // 打印响应报文
    System.out.println("-----------------------------------------\n");
    System.out.println(resultContent);
    System.out.println("\n------------------------------------------");

    TestResponseBean result = objectMapper.readValue(resultContent, TestResponseBean.class);
    return result;
  }

  @NotNull
  private static MockHttpServletRequestBuilder getMockHttpServletRequestBuilder(
      String path, Object paramObj, HttpHeaders httpHeaders) throws JsonProcessingException {

    MockHttpServletRequestBuilder postBuilder =
        post(path)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding(UTF8)
            .content(objectMapper.writeValueAsBytes(paramObj));
    if (httpHeaders != null) {
      postBuilder.headers(httpHeaders);
    }
    return postBuilder;
  }

  @NotNull
  private static MockHttpServletRequestBuilder getMockHttpServletRequestBuilder(
      String path, Object paramObj) throws JsonProcessingException {
    return getMockHttpServletRequestBuilder(path, paramObj, null);
  }
}
