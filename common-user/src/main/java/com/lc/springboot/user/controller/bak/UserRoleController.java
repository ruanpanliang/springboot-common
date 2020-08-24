// package com.lc.springboot.user.controller;
//
// import com.lc.springboot.user.dto.request.UserRoleAddRequest;
// import com.lc.springboot.user.dto.request.UserRoleQueryRequest;
// import com.lc.springboot.user.dto.request.UserRoleUpdateRequest;
// import com.lc.springboot.user.model.UserRole;
// import com.lc.springboot.user.service.UserRoleService;
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
// * 用户对应角色 控制器
// *
// * @author: liangc
// * @date: 2020-08-17 17:18
// * @version 1.0
// */
// @Api(tags = "用户对应角色 控制器处理类")
// @RestController
// @RequestMapping("/v1/user/userrole")
// public class UserRoleController {
//
//     @Autowired UserRoleService userRoleService;
//
//     /**
//     * 创建用户对应角色
//     * @param userRoleAddRequest 用户对应角色新增请求封装类
//     * @return 用户对应角色基本信息
//     */
//     @ApiOperation("创建用户对应角色")
//     @PostMapping(path = "/create")
//     @Authorize({})
//     public BaseBeanResponse<UserRoleUpdateRequest> create(
//         @RequestBody @Validated UserRoleAddRequest userRoleAddRequest) {
//         UserRoleUpdateRequest userRoleUpdateRequest = userRoleService.create(userRoleAddRequest);
//         return new BaseBeanResponse<>(userRoleUpdateRequest);
//     }
//
//     /**
//     * 查询用户对应角色列表
//     * @param userRoleQueryRequest 用户对应角色查询请求封装类
//     * @param page 第几页
//     * @param size 每页显示条数
//     * @return 用户对应角色列表信息，带分页信息
//     */
//     @ApiOperation("用户对应角色列表")
//     @ApiImplicitParams({
//     @ApiImplicitParam(name = "page", value = "第几页"),
//     @ApiImplicitParam(name = "size", value = "每页条数")
//     })
//     @GetMapping(path = "/list/{page}/{size}")
//     @Authorize({})
//     public BaseListResponse<UserRole> list(
//         UserRoleQueryRequest userRoleQueryRequest, @PathVariable int page, @PathVariable int size) {
//         Pageable pageable = PageRequest.of(page, size);
//         MyPageInfo<UserRole> list = userRoleService.list(userRoleQueryRequest, pageable);
//             return new BaseListResponse<>(list);
//     }
//
//     /**
//     * 根据id获取对应的用户对应角色记录信息
//     *
//     * @param id 用户对应角色主键编号
//     * @return 用户对应角色详情
//     */
//     @ApiOperation("获取用户对应角色详情")
//     @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户对应角色编号")})
//     @GetMapping(path = "/get/{id}")
//     @Authorize({})
//     public BaseBeanResponse<UserRole> get(@PathVariable Long id) {
//         UserRole info = userRoleService.getById(id);
//         return new BaseBeanResponse<>(info);
//     }
//
//     /**
//     * 更新(注意不能所有字段更新)
//     *
//     * @param userRoleUpdateRequest 用户对应角色更新请求封装类
//     * @return 用户对应角色主键编号
//     */
//     @ApiOperation("更新用户对应角色")
//     @PutMapping(path = "/update")
//     @Authorize({})
//     public BaseResponse update(
//         @RequestBody @Validated UserRoleUpdateRequest userRoleUpdateRequest) {
//         userRoleService.updateUserRole(userRoleUpdateRequest);
//         return BaseResponse.success();
//     }
//
//     /**
//     * 根据主键删除对应的记录
//     *
//     * @param id 用户对应角色主键编号
//     * @return 基本应答信息
//     */
//     @ApiOperation("删除用户对应角色-逻辑删除")
//     @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
//     @DeleteMapping(path = "/delete/{id}")
//     @Authorize({})
//     public BaseResponse delete(@PathVariable Long id) {
//         userRoleService.removeById(id);
//         return BaseResponse.success();
//     }
// }
