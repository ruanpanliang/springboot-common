package com.lc.springboot.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.lc.springboot.common.api.MyPageInfo;
import com.lc.springboot.common.api.ResultCode;
import com.lc.springboot.common.error.ServiceException;
import com.lc.springboot.common.utils.CollectionUtil;
import com.lc.springboot.user.dto.request.PrivilegeAddRequest;
import com.lc.springboot.user.dto.request.PrivilegeQueryRequest;
import com.lc.springboot.user.dto.request.PrivilegeUpdateRequest;
import com.lc.springboot.user.mapper.PrivilegeMapper;
import com.lc.springboot.user.mapper.PrivilegeMenuMapper;
import com.lc.springboot.user.model.Privilege;
import com.lc.springboot.user.model.PrivilegeMenu;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
  @Autowired private PrivilegeMenuService privilegeMenuService;
  @Resource private PrivilegeMenuMapper privilegeMenuMapper;

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

    // 创建对应的菜单列表
    List<PrivilegeMenu> privilegeMenuList = new ArrayList<>(privilegeAddRequest.getMenuId().size());
    for (Long menuId : privilegeAddRequest.getMenuId()) {
      if (menuId == 0L) {
        continue;
      }
      PrivilegeMenu privilegeMenu = new PrivilegeMenu(privilege.getId(), menuId);
      privilegeMenuList.add(privilegeMenu);
    }
    privilegeMenuService.saveBatch(privilegeMenuList);

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

    int update = privilegeMapper.updateById(privilege);

    // 更新与菜单的关系
    QueryWrapper queryWrapper = new QueryWrapper();
    queryWrapper.eq(PrivilegeMenu.COL_PRIVILEGE_ID, privilegeUpdateRequest.getId());
    List<PrivilegeMenu> privilegeMenuList = privilegeMenuMapper.selectList(queryWrapper);

    List<Long> priviMenuIdList =
        privilegeMenuList.stream()
            .map(privilegeMenu -> privilegeMenu.getMenuId())
            .collect(Collectors.toList());

    Collection sameList =
        CollectionUtil.getSame(privilegeUpdateRequest.getMenuId(), priviMenuIdList);

    // 删除不需要的
    priviMenuIdList.removeAll(sameList);
    if (priviMenuIdList.size() > 0) {
      queryWrapper.clear();
      queryWrapper.eq(PrivilegeMenu.COL_PRIVILEGE_ID, privilegeUpdateRequest.getId());
      queryWrapper.in(PrivilegeMenu.COL_MENU_ID, priviMenuIdList);
      privilegeMenuMapper.delete(queryWrapper);
    }

    // 新增新加的
    privilegeUpdateRequest.getMenuId().removeAll(sameList);
    privilegeMenuList.clear();
    if (privilegeUpdateRequest.getMenuId().size() > 0) {
      // 创建对应的菜单列表
      for (Long menuId : privilegeUpdateRequest.getMenuId()) {
        if (menuId == 0L) {
          continue;
        }
        PrivilegeMenu privilegeMenu = new PrivilegeMenu(privilege.getId(), menuId);
        privilegeMenuList.add(privilegeMenu);
      }
      privilegeMenuService.saveBatch(privilegeMenuList);
    }

    return update;
  }

  /**
   * 获取权限列表信息
   *
   * @param privilegeQueryRequest 权限查询对象
   * @param pageable 分页信息
   * @return 权限结果集
   */
  public MyPageInfo<Privilege> list(
      PrivilegeQueryRequest privilegeQueryRequest, Pageable pageable) {
    PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
    QueryWrapper<Privilege> mapper = new QueryWrapper(convertToModel(privilegeQueryRequest));
    mapper.ge(
        privilegeQueryRequest.getQueryStartDate() != null,
        Privilege.COL_CREATED_TIME,
        privilegeQueryRequest.getQueryStartDate());
    mapper.le(
        privilegeQueryRequest.getQueryEndDate() != null,
        Privilege.COL_CREATED_TIME,
        privilegeQueryRequest.getQueryEndDate());
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
