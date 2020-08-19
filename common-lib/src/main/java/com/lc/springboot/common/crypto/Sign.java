package com.lc.springboot.common.crypto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lc.springboot.common.error.ServiceException;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/** @author liangchao */
public class Sign {

  public static final String CLAIM_EMAIL = "email";
  public static final String CLAIM_USER_ID = "userId";
  public static final String CLAIM_SUPPORT = "support";

  /** 解密 */
  private static Map<String, JWTVerifier> verifierMap = new HashMap<>();

  /** 加密 */
  private static Map<String, Algorithm> algorithmMap = new HashMap<>();

  /**
   * 根据secret
   *
   * @param signingToken the secret to use in the verify or signing instance
   * @return
   */
  private static Algorithm getAlgorithm(String signingToken) {
    Algorithm algorithm = algorithmMap.get(signingToken);
    if (algorithm == null) {
      synchronized (algorithmMap) {
        algorithm = algorithmMap.get(signingToken);
        if (algorithm == null) {
          algorithm = Algorithm.HMAC512(signingToken);
          algorithmMap.put(signingToken, algorithm);
        }
      }
    }
    return algorithm;
  }

  public static String generateEmailConfirmationToken(
      String userId, String email, String signingToken) {
    Algorithm algorithm = getAlgorithm(signingToken);
    String token =
        JWT.create()
            .withClaim(CLAIM_EMAIL, email)
            .withClaim(CLAIM_USER_ID, userId)
            .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2)))
            .sign(algorithm);
    return token;
  }

  public static DecodedJWT verifyEmailConfirmationToken(String tokenString, String signingToken) {
    return verifyToken(tokenString, signingToken);
  }

  public static DecodedJWT verifySessionToken(String tokenString, String signingToken) {
    return verifyToken(tokenString, signingToken);
  }

  static DecodedJWT verifyToken(String tokenString, String signingToken) {
    JWTVerifier verifier = verifierMap.get(signingToken);
    if (verifier == null) {
      synchronized (verifierMap) {
        verifier = verifierMap.get(signingToken);
        if (verifier == null) {
          Algorithm algorithm = Algorithm.HMAC512(signingToken);
          verifier = JWT.require(algorithm).build();
          verifierMap.put(signingToken, verifier);
        }
      }
    }

    DecodedJWT jwt = verifier.verify(tokenString);
    return jwt;
  }

  /**
   * 生成jwt token 信息
   *
   * @param userId 用户编号
   * @param signingToken the secret to use in the verify or signing instance
   * @param support 是否是支持用户
   * @param duration token有效时长，单位:毫秒
   * @return
   */
  public static String generateSessionToken(
      String userId, String signingToken, boolean support, long duration) {
    if (StringUtils.isEmpty(signingToken)) {
      throw new ServiceException("No signing token present");
    }
    Algorithm algorithm = getAlgorithm(signingToken);
    String token =
        JWT.create()
            .withClaim(CLAIM_USER_ID, userId)
            .withClaim(CLAIM_SUPPORT, support)
            .withExpiresAt(new Date(System.currentTimeMillis() + duration))
            .sign(algorithm);
    return token;
  }
}
