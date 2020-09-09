package com.lc.springboot.common.sftp;

import com.lc.springboot.common.sftp.util.SftpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @description: sftp相关配置
 * @author: liangc
 * @date: 2020-09-09 15:11
 */
@Configuration
@EnableConfigurationProperties(SftpProperties.class)
@ConditionalOnClass(FTPClient.class)
@ConditionalOnProperty(prefix = "sftp", name = "host")
@Slf4j
public class SftpAutoConfig {

    /**
     * sftp操作工具类
     *
     * @param sftpProperties
     * @return
     */
    @Bean(name = "sftpUtil")
    public SftpUtil sftpUtil(SftpProperties sftpProperties) {
        SftpUtil sftpUtil = new SftpUtil();
        sftpUtil.setSftpProperties(sftpProperties);
        return sftpUtil;
    }
}
