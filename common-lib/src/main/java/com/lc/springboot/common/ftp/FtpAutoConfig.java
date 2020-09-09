package com.lc.springboot.common.ftp;

import com.lc.springboot.common.ftp.util.FtpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @description: ftp相关配置
 * @author: liangc
 * @date: 2020-08-06 15:11
 */
@Configuration
@EnableConfigurationProperties(FtpProperties.class)
@ConditionalOnClass(FTPClient.class)
//@ConditionalOnProperty(prefix = "ftp", name = "host")
@Slf4j
public class FtpAutoConfig {

    /**
     * ftp操作工具类
     *
     * @param ftpProperties
     * @return
     */
    @Bean(name = "ftpUtil")
    public FtpUtil ftpUtil(FtpProperties ftpProperties) {
        FtpUtil ftpUtil = new FtpUtil();
        ftpUtil.setFtpProperties(ftpProperties);
        return ftpUtil;
    }
}
