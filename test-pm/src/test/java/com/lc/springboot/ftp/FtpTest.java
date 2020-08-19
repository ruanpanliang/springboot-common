package com.lc.springboot.ftp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpException;
import cn.hutool.extra.ftp.FtpMode;
import com.lc.springboot.common.ftp.FtpProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/** 测试ftp功能 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@Slf4j
public class FtpTest {

  @Autowired
  FtpProperties ftpProperties;

  @Test
  public void cdTest() {
    Ftp ftp = new Ftp(ftpProperties, FtpMode.Active);
    ftp.cd("/");
    Console.log(ftp.pwd());
    IoUtil.close(ftp);
  }

  @Test
  public void testMkdirs() {
    Ftp ftp = new Ftp(ftpProperties, FtpMode.Active);
    ftp.mkDirs("/tmp/files/");
    Console.log(ftp.pwd());
    IoUtil.close(ftp);
  }

  @Test
  public void uploadTest() {
    try (Ftp ftp = new Ftp(ftpProperties, FtpMode.Active)) {
      List<String> ls = ftp.ls("tmp/files");
      Console.log(ls);

      boolean upload =
          ftp.upload(
              "/tmp/files",
              FileUtil.file("/Users/liangchao/Pictures/fa0abf71f4a7c55d800bf703b5cc4e76.jpg"));
      Console.log(upload);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void reconnectIfTimeoutTest() throws InterruptedException {
    Ftp ftp = new Ftp(ftpProperties, FtpMode.Active);

    Console.log("打印pwd: " + ftp.pwd());

    Console.log("休眠一段时间，然后再次发送pwd命令，抛出异常表明连接超时");
    Thread.sleep(35 * 1000);

    try {
      Console.log("打印pwd: " + ftp.pwd());
    } catch (FtpException e) {
      e.printStackTrace();
    }

    Console.log("判断是否超时并重连...");
    ftp.reconnectIfTimeout();

    Console.log("打印pwd: " + ftp.pwd());

    IoUtil.close(ftp);
  }

  /**
   * 递归下载文件
   */
  @Test
  public void recursiveDownloadFolder() {
    Ftp ftp = new Ftp(ftpProperties, FtpMode.Active);
    ftp.recursiveDownloadFolder("/", FileUtil.file("/Users/liangchao/tmp/ftp"));

    IoUtil.close(ftp);
  }

  // @Test
  // public void recursiveDownloadFolderSftp() {
  //   Sftp ftp = new Sftp("127.0.0.1", 22, "test", "test");
  //
  //   ftp.cd("/file/aaa");
  //   Console.log(ftp.pwd());
  //   ftp.recursiveDownloadFolder("/", FileUtil.file("d:/test/download"));
  //
  //   IoUtil.close(ftp);
  // }
}
