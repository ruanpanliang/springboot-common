package com.lc.springboot.user.controller;

import com.lc.springboot.user.dto.request.PrivilegeAddRequest;
import com.lc.springboot.user.dto.request.PrivilegeQueryRequest;
import com.lc.springboot.user.dto.request.PrivilegeUpdateRequest;
import com.lc.springboot.user.model.Privilege;
import com.lc.springboot.user.service.PrivilegeService;
import com.lc.springboot.common.api.BaseBeanResponse;
import com.lc.springboot.common.api.BaseListResponse;
import com.lc.springboot.common.api.BaseResponse;
import com.lc.springboot.common.api.MyPageInfo;
import com.lc.springboot.common.auth.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 权限 控制器
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@Api(tags = "权限 控制器处理类")
@RestController
@RequestMapping("/v1/user/privilege")
public class PrivilegeController {

  @Autowired PrivilegeService privilegeService;

  /**
   * 创建权限
   *
   * @param privilegeAddRequest 权限新增请求封装类
   * @return 权限基本信息
   */
  @ApiOperation("创建权限")
  @PostMapping(path = "/create")
  @Authorize({})
  public BaseBeanResponse<PrivilegeUpdateRequest> create(
      @RequestBody @Validated PrivilegeAddRequest privilegeAddRequest) {
    PrivilegeUpdateRequest privilegeUpdateRequest = privilegeService.create(privilegeAddRequest);
    return new BaseBeanResponse<>(privilegeUpdateRequest);
  }

  /**
   * 查询权限列表
   *
   * @param privilegeQueryRequest 权限查询请求封装类
   * @param page 第几页
   * @param size 每页显示条数
   * @return 权限列表信息，带分页信息
   */
  @ApiOperation("权限列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "第几页"),
    @ApiImplicitParam(name = "size", value = "每页条数")
  })
  @GetMapping(path = "/list/{page}/{size}")
  @Authorize({})
  public BaseListResponse<Privilege> list(
      PrivilegeQueryRequest privilegeQueryRequest, @PathVariable int page, @PathVariable int size) {
    Pageable pageable = PageRequest.of(page, size);
    MyPageInfo<Privilege> list = privilegeService.list(privilegeQueryRequest, pageable);
    return new BaseListResponse<>(list);
  }

  /**
   * 根据id获取对应的权限记录信息
   *
   * @param id 权限主键编号
   * @return 权限详情
   */
  @ApiOperation("获取权限详情")
  @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "权限编号")})
  @GetMapping(path = "/get/{id}")
  @Authorize({})
  public BaseBeanResponse<Privilege> get(@PathVariable Long id) {
    Privilege info = privilegeService.getById(id);
    return new BaseBeanResponse<>(info);
  }

  /**
   * 更新(注意不能所有字段更新)
   *
   * @param privilegeUpdateRequest 权限更新请求封装类
   * @return 权限主键编号
   */
  @ApiOperation("更新权限")
  @PutMapping(path = "/update")
  @Authorize({})
  public BaseResponse update(
      @RequestBody @Validated PrivilegeUpdateRequest privilegeUpdateRequest) {
    privilegeService.updatePrivilege(privilegeUpdateRequest);
    return BaseResponse.success();
  }

  /**
   * 根据主键删除对应的记录
   *
   * @param id 权限主键编号
   * @return 基本应答信息
   */
  @ApiOperation("删除权限-逻辑删除")
  @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
  @DeleteMapping(path = "/delete/{id}")
  @Authorize({})
  public BaseResponse delete(@PathVariable Long id) {
    privilegeService.removeById(id);
    return BaseResponse.success();
  }
}
