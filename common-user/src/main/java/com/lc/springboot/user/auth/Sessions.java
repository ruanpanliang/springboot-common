package com.lc.springboot.user.auth;

import com.lc.springboot.common.auth.AuthConstant;
import com.lc.springboot.common.crypto.Sign;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 会话层token
 *
 * @author liangchao
 */
public class Sessions {

  /** 12小时 */
  public static final long SHORT_SESSION = TimeUnit.HOURS.toMillis(12);
  /** 30天 */
  public static final long LONG_SESSION = TimeUnit.HOURS.toMillis(30 * 24);

  /**
   * 用户登录
   *
   * @param userId 用户ID
   * @param support 是否是支持用户
   * @param rememberMe 是否记住
   * @param signingSecret 签名秘钥
   * @param domain 域名
   * @param response http response对象
   */
  public void loginUser(
      String userId,
      boolean support,
      boolean rememberMe,
      String signingSecret,
      String domain,
      HttpServletResponse response) {
    long duration;
    int maxAge;

    if (rememberMe) {
      // "Remember me"
      duration = LONG_SESSION;
    } else {
      duration = SHORT_SESSION;
    }
    maxAge = (int) (duration / 1000);

    String token = Sign.generateSessionToken(userId, signingSecret, support, duration);

    Cookie cookie = new Cookie(AuthConstant.COOKIE_NAME, token);
    cookie.setPath("/");
    cookie.setDomain(domain);
    cookie.setMaxAge(maxAge);
    cookie.setHttpOnly(true);
    response.addCookie(cookie);
  }

  /**
   * 获取令牌
   *
   * @param request http request对象
   * @return 令牌
   */
  public String getToken(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null || cookies.length == 0) {
      return null;
    }
    Cookie tokenCookie =
        Arrays.stream(cookies)
            .filter(cookie -> AuthConstant.COOKIE_NAME.equals(cookie.getName()))
            .findAny()
            .orElse(null);
    if (tokenCookie == null) {
      return null;
    }
    return tokenCookie.getValue();
  }

  /**
   * 登出
   *
   * @param domain 域名
   * @param response http response对象
   */
  public void logout(String domain, HttpServletResponse response) {
    Cookie cookie = new Cookie(AuthConstant.COOKIE_NAME, "");
    cookie.setPath("/");
    cookie.setMaxAge(0);
    cookie.setDomain(domain);
    response.addCookie(cookie);
  }
}
