package com.lc.springboot.user.controller;

import com.lc.springboot.common.api.BaseBeanResponse;
import com.lc.springboot.common.api.BaseResponse;
import com.lc.springboot.common.auth.AuthContext;
import com.lc.springboot.common.auth.Authorize;
import com.lc.springboot.common.auth.token.AccessToken;
import com.lc.springboot.common.auth.token.AccessTokenUtil;
import com.lc.springboot.user.dto.request.RefreshTokenRequest;
import com.lc.springboot.user.dto.request.UserLoginRequest;
import com.lc.springboot.user.dto.response.UserLoginDetailResponse;
import com.lc.springboot.user.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/** @author liangchao */
@Api(tags = "用户登录认证，授权 控制器处理类")
@RestController
@RequestMapping("/v1/auth/")
public class AuthController {

  @Autowired AuthService authService;
  @Autowired AccessTokenUtil accessTokenUtil;

  /**
   * 用户登录
   *
   * @param userLoginRequest 用户登录请求对象
   * @return 用户登录返回详情（包括用户基本信息，角色信息，权限信息，菜单信息，令牌信息）
   */
  @ApiOperation(value = "登录 (无需header token校验)")
  @PostMapping(value = "/login")
  public BaseBeanResponse<AccessToken> login(@RequestBody UserLoginRequest userLoginRequest) {
    AccessToken login = authService.login(userLoginRequest);
    return new BaseBeanResponse<>(login);
  }

  /**
   * 获取用户授权列表信息
   *
   * @return 用户登录返回详情（包括用户基本信息，角色信息，权限信息，菜单信息，令牌信息）
   */
  @ApiOperation(value = "授权列表信息")
  @GetMapping(value = "/authorizationList")
  @Authorize({})
  public BaseBeanResponse<UserLoginDetailResponse> authorizationList() {
    UserLoginDetailResponse loginDetailInfo =
        authService.getLoginDetailInfo(AuthContext.getUserId(), AuthContext.getAuthz());
    return new BaseBeanResponse<>(loginDetailInfo);
  }

  /**
   * 用户登出
   *
   * @return 返回基本报文信息
   */
  @ApiOperation(value = "登出")
  @PostMapping(value = "/logout")
  @Authorize({})
  public BaseResponse logout() {
    accessTokenUtil.removeUserCacheInfo(AuthContext.getUserId(), true);
    return BaseResponse.success();
  }

  @PostMapping("/refreshToken")
  @ApiOperation(value = "刷新令牌值 (无需header token校验)")
  public BaseBeanResponse<AccessToken> refreshToken(
      @RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
    AccessToken accessToken = authService.refreshToken(refreshTokenRequest);
    return new BaseBeanResponse<>(accessToken);
  }
}
