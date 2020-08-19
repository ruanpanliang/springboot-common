package com.lc.springboot.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.springboot.user.dto.request.UserRoleAddRequest;
import com.lc.springboot.user.dto.request.UserRoleQueryRequest;
import com.lc.springboot.user.dto.request.UserRoleUpdateRequest;
import com.lc.springboot.user.mapper.UserRoleMapper;
import com.lc.springboot.user.model.UserRole;
import com.lc.springboot.common.api.MyPageInfo;
import com.lc.springboot.common.api.ResultCode;
import com.lc.springboot.common.error.ServiceException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* 用户对应角色业务处理类
*
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/
@Service
@Slf4j
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {

 @Autowired private ModelMapper modelMapper;
 @Resource private UserRoleMapper userRoleMapper;

 /**
 * 创建用户对应角色
 *
 * @param userRoleAddRequest 用户对应角色新增对象
 * @return 用户对应角色更新对象
 */
 @Transactional(rollbackFor = Exception.class)
 public UserRoleUpdateRequest create(UserRoleAddRequest userRoleAddRequest) {
   UserRole userRole = convertToModel(userRoleAddRequest);

   userRoleMapper.insert(userRole);
   log.info("创建用户对应角色,{}", userRole);

   return convertToDto(userRole);
 }

 /**
 * 更新用户对应角色信息
 *
 * @param userRoleUpdateRequest 用户对应角色更新对象
 * @return 返回更新条数
 */
 @Transactional(rollbackFor = Exception.class)
 public int updateUserRole(UserRoleUpdateRequest userRoleUpdateRequest) {
   // 先取回之前数据
   UserRole userRole = getById(userRoleUpdateRequest.getId());

   // 如果不存在，需要报异常
   if (userRole == null) {
   throw new ServiceException(ResultCode.RECORD_NOT_FOUND);
   }

   userRole.setUserId(userRoleUpdateRequest.getUserId());
   userRole.setRoleId(userRoleUpdateRequest.getRoleId());

   return userRoleMapper.updateById(userRole);
 }

 /**
 * 获取用户对应角色列表信息
 *
 * @param userRoleQueryRequest 用户对应角色查询对象
 * @param pageable 分页信息
 * @return 用户对应角色结果集
 */
 public MyPageInfo<UserRole> list(UserRoleQueryRequest userRoleQueryRequest, Pageable pageable) {
   PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
   QueryWrapper<UserRole> mapper = new QueryWrapper(convertToModel(userRoleQueryRequest));
   mapper.ge(userRoleQueryRequest.getQueryStartDate() != null ,UserRole.COL_CREATED_TIME,userRoleQueryRequest.getQueryStartDate());
   mapper.le(userRoleQueryRequest.getQueryEndDate()!= null ,UserRole.COL_CREATED_TIME,userRoleQueryRequest.getQueryEndDate());
   mapper.orderByDesc(UserRole.COL_CREATED_TIME);
   return new MyPageInfo(userRoleMapper.selectList(mapper));
 }

 private UserRoleUpdateRequest convertToDto(UserRole userRole) {
   return modelMapper.map(userRole, UserRoleUpdateRequest.class);
 }

 private UserRole convertToModel(UserRoleAddRequest userRoleAddRequest) {
   return modelMapper.map(userRoleAddRequest, UserRole.class);
 }

 private UserRole convertToModel(UserRoleQueryRequest userRoleQueryRequest) {
   return modelMapper.map(userRoleQueryRequest, UserRole.class);
 }
}
