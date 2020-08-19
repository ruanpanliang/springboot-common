package com.lc.springboot.user.controller;

import com.lc.springboot.user.dto.request.MenuAddRequest;
import com.lc.springboot.user.dto.request.MenuQueryRequest;
import com.lc.springboot.user.dto.request.MenuUpdateRequest;
import com.lc.springboot.user.model.Menu;
import com.lc.springboot.user.service.MenuService;
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
* 菜单 控制器
*
* @author: liangc
* @date: 2020-08-17 17:18
* @version 1.0
*/
@Api(tags = "菜单 控制器处理类")
@RestController
@RequestMapping("/v1/user/menu")
public class MenuController {

    @Autowired MenuService menuService;

    /**
    * 创建菜单
    * @param menuAddRequest 菜单新增请求封装类
    * @return 菜单基本信息
    */
    @ApiOperation("创建菜单")
    @PostMapping(path = "/create")
    @Authorize({})
    public BaseBeanResponse<MenuUpdateRequest> create(
        @RequestBody @Validated MenuAddRequest menuAddRequest) {
        MenuUpdateRequest menuUpdateRequest = menuService.create(menuAddRequest);
        return new BaseBeanResponse<>(menuUpdateRequest);
    }

    /**
    * 查询菜单列表
    * @param menuQueryRequest 菜单查询请求封装类
    * @param page 第几页
    * @param size 每页显示条数
    * @return 菜单列表信息，带分页信息
    */
    @ApiOperation("菜单列表")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "第几页"),
    @ApiImplicitParam(name = "size", value = "每页条数")
    })
    @GetMapping(path = "/list/{page}/{size}")
    @Authorize({})
    public BaseListResponse<Menu> list(
        MenuQueryRequest menuQueryRequest, @PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);
        MyPageInfo<Menu> list = menuService.list(menuQueryRequest, pageable);
            return new BaseListResponse<>(list);
    }

    /**
    * 根据id获取对应的菜单记录信息
    *
    * @param id 菜单主键编号
    * @return 菜单详情
    */
    @ApiOperation("获取菜单详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "菜单编号")})
    @GetMapping(path = "/get/{id}")
    @Authorize({})
    public BaseBeanResponse<Menu> get(@PathVariable Long id) {
        Menu info = menuService.getById(id);
        return new BaseBeanResponse<>(info);
    }

    /**
    * 更新(注意不能所有字段更新)
    *
    * @param menuUpdateRequest 菜单更新请求封装类
    * @return 菜单主键编号
    */
    @ApiOperation("更新菜单")
    @PutMapping(path = "/update")
    @Authorize({})
    public BaseResponse update(
        @RequestBody @Validated MenuUpdateRequest menuUpdateRequest) {
        menuService.updateMenu(menuUpdateRequest);
        return BaseResponse.success();
    }

    /**
    * 根据主键删除对应的记录
    *
    * @param id 菜单主键编号
    * @return 基本应答信息
    */
    @ApiOperation("删除菜单-逻辑删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
    @DeleteMapping(path = "/delete/{id}")
    @Authorize({})
    public BaseResponse delete(@PathVariable Long id) {
        menuService.removeById(id);
        return BaseResponse.success();
    }
}
