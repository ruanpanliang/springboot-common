package com.lc.springboot.obs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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

    @Bean
    @ConditionalOnMissingBean(HuaWeiObsUtil.class)
    public HuaWeiObsUtil huaWeiObsUtil(){
        return new HuaWeiObsUtil(huaWeiObsProperties);
    }

    @PostConstruct
    public void checkBucketExists() {
        if (StringUtils.isEmpty(huaWeiObsProperties.getEndPoint())) {
            log.warn("obs endpoint information is not configured, obs function cannot be used !!!");
            return;
        }

        if (StringUtils.isEmpty(huaWeiObsProperties.getBucketName())) {
            log.error("Please set the obs bucket name first, otherwise the obs resource cannot be used !!!");
            return;
        }

        log.info("Check whether the bucket name of obs resource exists");
        if (!huaWeiObsUtil().bucketExists(huaWeiObsProperties.getBucketName())) {
            // 创建bucket
            log.info("Create bucket");
            huaWeiObsUtil().createBucket(huaWeiObsProperties.getBucketName());
        }
    }

}
