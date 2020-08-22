package com.lc.springboot.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.lc.springboot.common.api.MyPageInfo;
import com.lc.springboot.common.api.ResultCode;
import com.lc.springboot.common.error.ServiceException;
import com.lc.springboot.user.dto.request.UserAddRequest;
import com.lc.springboot.user.dto.request.UserQueryRequest;
import com.lc.springboot.user.dto.request.UserUpdateRequest;
import com.lc.springboot.user.enums.UserStatus;
import com.lc.springboot.user.mapper.UserMapper;
import com.lc.springboot.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    // user.setUserPassword(userUpdateRequest.getUserPassword());
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
}
