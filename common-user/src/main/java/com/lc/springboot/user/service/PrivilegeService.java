package com.lc.springboot.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.springboot.user.dto.request.PrivilegeAddRequest;
import com.lc.springboot.user.dto.request.PrivilegeQueryRequest;
import com.lc.springboot.user.dto.request.PrivilegeUpdateRequest;
import com.lc.springboot.user.mapper.PrivilegeMapper;
import com.lc.springboot.user.model.Privilege;
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
* 权限业务处理类
*
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/
@Service
@Slf4j
public class PrivilegeService extends ServiceImpl<PrivilegeMapper, Privilege> {

 @Autowired private ModelMapper modelMapper;
 @Resource private PrivilegeMapper privilegeMapper;

 /**
 * 创建权限
 *
 * @param privilegeAddRequest 权限新增对象
 * @return 权限更新对象
 */
 @Transactional(rollbackFor = Exception.class)
 public PrivilegeUpdateRequest create(PrivilegeAddRequest privilegeAddRequest) {
   Privilege privilege = convertToModel(privilegeAddRequest);

   privilegeMapper.insert(privilege);
   log.info("创建权限,{}", privilege);

   return convertToDto(privilege);
 }

 /**
 * 更新权限信息
 *
 * @param privilegeUpdateRequest 权限更新对象
 * @return 返回更新条数
 */
 @Transactional(rollbackFor = Exception.class)
 public int updatePrivilege(PrivilegeUpdateRequest privilegeUpdateRequest) {
   // 先取回之前数据
   Privilege privilege = getById(privilegeUpdateRequest.getId());

   // 如果不存在，需要报异常
   if (privilege == null) {
   throw new ServiceException(ResultCode.RECORD_NOT_FOUND);
   }

   privilege.setPrivilegeCode(privilegeUpdateRequest.getPrivilegeCode());
   privilege.setPrivilegeName(privilegeUpdateRequest.getPrivilegeName());
   privilege.setPrivilegeType(privilegeUpdateRequest.getPrivilegeType());
   privilege.setEntityType(privilegeUpdateRequest.getEntityType());
   privilege.setEntityId(privilegeUpdateRequest.getEntityId());
   privilege.setRemark(privilegeUpdateRequest.getRemark());

   return privilegeMapper.updateById(privilege);
 }

 /**
 * 获取权限列表信息
 *
 * @param privilegeQueryRequest 权限查询对象
 * @param pageable 分页信息
 * @return 权限结果集
 */
 public MyPageInfo<Privilege> list(PrivilegeQueryRequest privilegeQueryRequest, Pageable pageable) {
   PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
   QueryWrapper<Privilege> mapper = new QueryWrapper(convertToModel(privilegeQueryRequest));
   mapper.ge(privilegeQueryRequest.getQueryStartDate() != null ,Privilege.COL_CREATED_TIME,privilegeQueryRequest.getQueryStartDate());
   mapper.le(privilegeQueryRequest.getQueryEndDate()!= null ,Privilege.COL_CREATED_TIME,privilegeQueryRequest.getQueryEndDate());
   mapper.orderByDesc(Privilege.COL_CREATED_TIME);
   return new MyPageInfo(privilegeMapper.selectList(mapper));
 }

 private PrivilegeUpdateRequest convertToDto(Privilege privilege) {
   return modelMapper.map(privilege, PrivilegeUpdateRequest.class);
 }

 private Privilege convertToModel(PrivilegeAddRequest privilegeAddRequest) {
   return modelMapper.map(privilegeAddRequest, Privilege.class);
 }

 private Privilege convertToModel(PrivilegeQueryRequest privilegeQueryRequest) {
   return modelMapper.map(privilegeQueryRequest, Privilege.class);
 }
}
