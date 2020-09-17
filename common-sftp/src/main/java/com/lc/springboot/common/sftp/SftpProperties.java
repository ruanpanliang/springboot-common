package com.lc.springboot.common.sftp;

import cn.hutool.extra.ftp.FtpConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**

 * @description: sftp配置
 * @author: liangc
 * @date: 2020-09-09 15:07
 * @version 1.0
 **/
@ConfigurationProperties(prefix = "sftp")
public class SftpProperties extends FtpConfig {


}
