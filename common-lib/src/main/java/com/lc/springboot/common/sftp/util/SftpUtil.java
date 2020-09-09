package com.lc.springboot.common.sftp.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.extra.ssh.Sftp;
import com.lc.springboot.common.sftp.SftpProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @version 1.0
 * @description: sftp工具类
 * @author: liangc
 * @date: 2020-09-09 23:02
 */
@Slf4j
@Getter
@Setter
public class SftpUtil {

    private SftpProperties sftpProperties;

    /**
     * 上传文件到服务器端
     *
     * @param destPath      服务器上的相对路径，例如tmp/file
     * @param localFilePath 本地文件的绝对路径
     * @return boolean 成功返回true，否则返回false
     */
    public boolean uploadFile(String destPath, String localFilePath) {
        Sftp sftp = new Sftp(sftpProperties);
        boolean result;
        try {
            result = sftp.upload(destPath, FileUtil.file(localFilePath));
            log.info("sftp 上传文件 " + localFilePath + "至 " + destPath + " 结果：" + result);
        } finally {
            IoUtil.close(sftp);
        }
        return result;
    }

    /**
     * 下载文件
     *
     * @param remotePath    ftp目录
     * @param fileName      需要下载的文件
     * @param localFilePath 本地文件路径
     */
    public void downloadFile(String remotePath, String fileName, String localFilePath) {
        Sftp sftp = new Sftp(sftpProperties);
        try {
            sftp.download(remotePath + "/" + fileName, new File(localFilePath));
            log.info("sftp 成功下载文件 " + localFilePath + "至 " + localFilePath);
        } finally {
            IoUtil.close(sftp);
        }
    }
}
