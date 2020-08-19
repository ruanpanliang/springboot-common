package com.lc.springboot.common.crypto;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** lwx 2019/12/10 */
public class Sha256 {
  /***
   * 利用Apache的工具类实现SHA-256加密
   * @param str 加密后的报文
   * @return
   */
  public static String getSHA256Str(String str) {
    MessageDigest messageDigest;
    String encdeStr = "";
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
      byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
      encdeStr = Hex.encodeHexString(hash);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return encdeStr;
  }
}
