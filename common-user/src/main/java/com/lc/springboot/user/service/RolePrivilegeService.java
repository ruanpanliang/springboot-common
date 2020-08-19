package com.lc.springboot.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.springboot.user.dto.request.RolePrivilegeAddRequest;
import com.lc.springboot.user.dto.request.RolePrivilegeQueryRequest;
import com.lc.springboot.user.dto.request.RolePrivilegeUpdateRequest;
import com.lc.springboot.user.mapper.RolePrivilegeMapper;
import com.lc.springboot.user.model.RolePrivilege;
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
* 角色对应权限业务处理类
*
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/
@Service
@Slf4j
public class RolePrivilegeService extends ServiceImpl<RolePrivilegeMapper, RolePrivilege> {

 @Autowired private ModelMapper modelMapper;
 @Resource private RolePrivilegeMapper rolePrivilegeMapper;

 /**
 * 创建角色对应权限
 *
 * @param rolePrivilegeAddRequest 角色对应权限新增对象
 * @return 角色对应权限更新对象
 */
 @Transactional(rollbackFor = Exception.class)
 public RolePrivilegeUpdateRequest create(RolePrivilegeAddRequest rolePrivilegeAddRequest) {
   RolePrivilege rolePrivilege = convertToModel(rolePrivilegeAddRequest);

   rolePrivilegeMapper.insert(rolePrivilege);
   log.info("创建角色对应权限,{}", rolePrivilege);

   return convertToDto(rolePrivilege);
 }

 /**
 * 更新角色对应权限信息
 *
 * @param rolePrivilegeUpdateRequest 角色对应权限更新对象
 * @return 返回更新条数
 */
 @Transactional(rollbackFor = Exception.class)
 public int updateRolePrivilege(RolePrivilegeUpdateRequest rolePrivilegeUpdateRequest) {
   // 先取回之前数据
   RolePrivilege rolePrivilege = getById(rolePrivilegeUpdateRequest.getId());

   // 如果不存在，需要报异常
   if (rolePrivilege == null) {
   throw new ServiceException(ResultCode.RECORD_NOT_FOUND);
   }

   rolePrivilege.setRoleId(rolePrivilegeUpdateRequest.getRoleId());
   rolePrivilege.setPrivilegeId(rolePrivilegeUpdateRequest.getPrivilegeId());

   return rolePrivilegeMapper.updateById(rolePrivilege);
 }

 /**
 * 获取角色对应权限列表信息
 *
 * @param rolePrivilegeQueryRequest 角色对应权限查询对象
 * @param pageable 分页信息
 * @return 角色对应权限结果集
 */
 public MyPageInfo<RolePrivilege> list(RolePrivilegeQueryRequest rolePrivilegeQueryRequest, Pageable pageable) {
   PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
   QueryWrapper<RolePrivilege> mapper = new QueryWrapper(convertToModel(rolePrivilegeQueryRequest));
   mapper.ge(rolePrivilegeQueryRequest.getQueryStartDate() != null ,RolePrivilege.COL_CREATED_TIME,rolePrivilegeQueryRequest.getQueryStartDate());
   mapper.le(rolePrivilegeQueryRequest.getQueryEndDate()!= null ,RolePrivilege.COL_CREATED_TIME,rolePrivilegeQueryRequest.getQueryEndDate());
   mapper.orderByDesc(RolePrivilege.COL_CREATED_TIME);
   return new MyPageInfo(rolePrivilegeMapper.selectList(mapper));
 }

 private RolePrivilegeUpdateRequest convertToDto(RolePrivilege rolePrivilege) {
   return modelMapper.map(rolePrivilege, RolePrivilegeUpdateRequest.class);
 }

 private RolePrivilege convertToModel(RolePrivilegeAddRequest rolePrivilegeAddRequest) {
   return modelMapper.map(rolePrivilegeAddRequest, RolePrivilege.class);
 }

 private RolePrivilege convertToModel(RolePrivilegeQueryRequest rolePrivilegeQueryRequest) {
   return modelMapper.map(rolePrivilegeQueryRequest, RolePrivilege.class);
 }
}
