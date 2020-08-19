package com.lc.springboot.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.springboot.user.dto.request.PrivilegeMenuAddRequest;
import com.lc.springboot.user.dto.request.PrivilegeMenuQueryRequest;
import com.lc.springboot.user.dto.request.PrivilegeMenuUpdateRequest;
import com.lc.springboot.user.mapper.PrivilegeMenuMapper;
import com.lc.springboot.user.model.PrivilegeMenu;
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
* 权限对应菜单业务处理类
*
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/
@Service
@Slf4j
public class PrivilegeMenuService extends ServiceImpl<PrivilegeMenuMapper, PrivilegeMenu> {

 @Autowired private ModelMapper modelMapper;
 @Resource private PrivilegeMenuMapper privilegeMenuMapper;

 /**
 * 创建权限对应菜单
 *
 * @param privilegeMenuAddRequest 权限对应菜单新增对象
 * @return 权限对应菜单更新对象
 */
 @Transactional(rollbackFor = Exception.class)
 public PrivilegeMenuUpdateRequest create(PrivilegeMenuAddRequest privilegeMenuAddRequest) {
   PrivilegeMenu privilegeMenu = convertToModel(privilegeMenuAddRequest);

   privilegeMenuMapper.insert(privilegeMenu);
   log.info("创建权限对应菜单,{}", privilegeMenu);

   return convertToDto(privilegeMenu);
 }

 /**
 * 更新权限对应菜单信息
 *
 * @param privilegeMenuUpdateRequest 权限对应菜单更新对象
 * @return 返回更新条数
 */
 @Transactional(rollbackFor = Exception.class)
 public int updatePrivilegeMenu(PrivilegeMenuUpdateRequest privilegeMenuUpdateRequest) {
   // 先取回之前数据
   PrivilegeMenu privilegeMenu = getById(privilegeMenuUpdateRequest.getId());

   // 如果不存在，需要报异常
   if (privilegeMenu == null) {
   throw new ServiceException(ResultCode.RECORD_NOT_FOUND);
   }

   privilegeMenu.setPrivilegeId(privilegeMenuUpdateRequest.getPrivilegeId());
   privilegeMenu.setMenuId(privilegeMenuUpdateRequest.getMenuId());

   return privilegeMenuMapper.updateById(privilegeMenu);
 }

 /**
 * 获取权限对应菜单列表信息
 *
 * @param privilegeMenuQueryRequest 权限对应菜单查询对象
 * @param pageable 分页信息
 * @return 权限对应菜单结果集
 */
 public MyPageInfo<PrivilegeMenu> list(PrivilegeMenuQueryRequest privilegeMenuQueryRequest, Pageable pageable) {
   PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
   QueryWrapper<PrivilegeMenu> mapper = new QueryWrapper(convertToModel(privilegeMenuQueryRequest));
   mapper.ge(privilegeMenuQueryRequest.getQueryStartDate() != null ,PrivilegeMenu.COL_CREATED_TIME,privilegeMenuQueryRequest.getQueryStartDate());
   mapper.le(privilegeMenuQueryRequest.getQueryEndDate()!= null ,PrivilegeMenu.COL_CREATED_TIME,privilegeMenuQueryRequest.getQueryEndDate());
   mapper.orderByDesc(PrivilegeMenu.COL_CREATED_TIME);
   return new MyPageInfo(privilegeMenuMapper.selectList(mapper));
 }

 private PrivilegeMenuUpdateRequest convertToDto(PrivilegeMenu privilegeMenu) {
   return modelMapper.map(privilegeMenu, PrivilegeMenuUpdateRequest.class);
 }

 private PrivilegeMenu convertToModel(PrivilegeMenuAddRequest privilegeMenuAddRequest) {
   return modelMapper.map(privilegeMenuAddRequest, PrivilegeMenu.class);
 }

 private PrivilegeMenu convertToModel(PrivilegeMenuQueryRequest privilegeMenuQueryRequest) {
   return modelMapper.map(privilegeMenuQueryRequest, PrivilegeMenu.class);
 }
}
