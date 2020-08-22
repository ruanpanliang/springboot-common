package com.lc.springboot.user.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.springboot.common.api.MyPageInfo;
import com.lc.springboot.common.auth.AuthContext;
import com.lc.springboot.common.auth.AuthProperties;
import com.lc.springboot.common.auth.token.AccessToken;
import com.lc.springboot.common.auth.token.AccessTokenGen;
import com.lc.springboot.common.auth.token.AccessTokenUtil;
import com.lc.springboot.common.error.ServiceException;
import com.lc.springboot.common.redis.util.RedisUtil;
import com.lc.springboot.user.dto.request.UserLoginRequest;
import com.lc.springboot.user.dto.request.UserQueryRequest;
import com.lc.springboot.user.dto.response.MenuLoginDetailResponse;
import com.lc.springboot.user.dto.response.PrivilegeLoginDetailResponse;
import com.lc.springboot.user.dto.response.RoleLoginDetailResponse;
import com.lc.springboot.user.dto.response.UserLoginDetailResponse;
import com.lc.springboot.user.enums.UserStatus;
import com.lc.springboot.user.mapper.MenuMapper;
import com.lc.springboot.user.mapper.PrivilegeMapper;
import com.lc.springboot.user.mapper.RoleMapper;
import com.lc.springboot.user.mapper.UserMapper;
import com.lc.springboot.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户认证授权业务类
 *
 * @author: liangc
 * @date: 2020-08-22 22:18
 * @version 1.0
 */
@Service
@Slf4j
public class AuthService extends ServiceImpl<UserMapper, User> {

  @Autowired private ModelMapper modelMapper;
  @Resource private RoleMapper roleMapper;
  @Resource private PrivilegeMapper privilegeMapper;
  @Resource private MenuMapper menuMapper;
  @Autowired AuthProperties authProperties;
  @Autowired AccessTokenUtil accessTokenUtil;
  @Autowired RedisUtil redisUtil;
  @Autowired UserService userService;

  /**
   * 用户登录
   *
   * @param userLoginRequest 用户登录请求对象
   * @return 用户令牌信息
   */
  public AccessToken login(UserLoginRequest userLoginRequest) {
    MyPageInfo<User> list =
        userService.list(convertToQueryDto(userLoginRequest), PageRequest.of(1, 1));

    if (CollectionUtil.isEmpty(list.getList())) {
      throw new ServiceException("账号或密码错误");
    }

    User user = list.getList().get(0);
    if (UserStatus.LOGOUT.getCode() == user.getStatus()) {
      throw new ServiceException("用户已被注销");
    }

    // 令牌信息
    // 获取系统定义的令牌过期时间
    long accessTokenValiditySeconds = authProperties.getAccessTokenValiditySeconds();
    // 生成令牌
    AccessToken accessToken = AccessTokenGen.genAccessToken(accessTokenValiditySeconds);
    // 删除原来保存在redis中用户信息
    accessTokenUtil.removeUserCacheInfo(user.getId());

    // 保存用户信息到redis中,其实只保存了用户的主键编号，当调用
    accessTokenUtil.saveUserInfo(accessToken, user.getId());

    return accessToken;
  }

  /**
   * 获取用户登录相关信息
   *
   * @return 用户登录详情（包括用户基本信息，角色信息，权限信息，菜单信息，令牌信息等）
   */
  public UserLoginDetailResponse getLoginDetailInfo() {
    long userId = AuthContext.getUserId();

    if (userId == 0L) {
      throw new ServiceException(authProperties.getAccessTokenTimeout());
    }

    // 获取用户基本信息
    User user = getById(userId);

    UserLoginDetailResponse result = new UserLoginDetailResponse();
    result.setEmail(user.getEmail());
    result.setUserAccount(user.getUserAccount());
    result.setUserName(user.getUserName());
    result.setUserType(user.getUserType());
    // 将随机码设置用户编号
    result.setRandomCode(user.getRandomCode());
    result.setId(user.getId());

    // 获取角色列表
    List<RoleLoginDetailResponse> roleList = roleMapper.getRoleListByUserId(user.getId());
    result.setRoleList(roleList);

    if (CollectionUtil.isNotEmpty(roleList)) {
      List<Long> roleIdList =
          roleList.stream().map(role -> role.getRoleId()).collect(Collectors.toList());
      // 获取权限列表
      List<PrivilegeLoginDetailResponse> privilegeList =
          privilegeMapper.getPrivilegeListByRoleIds(roleIdList);
      result.setPrivilegeList(privilegeList);
    } else {
      result.setPrivilegeList(ListUtil.empty());
    }

    if (CollectionUtil.isNotEmpty(result.getPrivilegeList())) {
      List<Long> privilegeIdList =
          result.getPrivilegeList().stream()
              .map(privilege -> privilege.getPrivilegeId())
              .collect(Collectors.toList());
      // 获取菜单信息
      List<MenuLoginDetailResponse> menuList =
          menuMapper.getMenuListByPrivilegeIds(privilegeIdList);
      result.setMenuList(menuList);
    } else {
      result.setMenuList(ListUtil.empty());
    }

    // 保存用户详细信息保存到redis中
    accessTokenUtil.saveUserDetails(AuthContext.getAuthz(), result);

    return result;
  }

  private UserQueryRequest convertToQueryDto(UserLoginRequest userLoginRequest) {
    return modelMapper.map(userLoginRequest, UserQueryRequest.class);
  }
}
