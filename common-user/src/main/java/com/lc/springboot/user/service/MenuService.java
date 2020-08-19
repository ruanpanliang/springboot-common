package com.lc.springboot.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.springboot.user.dto.request.MenuAddRequest;
import com.lc.springboot.user.dto.request.MenuQueryRequest;
import com.lc.springboot.user.dto.request.MenuUpdateRequest;
import com.lc.springboot.user.mapper.MenuMapper;
import com.lc.springboot.user.model.Menu;
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
* 菜单业务处理类
*
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/
@Service
@Slf4j
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

 @Autowired private ModelMapper modelMapper;
 @Resource private MenuMapper menuMapper;

 /**
 * 创建菜单
 *
 * @param menuAddRequest 菜单新增对象
 * @return 菜单更新对象
 */
 @Transactional(rollbackFor = Exception.class)
 public MenuUpdateRequest create(MenuAddRequest menuAddRequest) {
   Menu menu = convertToModel(menuAddRequest);

   menuMapper.insert(menu);
   log.info("创建菜单,{}", menu);

   return convertToDto(menu);
 }

 /**
 * 更新菜单信息
 *
 * @param menuUpdateRequest 菜单更新对象
 * @return 返回更新条数
 */
 @Transactional(rollbackFor = Exception.class)
 public int updateMenu(MenuUpdateRequest menuUpdateRequest) {
   // 先取回之前数据
   Menu menu = getById(menuUpdateRequest.getId());

   // 如果不存在，需要报异常
   if (menu == null) {
   throw new ServiceException(ResultCode.RECORD_NOT_FOUND);
   }

   menu.setMenuCode(menuUpdateRequest.getMenuCode());
   menu.setMenuName(menuUpdateRequest.getMenuName());
   menu.setMenuType(menuUpdateRequest.getMenuType());
   menu.setMenuIndex(menuUpdateRequest.getMenuIndex());
   menu.setMenuPath(menuUpdateRequest.getMenuPath());
   menu.setMenuIcon(menuUpdateRequest.getMenuIcon());
   menu.setParentId(menuUpdateRequest.getParentId());

   return menuMapper.updateById(menu);
 }

 /**
 * 获取菜单列表信息
 *
 * @param menuQueryRequest 菜单查询对象
 * @param pageable 分页信息
 * @return 菜单结果集
 */
 public MyPageInfo<Menu> list(MenuQueryRequest menuQueryRequest, Pageable pageable) {
   PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
   QueryWrapper<Menu> mapper = new QueryWrapper(convertToModel(menuQueryRequest));
   mapper.ge(menuQueryRequest.getQueryStartDate() != null ,Menu.COL_CREATED_TIME,menuQueryRequest.getQueryStartDate());
   mapper.le(menuQueryRequest.getQueryEndDate()!= null ,Menu.COL_CREATED_TIME,menuQueryRequest.getQueryEndDate());
   mapper.orderByDesc(Menu.COL_CREATED_TIME);
   return new MyPageInfo(menuMapper.selectList(mapper));
 }

 private MenuUpdateRequest convertToDto(Menu menu) {
   return modelMapper.map(menu, MenuUpdateRequest.class);
 }

 private Menu convertToModel(MenuAddRequest menuAddRequest) {
   return modelMapper.map(menuAddRequest, Menu.class);
 }

 private Menu convertToModel(MenuQueryRequest menuQueryRequest) {
   return modelMapper.map(menuQueryRequest, Menu.class);
 }
}
