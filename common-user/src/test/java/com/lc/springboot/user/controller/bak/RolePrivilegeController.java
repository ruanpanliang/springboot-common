// package com.lc.springboot.user.controller;
//
// import com.lc.springboot.user.dto.request.RolePrivilegeAddRequest;
// import com.lc.springboot.user.dto.request.RolePrivilegeQueryRequest;
// import com.lc.springboot.user.dto.request.RolePrivilegeUpdateRequest;
// import com.lc.springboot.user.model.RolePrivilege;
// import com.lc.springboot.user.service.RolePrivilegeService;
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
// * 角色对应权限 控制器
// *
// * @author: liangc
// * @date: 2020-08-17 17:18
// * @version 1.0
// */
// @Api(tags = "角色对应权限 控制器处理类")
// @RestController
// @RequestMapping("/v1/user/roleprivilege")
// public class RolePrivilegeController {
//
//     @Autowired RolePrivilegeService rolePrivilegeService;
//
//     /**
//     * 创建角色对应权限
//     * @param rolePrivilegeAddRequest 角色对应权限新增请求封装类
//     * @return 角色对应权限基本信息
//     */
//     @ApiOperation("创建角色对应权限")
//     @PostMapping(path = "/create")
//     @Authorize({})
//     public BaseBeanResponse<RolePrivilegeUpdateRequest> create(
//         @RequestBody @Validated RolePrivilegeAddRequest rolePrivilegeAddRequest) {
//         RolePrivilegeUpdateRequest rolePrivilegeUpdateRequest = rolePrivilegeService.create(rolePrivilegeAddRequest);
//         return new BaseBeanResponse<>(rolePrivilegeUpdateRequest);
//     }
//
//     /**
//     * 查询角色对应权限列表
//     * @param rolePrivilegeQueryRequest 角色对应权限查询请求封装类
//     * @param page 第几页
//     * @param size 每页显示条数
//     * @return 角色对应权限列表信息，带分页信息
//     */
//     @ApiOperation("角色对应权限列表")
//     @ApiImplicitParams({
//     @ApiImplicitParam(name = "page", value = "第几页"),
//     @ApiImplicitParam(name = "size", value = "每页条数")
//     })
//     @GetMapping(path = "/list/{page}/{size}")
//     @Authorize({})
//     public BaseListResponse<RolePrivilege> list(
//         RolePrivilegeQueryRequest rolePrivilegeQueryRequest, @PathVariable int page, @PathVariable int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         MyPageInfo<RolePrivilege> list = rolePrivilegeService.list(rolePrivilegeQueryRequest, pageable);
//             return new BaseListResponse<>(list);
//     }
//
//     /**
//     * 根据id获取对应的角色对应权限记录信息
//     *
//     * @param id 角色对应权限主键编号
//     * @return 角色对应权限详情
//     */
//     @ApiOperation("获取角色对应权限详情")
//     @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "角色对应权限编号")})
//     @GetMapping(path = "/get/{id}")
//     @Authorize({})
//     public BaseBeanResponse<RolePrivilege> get(@PathVariable Long id) {
//         RolePrivilege info = rolePrivilegeService.getById(id);
//         return new BaseBeanResponse<>(info);
//     }
//
//     /**
//     * 更新(注意不能所有字段更新)
//     *
//     * @param rolePrivilegeUpdateRequest 角色对应权限更新请求封装类
//     * @return 角色对应权限主键编号
//     */
//     @ApiOperation("更新角色对应权限")
//     @PutMapping(path = "/update")
//     @Authorize({})
//     public BaseResponse update(
//         @RequestBody @Validated RolePrivilegeUpdateRequest rolePrivilegeUpdateRequest) {
//         rolePrivilegeService.updateRolePrivilege(rolePrivilegeUpdateRequest);
//         return BaseResponse.success();
//     }
//
//     /**
//     * 根据主键删除对应的记录
//     *
//     * @param id 角色对应权限主键编号
//     * @return 基本应答信息
//     */
//     @ApiOperation("删除角色对应权限-逻辑删除")
//     @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
//     @DeleteMapping(path = "/delete/{id}")
//     @Authorize({})
//     public BaseResponse delete(@PathVariable Long id) {
//         rolePrivilegeService.removeById(id);
//         return BaseResponse.success();
//     }
// }
