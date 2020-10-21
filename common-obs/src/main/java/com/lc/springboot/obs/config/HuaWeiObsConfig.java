package com.lc.springboot.obs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @author liangchao
 */

@Configuration
@EnableConfigurationProperties(HuaWeiObsProperties.class)
@Slf4j
public class HuaWeiObsConfig {

    @Autowired
    HuaWeiObsProperties huaWeiObsProperties;

    @Autowired
    HuaWeiObsUtil huaWeiObsUtil;

    @PostConstruct
    public void checkBucketExists() {
        if (StringUtils.isEmpty(huaWeiObsProperties.getEndPoint())) {
            log.warn("obs的endpoint信息没有做配置，obs功能不能使用哦!");
            return;
        }

        if (StringUtils.isEmpty(huaWeiObsProperties.getBucketName())) {
            log.error("请先设置obs桶名称，否则obs资源无法使用哦");
            return;
        }

        log.info("检测obs资源的bucket名称是否存在");
        if (!huaWeiObsUtil.bucketExists(huaWeiObsProperties.getBucketName())) {
            // 创建bucket
            log.info("创建bucket");
            huaWeiObsUtil.createBucket(huaWeiObsProperties.getBucketName());
        }
    }

}
