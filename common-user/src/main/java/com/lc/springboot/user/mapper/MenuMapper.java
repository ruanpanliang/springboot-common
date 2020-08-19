package com.lc.springboot.user.mapper;

import com.lc.springboot.user.dto.response.MenuLoginDetailResponse;
import com.lc.springboot.user.dto.response.PrivilegeLoginDetailResponse;
import com.lc.springboot.user.dto.response.RoleLoginDetailResponse;
import com.lc.springboot.user.model.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 菜单 Mapper 接口
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
public interface MenuMapper extends BaseMapper<Menu> {

  /**
   * 根据权限ID列表获取相应的菜单列表
   *
   * @param idCollection
   * @return
   */
  List<MenuLoginDetailResponse> getMenuListByPrivilegeIds(
      @Param("idCollection") Collection<Long> idCollection);
}
