package com.lc.springboot.common.ftp.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;
import com.lc.springboot.common.ftp.FtpProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**

 * @description: ftp工具类
 * @author: liangc
 * @date: 2020-08-06 23:02
 * @version 1.0
 */
@Slf4j
@Getter
@Setter
public class FtpUtil {

  private FtpProperties ftpProperties;
  /**
   * 上传文件到服务器端
   *
   * @param destPath 服务器上的相对路径，例如tmp/file
   * @param localFilePath 本地文件的绝对路径
   * @return boolean 成功返回true，否则返回false
   */
  public boolean uploadFile(String destPath, String localFilePath) {
    Ftp ftp = new Ftp(ftpProperties, FtpMode.Active);
    boolean result;
    try {
      result = ftp.upload(destPath, FileUtil.file(localFilePath));
      log.info("上传文件 " + localFilePath + "至 " + destPath + " 结果：" + result);
    } finally {
      IoUtil.close(ftp);
    }
    return result;
  }

  /**
   * 下载文件
   *
   * @param remotePath ftp目录
   * @param fileName 需要下载的文件
   * @param localFilePath 本地文件路径
   */
  public void downloadFile(String remotePath, String fileName, String localFilePath) {
    Ftp ftp = new Ftp(ftpProperties, FtpMode.Active);
    try {
      ftp.download(remotePath, fileName, new File(localFilePath));
      log.info("成功下载文件 " + localFilePath + "至 " + localFilePath);
    } finally {
      IoUtil.close(ftp);
    }
  }
}
