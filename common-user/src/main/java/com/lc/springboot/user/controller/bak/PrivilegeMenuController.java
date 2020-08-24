// package com.lc.springboot.user.controller;
//
// import com.lc.springboot.user.dto.request.PrivilegeMenuAddRequest;
// import com.lc.springboot.user.dto.request.PrivilegeMenuQueryRequest;
// import com.lc.springboot.user.dto.request.PrivilegeMenuUpdateRequest;
// import com.lc.springboot.user.model.PrivilegeMenu;
// import com.lc.springboot.user.service.PrivilegeMenuService;
// import com.lc.springboot.common.api.BaseBeanResponse;
// import com.lc.springboot.common.api.BaseListResponse;
// import com.lc.springboot.common.api.BaseResponse;
// import com.lc.springboot.common.api.MyPageInfo;
// import com.lc.springboot.common.auth.Authorize;
// import io.swagger.annotations.Api;
// import io.swagger.annotations.ApiImplicitParam;
// import io.swagger.annotations.ApiImplicitParams;
// import io.swagger.annotations.ApiOperation;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.*;
//
// /**
// * 权限对应菜单 控制器
// *
// * @author: liangc
// * @date: 2020-08-17 17:18
// * @version 1.0
// */
// @Api(tags = "权限对应菜单 控制器处理类")
// @RestController
// @RequestMapping("/v1/user/privilegemenu")
// public class PrivilegeMenuController {
//
//     @Autowired PrivilegeMenuService privilegeMenuService;
//
//     /**
//     * 创建权限对应菜单
//     * @param privilegeMenuAddRequest 权限对应菜单新增请求封装类
//     * @return 权限对应菜单基本信息
//     */
//     @ApiOperation("创建权限对应菜单")
//     @PostMapping(path = "/create")
//     @Authorize({})
//     public BaseBeanResponse<PrivilegeMenuUpdateRequest> create(
//         @RequestBody @Validated PrivilegeMenuAddRequest privilegeMenuAddRequest) {
//         PrivilegeMenuUpdateRequest privilegeMenuUpdateRequest = privilegeMenuService.create(privilegeMenuAddRequest);
//         return new BaseBeanResponse<>(privilegeMenuUpdateRequest);
//     }
//
//     /**
//     * 查询权限对应菜单列表
//     * @param privilegeMenuQueryRequest 权限对应菜单查询请求封装类
//     * @param page 第几页
//     * @param size 每页显示条数
//     * @return 权限对应菜单列表信息，带分页信息
//     */
//     @ApiOperation("权限对应菜单列表")
//     @ApiImplicitParams({
//     @ApiImplicitParam(name = "page", value = "第几页"),
//     @ApiImplicitParam(name = "size", value = "每页条数")
//     })
//     @GetMapping(path = "/list/{page}/{size}")
//     @Authorize({})
//     public BaseListResponse<PrivilegeMenu> list(
//         PrivilegeMenuQueryRequest privilegeMenuQueryRequest, @PathVariable int page, @PathVariable int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         MyPageInfo<PrivilegeMenu> list = privilegeMenuService.list(privilegeMenuQueryRequest, pageable);
//             return new BaseListResponse<>(list);
//     }
//
//     /**
//     * 根据id获取对应的权限对应菜单记录信息
//     *
//     * @param id 权限对应菜单主键编号
//     * @return 权限对应菜单详情
//     */
//     @ApiOperation("获取权限对应菜单详情")
//     @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "权限对应菜单编号")})
//     @GetMapping(path = "/get/{id}")
//     @Authorize({})
//     public BaseBeanResponse<PrivilegeMenu> get(@PathVariable Long id) {
//         PrivilegeMenu info = privilegeMenuService.getById(id);
//         return new BaseBeanResponse<>(info);
//     }
//
//     /**
//     * 更新(注意不能所有字段更新)
//     *
//     * @param privilegeMenuUpdateRequest 权限对应菜单更新请求封装类
//     * @return 权限对应菜单主键编号
//     */
//     @ApiOperation("更新权限对应菜单")
//     @PutMapping(path = "/update")
//     @Authorize({})
//     public BaseResponse update(
//         @RequestBody @Validated PrivilegeMenuUpdateRequest privilegeMenuUpdateRequest) {
//         privilegeMenuService.updatePrivilegeMenu(privilegeMenuUpdateRequest);
//         return BaseResponse.success();
//     }
//
//     /**
//     * 根据主键删除对应的记录
//     *
//     * @param id 权限对应菜单主键编号
//     * @return 基本应答信息
//     */
//     @ApiOperation("删除权限对应菜单-逻辑删除")
//     @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
//     @DeleteMapping(path = "/delete/{id}")
//     @Authorize({})
//     public BaseResponse delete(@PathVariable Long id) {
//         privilegeMenuService.removeById(id);
//         return BaseResponse.success();
//     }
// }
