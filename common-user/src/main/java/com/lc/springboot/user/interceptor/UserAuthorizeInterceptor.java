package com.lc.springboot.user.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.lc.springboot.common.auth.AuthProperties;
import com.lc.springboot.common.auth.AuthorizeInterceptor;
import com.lc.springboot.common.auth.PermissionDeniedException;
import com.lc.springboot.common.holder.RequestUserHolder;
import com.lc.springboot.common.redis.util.RedisUtil;
import com.lc.springboot.common.utils.RequestUtil;
import com.lc.springboot.user.auth.token.AccessTokenUtil;
import com.lc.springboot.user.constant.UserMenuConstant;
import com.lc.springboot.user.dto.response.MenuLoginDetailResponse;
import com.lc.springboot.user.dto.response.UserLoginDetailResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 权限拦截器
 *
 * @author liangchao
 */
@Slf4j
@Getter
@Setter
public class UserAuthorizeInterceptor extends AuthorizeInterceptor {

    private RedisUtil redisUtil;
    private AccessTokenUtil accessTokenUtil;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public UserAuthorizeInterceptor(AuthProperties authProperties) {
        super(authProperties);
    }

    /**
     * 从redis库（或者其他途径）中获取相关的用户信息进行校验
     *
     * @param authz      请求头token信息
     * @param authValues 认证细节信息
     * @return 检验通过返回true，否则返回false
     */
    @Override
    public boolean checkUser(String authz, String[] authValues) {
        // 将redis保存的用户信息提取出来,该值是在用户登录的时候保存到redis中的
        Long userId = accessTokenUtil.getUserId(authz);
        if (userId == null || userId == 0L) {
            throw new PermissionDeniedException(authProperties.getErrorAuthorizationHeader());
        }

        // 设置上下文
        RequestUserHolder.set(userId);

        //对于获取用户授权列表的功能，不需要url路径校验
        if (ArrayUtil.contains(authValues, UserMenuConstant.AUTHORIZATION_LIST)) {
            return true;
        }

        //从redis中获取用户信息
        UserLoginDetailResponse userDetail = (UserLoginDetailResponse) accessTokenUtil.currentUserInfo();
        //判断用户是否有权限
        List<MenuLoginDetailResponse> menuList = userDetail.getMenuList();
        if (CollectionUtil.isEmpty(menuList)) {
            return false;
        }

        String requestUri = RequestUtil.getHttpRequest().getRequestURI();

        for (MenuLoginDetailResponse menuLoginDetailResponse : menuList) {
            if (antPathMatcher.match(menuLoginDetailResponse.getMenuPath() + "/**", requestUri)) {
                return true;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
        // 删除上下文信息
        RequestUserHolder.remove();
    }
}
