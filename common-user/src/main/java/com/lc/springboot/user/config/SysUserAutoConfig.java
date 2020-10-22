package com.lc.springboot.user.config;

import com.lc.springboot.common.auth.AuthProperties;
import com.lc.springboot.common.config.date.StringToDateConverter;
import com.lc.springboot.common.redis.util.RedisUtil;
import com.lc.springboot.user.auth.token.AccessTokenUtil;
import com.lc.springboot.user.handler.sql.SqlHandler;
import com.lc.springboot.user.interceptor.UserAuthorizeInterceptor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，用于配置拦截器和时间状态器
 *
 * @author liangchao
 */
@Configuration
@EnableConfigurationProperties(UserProperties.class)
@ComponentScan(basePackages = {"com.lc.springboot"})
@MapperScan(basePackages = {"com.lc.springboot.user.mapper"})
public class SysUserAutoConfig implements WebMvcConfigurer {

    @Autowired
    AuthProperties authProperties;
    @Autowired
    UserProperties userProperties;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    Environment environment;

    /**
     * 令牌工具类
     * @return
     */
    @Bean
    AccessTokenUtil accessTokenUtil() {
        AccessTokenUtil accessTokenUtil = new AccessTokenUtil();
        accessTokenUtil.setRedisUtil(redisUtil);
        accessTokenUtil.setAuthProperties(authProperties);
        return accessTokenUtil;
    }

    /**
     * 加密方式
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 对象属性copy工具
     * @return
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    /**
     * 自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        UserAuthorizeInterceptor userAuthorizeInterceptor =
                new UserAuthorizeInterceptor(authProperties);
        userAuthorizeInterceptor.setRedisUtil(redisUtil);
        userAuthorizeInterceptor.setAccessTokenUtil(accessTokenUtil());

        registry.addInterceptor(userAuthorizeInterceptor);
    }

    /**
     * 前端参数转换工具
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 字符串转时间转换器，用于拦截带有注解@RequestMapping的方法参数
        registry.addConverter(new StringToDateConverter());
    }


    /**
     * 初始化sql脚本
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserPluginManager.class)
    public UserPluginManager userPluginManager() {
        // 初始化SCHEMA
        SqlHandler.init(environment);
        UserPluginManager pluginManager = new UserPluginManager() {
        };
        // 检查数据库字典是否已存在
        if (userProperties.isInitSql() && SqlHandler.checkIsDictionaryTableExists() == false) {
            SqlHandler.initBootstrapSql(pluginManager.getClass(), environment, "user");
        }
        return pluginManager;
    }
}
