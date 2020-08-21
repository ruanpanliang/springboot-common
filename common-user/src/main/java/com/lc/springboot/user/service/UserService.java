package com.lc.springboot.user.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.springboot.common.auth.AuthProperties;
import com.lc.springboot.common.auth.token.AccessToken;
import com.lc.springboot.common.auth.token.AccessTokenGen;
import com.lc.springboot.common.auth.token.AccessTokenUtil;
import com.lc.springboot.user.dto.request.UserAddRequest;
import com.lc.springboot.user.dto.request.UserLoginRequest;
import com.lc.springboot.user.dto.request.UserQueryRequest;
import com.lc.springboot.user.dto.request.UserUpdateRequest;
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
import com.lc.springboot.common.api.MyPageInfo;
import com.lc.springboot.common.api.ResultCode;
import com.lc.springboot.common.error.ServiceException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户业务处理类
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

  @Autowired private ModelMapper modelMapper;
  @Resource private UserMapper userMapper;
  @Resource private RoleMapper roleMapper;
  @Resource private PrivilegeMapper privilegeMapper;
  @Resource private MenuMapper menuMapper;
  @Autowired AuthProperties authProperties;
  @Autowired AccessTokenUtil accessTokenUtil;

  /**
   * 用户登录
   *
   * @param userLoginRequest 用户登录请求对象
   * @return 用户登录详情（包括用户基本信息，角色信息，权限信息，菜单信息，令牌信息等）
   */
  public UserLoginDetailResponse login(UserLoginRequest userLoginRequest) {
    MyPageInfo<User> list = list(convertToQueryDto(userLoginRequest), PageRequest.of(1, 1));

    if (CollectionUtil.isEmpty(list.getList())) {
      throw new ServiceException("账号或密码错误");
    }
    User user = list.getList().get(0);
    if (UserStatus.LOGOUT.getCode() == user.getStatus()) {
      throw new ServiceException("用户已被注销");
    }

    // 执行登录操作
    return getLoginDetailInfo(user);
  }

  /**
   * 获取用户登录相关信息
   *
   * @param user 用户实体对象
   * @return 用户登录详情（包括用户基本信息，角色信息，权限信息，菜单信息，令牌信息等）
   */
  private UserLoginDetailResponse getLoginDetailInfo(User user) {
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

    // 令牌信息
    // 获取系统定义的令牌过期时间
    long accessTokenValiditySeconds = authProperties.getAccessTokenValiditySeconds();
    // 生成令牌
    AccessToken accessToken = AccessTokenGen.genAccessToken(accessTokenValiditySeconds);
    result.setAccessToken(accessToken);
    // 删除原来保存在redis中用户信息
    accessTokenUtil.delUserInfo(user.getUserAccount());

    // 保存用户信息到redis中
    accessTokenUtil.saveUserInfo(accessToken, result, user.getUserAccount());

    return result;
  }

  /**
   * 创建用户
   *
   * @param userAddRequest 用户新增对象
   * @return 用户更新对象
   */
  @Transactional(rollbackFor = Exception.class)
  public UserUpdateRequest create(UserAddRequest userAddRequest) {
    User user = convertToModel(userAddRequest);
    // 默认设置为正常状态
    user.setStatus(UserStatus.NORMAL.getCode());

    userMapper.insert(user);
    log.info("创建用户,{}", user);

    return convertToDto(user);
  }

  /**
   * 更新用户信息
   *
   * @param userUpdateRequest 用户更新对象
   * @return 返回更新条数
   */
  @Transactional(rollbackFor = Exception.class)
  public int updateUser(UserUpdateRequest userUpdateRequest) {
    // 先取回之前数据
    User user = getById(userUpdateRequest.getId());

    // 如果不存在，需要报异常
    if (user == null) {
      throw new ServiceException(ResultCode.RECORD_NOT_FOUND);
    }

    user.setUserAccount(userUpdateRequest.getUserAccount());
    //user.setUserPassword(userUpdateRequest.getUserPassword());
    user.setUserType(userUpdateRequest.getUserType());
    user.setUserName(userUpdateRequest.getUserName());
    user.setEmail(userUpdateRequest.getEmail());
    user.setPhone(userUpdateRequest.getPhone());
    user.setStatus(userUpdateRequest.getStatus());

    return userMapper.updateById(user);
  }

  /**
   * 获取用户列表信息
   *
   * @param userQueryRequest 用户查询对象
   * @param pageable 分页信息
   * @return 用户结果集
   */
  public MyPageInfo<User> list(UserQueryRequest userQueryRequest, Pageable pageable) {
    PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
    QueryWrapper<User> mapper = new QueryWrapper(convertToModel(userQueryRequest));
    mapper.ge(
        userQueryRequest.getQueryStartDate() != null,
        User.COL_CREATED_TIME,
        userQueryRequest.getQueryStartDate());
    mapper.le(
        userQueryRequest.getQueryEndDate() != null,
        User.COL_CREATED_TIME,
        userQueryRequest.getQueryEndDate());
    mapper.orderByDesc(User.COL_CREATED_TIME);
    return new MyPageInfo(userMapper.selectList(mapper));
  }

  private UserUpdateRequest convertToDto(User user) {
    return modelMapper.map(user, UserUpdateRequest.class);
  }

  private User convertToModel(UserAddRequest userAddRequest) {
    return modelMapper.map(userAddRequest, User.class);
  }

  private User convertToModel(UserQueryRequest userQueryRequest) {
    return modelMapper.map(userQueryRequest, User.class);
  }

  private UserQueryRequest convertToQueryDto(UserLoginRequest userLoginRequest) {
    return modelMapper.map(userLoginRequest, UserQueryRequest.class);
  }
}
