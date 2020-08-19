package com.lc.springboot.user.controller;

import com.lc.springboot.user.dto.request.RoleAddRequest;
import com.lc.springboot.user.dto.request.RoleQueryRequest;
import com.lc.springboot.user.dto.request.RoleUpdateRequest;
import com.lc.springboot.user.model.Role;
import com.lc.springboot.user.service.RoleService;
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
* 角色 控制器
*
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/
@Api(tags = "角色 控制器处理类")
@RestController
@RequestMapping("/v1/user/role")
public class RoleController {

    @Autowired RoleService roleService;

    /**
    * 创建角色
    * @param roleAddRequest 角色新增请求封装类
    * @return 角色基本信息
    */
    @ApiOperation("创建角色")
    @PostMapping(path = "/create")
    @Authorize({})
    public BaseBeanResponse<RoleUpdateRequest> create(
        @RequestBody @Validated RoleAddRequest roleAddRequest) {
        RoleUpdateRequest roleUpdateRequest = roleService.create(roleAddRequest);
        return new BaseBeanResponse<>(roleUpdateRequest);
    }

    /**
    * 查询角色列表
    * @param roleQueryRequest 角色查询请求封装类
    * @param page 第几页
    * @param size 每页显示条数
    * @return 角色列表信息，带分页信息
    */
    @ApiOperation("角色列表")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "第几页"),
    @ApiImplicitParam(name = "size", value = "每页条数")
    })
    @GetMapping(path = "/list/{page}/{size}")
    @Authorize({})
    public BaseListResponse<Role> list(
        RoleQueryRequest roleQueryRequest, @PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);
        MyPageInfo<Role> list = roleService.list(roleQueryRequest, pageable);
            return new BaseListResponse<>(list);
    }

    /**
    * 根据id获取对应的角色记录信息
    *
    * @param id 角色主键编号
    * @return 角色详情
    */
    @ApiOperation("获取角色详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "角色编号")})
    @GetMapping(path = "/get/{id}")
    @Authorize({})
    public BaseBeanResponse<Role> get(@PathVariable Long id) {
        Role info = roleService.getById(id);
        return new BaseBeanResponse<>(info);
    }

    /**
    * 更新(注意不能所有字段更新)
    *
    * @param roleUpdateRequest 角色更新请求封装类
    * @return 角色主键编号
    */
    @ApiOperation("更新角色")
    @PutMapping(path = "/update")
    @Authorize({})
    public BaseResponse update(
        @RequestBody @Validated RoleUpdateRequest roleUpdateRequest) {
        roleService.updateRole(roleUpdateRequest);
        return BaseResponse.success();
    }

    /**
    * 根据主键删除对应的记录
    *
    * @param id 角色主键编号
    * @return 基本应答信息
    */
    @ApiOperation("删除角色-逻辑删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
    @DeleteMapping(path = "/delete/{id}")
    @Authorize({})
    public BaseResponse delete(@PathVariable Long id) {
        roleService.removeById(id);
        return BaseResponse.success();
    }
}
