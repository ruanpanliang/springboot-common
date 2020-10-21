package com.lc.springboot.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liangc
 * @version V1.0
 * @date 2020/10/21
 **/
@ConfigurationProperties("com.lc.springboot.user")
@Data
public class UserProperties {
    /**
     * 是否初始化，默认true自动安装SQL
     */
    private boolean initSql = true;

}
