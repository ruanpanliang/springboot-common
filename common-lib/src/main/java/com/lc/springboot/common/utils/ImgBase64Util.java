package com.lc.springboot.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 对image进行异或操作，用于加密处理
 *
 * @author liangchao
 */
@Deprecated
public class ImgBase64Util {

  public static String wirtePhoto(String image, String fileUrl) throws IOException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    String imageName = fileUrl + "/" + sdf.format(new Date()) + ".jpg";

    FileOutputStream ou = new FileOutputStream(imageName);
    BASE64Decoder decoder = new BASE64Decoder();
    ou.write(decoder.decodeBuffer(image));
    ou.flush();
    ou.close();
    return imageName;
  }

  /**
   * dat加密，目前判断该方法是多余的，不建议使用
   *
   * @param base64ImageStr 进行base64加密的image字符串
   * @return 进行异或后的值
   * @throws IOException
   */
  @Deprecated
  public static String convert(String base64ImageStr) throws IOException {
    BASE64Decoder decoder = new BASE64Decoder();
    BASE64Encoder encoder = new BASE64Encoder();
    byte[] bytes = decoder.decodeBuffer(base64ImageStr);

    Object[] xor = getFileType(bytes);

    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte) (bytes[i] ^ (int) xor[1]);
    }

    return encoder.encode(bytes).replaceAll("\r|\n", "");
  }

  /**
   * 判断图片类型
   *
   * @param bytes 原图片文件流
   * @return 第一个对象是图片的格式（例如：jpg）,第二个索引值是原字节数组和文件类型魔术头进行异或的值
   */
  private static Object[] getFileType(byte[] bytes) {
    Object[] xorType = new Object[2];
    int[] xors = new int[3];
    for (Map.Entry<String, String> type : FILE_TYPE_MAP.entrySet()) {
      String[] hex = {
        String.valueOf(type.getKey().charAt(0)) + type.getKey().charAt(1),
        String.valueOf(type.getKey().charAt(2)) + type.getKey().charAt(3),
        String.valueOf(type.getKey().charAt(4)) + type.getKey().charAt(5)
      };
      xors[0] = bytes[0] & 0xFF ^ Integer.parseInt(hex[0], 16);
      xors[1] = bytes[1] & 0xFF ^ Integer.parseInt(hex[1], 16);
      xors[2] = bytes[2] & 0xFF ^ Integer.parseInt(hex[2], 16);
      if (xors[0] == xors[1] && xors[1] == xors[2]) {
        xorType[0] = type.getValue();
        xorType[1] = xors[0];
        break;
      }
    }
    return xorType;
  }

  private static final Map<String, String> FILE_TYPE_MAP = new HashMap<>();

  static {
    getAllFileType();
  }

  /** 文件头信息 */
  private static void getAllFileType() {
    FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg");
    FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png");
  }
}
