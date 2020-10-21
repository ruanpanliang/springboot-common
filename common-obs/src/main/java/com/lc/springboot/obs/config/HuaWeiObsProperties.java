package com.lc.springboot.obs.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * obs存储配置
 *
 * @author liangc
 * @version V1.0
 * @date 2020/8/28
 **/
@Data
@ConfigurationProperties(prefix = "huawei.obs")
public class HuaWeiObsProperties {

    /**
     * 接口地址
     */
    private String endPoint;

    private String ak;

    private String sk;

    /**
     * 桶名
     */
    private String bucketName;

    /**
     * obs分配的存储空间，默认创建一个桶，则该桶的空间等于分配的总空间,单位：字节
     */
    private long obsTotalStorageSize;

}
