package com.lc.springboot.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统工具类
 *
 * @author liangchao
 */
@Slf4j
public class SysUtil {

  public static final String JSON_UTF8_TYPE = "application/json;charset=UTF-8";

  /**
   * 将异常堆栈信息转成String的形式返回
   *
   * @param e 异常信息
   * @return 异常信息字符串
   */
  public static StringBuilder getExceptionStackTraceInfo(Throwable e) {
    StringBuilder sb = new StringBuilder();
    if (e == null) {
      return sb;
    }

    sb.append(e.toString() + "\n");
    StackTraceElement[] stackTraceElements = e.getStackTrace();
    for (StackTraceElement element : stackTraceElements) {
      sb.append("    at " + element + "\n");
    }

    return sb;
  }

  /** 打印请求的参数信息，包括 参数，body和header */
  public static void printRequestParamInfo() {
    HttpServletRequest request = getRequest();
    Map<String, String[]> paramMap = request.getParameterMap();

    log.info("请求参数信息：-------------------------------------------");
    for (Map.Entry<String, String[]> stringEntry : paramMap.entrySet()) {
      log.info(stringEntry.getKey() + " = " + Arrays.toString(stringEntry.getValue()));
    }

    log.info("-------------------------------------------------------");
    log.info("请求体实体信息：" + getRequestBodyContent());
    log.info("-------------------------------------------------------");

    log.info("请求头信息-----------------------------------------------");
    log.info(getRequestAllHeaderInfo(request).toString());
  }

  /**
   * 获取request体的数据
   *
   * @return 请求数据报文信息
   */
  public static String getRequestBodyContent() {
    HttpServletRequest request = getRequest();

    StringBuilder sb = new StringBuilder();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
      String str;
      while ((str = reader.readLine()) != null) {
        sb.append(str);
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return sb.toString();
  }

  /**
   * 获取客户端的真实IP
   *
   * @return 客户端的真实IP
   */
  public static String getRemoteAddrIp() {
    HttpServletRequest request = getRequest();

    // 先从nginx自定义配置获取
    String ip = request.getHeader("X-real-ip");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("x-forwarded-for");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return StringUtils.trim(ip);
  }

  /**
   * 将request的所有header信息转成Map的形式保存起来
   *
   * @param request HttpServletRequest
   * @return
   */
  public static Map<String, String> getRequestAllHeaderInfo(HttpServletRequest request) {
    Map<String, String> result = new HashMap<>();
    Enumeration<String> enumeration = request.getHeaderNames();

    while (enumeration.hasMoreElements()) {
      String headerName = enumeration.nextElement();
      result.put(headerName, request.getHeader(headerName));
    }
    return result;
  }

  /**
   * 向response对象里写数据，json格式
   *
   * @param response
   * @param altMsg
   */
  public static void doResponse(HttpServletResponse response, String altMsg) {
    try {
      response.setContentType(JSON_UTF8_TYPE);
      response.getWriter().write(altMsg);
      response.getWriter().flush();
      response.getWriter().close();
    } catch (IOException e) {
      log.error("response写数据出错", e);
    }
  }

  /**
   * 检测http method是否校验通过
   *
   * @param dbMethod 数据库端允许的method配置，如果该值为all，则表示所有的method都是允许的
   * @param requestMethod 请求端的http method的值
   * @return 检测通过返回true，反之返回false
   */
  public static boolean isHttpMethodMatch(String dbMethod, String requestMethod) {
    if (StringUtils.isEmpty(dbMethod)) {
      log.info("参数 dbMethod 的值为空 ：" + dbMethod);
      return false;
    }

    if (StringUtils.isEmpty(requestMethod)) {
      log.info("参数 requestMethod 的值为空 ：" + requestMethod);
      return false;
    }

    // 如果配置端（DB）配置的method值为all，则表示所有 Http Method的只都可以通过
    if (StringUtils.equalsIgnoreCase(dbMethod, "all")) {
      return true;
    }

    return StringUtils.equalsIgnoreCase(dbMethod, requestMethod);
  }

  /**
   * 从上下文中获取request对象
   *
   * @return request对象
   */
  public static HttpServletRequest getRequest() {
    ServletRequestAttributes ra =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = ra.getRequest();

    return request;
  }
}
