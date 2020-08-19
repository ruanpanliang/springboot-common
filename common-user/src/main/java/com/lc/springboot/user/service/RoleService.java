package com.lc.springboot.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.springboot.user.dto.request.RoleAddRequest;
import com.lc.springboot.user.dto.request.RoleQueryRequest;
import com.lc.springboot.user.dto.request.RoleUpdateRequest;
import com.lc.springboot.user.mapper.RoleMapper;
import com.lc.springboot.user.model.Role;
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
* 角色业务处理类
*
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/
@Service
@Slf4j
public class RoleService extends ServiceImpl<RoleMapper, Role> {

 @Autowired private ModelMapper modelMapper;
 @Resource private RoleMapper roleMapper;

 /**
 * 创建角色
 *
 * @param roleAddRequest 角色新增对象
 * @return 角色更新对象
 */
 @Transactional(rollbackFor = Exception.class)
 public RoleUpdateRequest create(RoleAddRequest roleAddRequest) {
   Role role = convertToModel(roleAddRequest);

   roleMapper.insert(role);
   log.info("创建角色,{}", role);

   return convertToDto(role);
 }

 /**
 * 更新角色信息
 *
 * @param roleUpdateRequest 角色更新对象
 * @return 返回更新条数
 */
 @Transactional(rollbackFor = Exception.class)
 public int updateRole(RoleUpdateRequest roleUpdateRequest) {
   // 先取回之前数据
   Role role = getById(roleUpdateRequest.getId());

   // 如果不存在，需要报异常
   if (role == null) {
   throw new ServiceException(ResultCode.RECORD_NOT_FOUND);
   }

   role.setRoleCode(roleUpdateRequest.getRoleCode());
   role.setRoleName(roleUpdateRequest.getRoleName());
   role.setRoleType(roleUpdateRequest.getRoleType());
   role.setRoleStatus(roleUpdateRequest.getRoleStatus());
   role.setRemark(roleUpdateRequest.getRemark());

   return roleMapper.updateById(role);
 }

 /**
 * 获取角色列表信息
 *
 * @param roleQueryRequest 角色查询对象
 * @param pageable 分页信息
 * @return 角色结果集
 */
 public MyPageInfo<Role> list(RoleQueryRequest roleQueryRequest, Pageable pageable) {
   PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
   QueryWrapper<Role> mapper = new QueryWrapper(convertToModel(roleQueryRequest));
   mapper.ge(roleQueryRequest.getQueryStartDate() != null ,Role.COL_CREATED_TIME,roleQueryRequest.getQueryStartDate());
   mapper.le(roleQueryRequest.getQueryEndDate()!= null ,Role.COL_CREATED_TIME,roleQueryRequest.getQueryEndDate());
   mapper.orderByDesc(Role.COL_CREATED_TIME);
   return new MyPageInfo(roleMapper.selectList(mapper));
 }

 private RoleUpdateRequest convertToDto(Role role) {
   return modelMapper.map(role, RoleUpdateRequest.class);
 }

 private Role convertToModel(RoleAddRequest roleAddRequest) {
   return modelMapper.map(roleAddRequest, Role.class);
 }

 private Role convertToModel(RoleQueryRequest roleQueryRequest) {
   return modelMapper.map(roleQueryRequest, Role.class);
 }
}
