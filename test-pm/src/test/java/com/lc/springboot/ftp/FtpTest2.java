package com.lc.springboot.ftp;

import cn.hutool.core.io.checksum.crc16.CRC16CCITT;
import com.lc.springboot.common.ftp.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/** 测试ftp功能 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@Slf4j
public class FtpTest2 {

  @Autowired
  FtpUtil ftpUtil;

  /** 上传文件 */
  @Test
  public void uploadTest() {
    boolean b =
        ftpUtil.uploadFile(
            "/testuploafile/aaa",
            "/Users/liangchao/work/我的文件/项目/三墩-移动/咪咕爱看-AI赋能/技术方案/(20200729) 浙江移动手机视频 AI智能分析片头片尾场景方案初稿.pptx");
    assertEquals(true, b);
  }

  @Test
  public void testConvert() throws IOException {
    String localFile1 = "/Users/liangchao/tmp/ftp/aaaaaa11111.jpg";

    byte[] bytes = FileUtils.readFileToByteArray(new File(localFile1));
    // 进行base64处理
    BASE64Encoder encoder = new BASE64Encoder();
    String base64ImageStr = encoder.encodeBuffer(bytes);
    byte[] aa = base64ImageStr.getBytes();

    for (int i = 0; i < 1000; i++) {
      System.out.print(aa[i]);
    }
    System.out.println();

    String convert = ImgBase64Util.convert(base64ImageStr);

    aa = convert.getBytes();
    for (int i = 0; i < 1000; i++) {
      System.out.print(aa[i]);
    }
  }

  @Test
  public void checkSum() throws IOException {
    String localFile1 = "/Users/liangchao/tmp/ftp/aaaaaa11111.jpg";
    String localFile2 = "/Users/liangchao/tmp/ftp/22222.jpg";

    byte[] bytes1 = FileUtils.readFileToByteArray(new File(localFile1));
    byte[] bytes2 = FileUtils.readFileToByteArray(new File(localFile2));

    CRC16CCITT crc16 = new CRC16CCITT();
    crc16.update(bytes1);
    log.info(crc16.getHexValue());

    crc16 = new CRC16CCITT();
    crc16.update(bytes2);
    log.info(crc16.getHexValue());
  }

  /** 下载文件 */
  @Test
  public void downloadTest() {
    ftpUtil.downloadFile("/testuploafile/aaa", "1111.jpg", "/Users/liangchao/tmp/ftp/3333.jpg");
  }

  // /** 上传文件,base64编码 */
  // @Test
  // public void uploadWithBase64Test() throws IOException {
  //   String localFile = "/Users/liangchao/tmp/ftp/aaaaaa11111.jpg";
  //   byte[] bytes = FileUtils.readFileToByteArray(new File(localFile));
  //
  //   for (int i = 0; i < 1000; i++) {
  //     System.out.print(bytes[i]);
  //   }
  //   System.out.println();
  //
  //
  //   // 进行base64处理
  //   BASE64Encoder encoder = new BASE64Encoder();
  //   String base64ImageStr = encoder.encodeBuffer(bytes);
  //
  //   System.out.println(base64ImageStr.substring(0,1000));
  //   String convert = ImgBase64Util.convert(base64ImageStr);
  //   System.out.println(convert.substring(0,1000));
  //
  //   BASE64Decoder decoder = new BASE64Decoder();
  //   byte[] bytes2 = decoder.decodeBuffer(convert);
  //
  //   for (int i = 0; i < 1000; i++) {
  //     System.out.print(bytes2[i]);
  //   }
  //   System.out.println();
  //
  //   boolean b = ftpUtil.uploadFileWithBase64("/testuploafile/aaa", convert, "22222.jpg");
  //   assertEquals(true, b);
  // }

}
