package com.lc.springboot.common.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author liangchao
 */
@ApiModel("公共配置类")
@ConfigurationProperties(prefix = "common.auth.props")
@Getter
@Setter
public class AuthProperties {

    /**
     * AUTH ERROR Messages
     */
    @ApiModelProperty(value = "您无此功能操作权限", example = "You do not have access to this service")
    private String errorMsgDoNotHaveAccess = "您无此功能操作权限";

    /**
     * Missing Authorization http header
     */
    @ApiModelProperty(value = "缺少Authorization信息", example = "Missing Authorization http header")
    private String errorMsgMissingAuthHeader = "Missing Authorization http header";

    /**
     * Invalid Authorization Header
     */
    @ApiModelProperty(value = "非法的Authorization信息", example = "Invalid Authorization Header")
    private String errorAuthorizationHeader = "Invalid Authorization Header";

    /**
     * 令牌过期时间，单位：秒，默认24小时
     */
    @ApiModelProperty(value = "令牌过期时间，单位：秒", example = "86400")
    private long accessTokenValiditySeconds = 86400;

    /**
     * Session timed out, please log in again
     */
    @ApiModelProperty(value = "会话超时，请重新登录")
    private String accessTokenTimeout = "会话超时，请重新登录";

    /**
     * 白名单IP列表，对于这些IP不做权限校验（例如token校验或用户权限校验等）
     */
    @ApiModelProperty(value = "白名单IP列表")
    private List<String> whiteIpList;

    /**
     * 白名单token列表，不需要用户登录再进行授权等操作
     */
    @ApiModelProperty(value = "白名单token列表")
    private List<String> whiteTokenList;

}
