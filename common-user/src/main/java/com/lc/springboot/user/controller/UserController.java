package com.lc.springboot.user.controller;

import com.lc.springboot.common.api.BaseBeanResponse;
import com.lc.springboot.common.api.BaseListResponse;
import com.lc.springboot.common.api.BaseResponse;
import com.lc.springboot.common.api.MyPageInfo;
import com.lc.springboot.common.auth.Authorize;
import com.lc.springboot.user.dto.request.UserAddRequest;
import com.lc.springboot.user.dto.request.UserQueryRequest;
import com.lc.springboot.user.dto.request.UserUpdateRequest;
import com.lc.springboot.user.model.User;
import com.lc.springboot.user.service.UserService;
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
 * 用户 控制器
 *
 * @author: liangc
 * @date: 2020-08-17 17:18
 * @version 1.0
 */
@Api(tags = "用户 控制器处理类")
@RestController
@RequestMapping("/v1/user/user")
public class UserController {

  @Autowired UserService userService;

  /**
   * 创建用户
   *
   * @param userAddRequest 用户新增请求封装类
   * @return 用户基本信息
   */
  @ApiOperation("创建用户")
  @PostMapping(path = "/create")
  @Authorize({})
  public BaseBeanResponse<UserUpdateRequest> create(
      @RequestBody @Validated UserAddRequest userAddRequest) {
    UserUpdateRequest userUpdateRequest = userService.create(userAddRequest);
    return new BaseBeanResponse<>(userUpdateRequest);
  }

  /**
   * 查询用户列表
   *
   * @param userQueryRequest 用户查询请求封装类
   * @param page 第几页
   * @param size 每页显示条数
   * @return 用户列表信息，带分页信息
   */
  @ApiOperation("用户列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "第几页"),
    @ApiImplicitParam(name = "size", value = "每页条数")
  })
  @GetMapping(path = "/list/{page}/{size}")
  @Authorize({})
  public BaseListResponse<User> list(
      UserQueryRequest userQueryRequest, @PathVariable int page, @PathVariable int size) {
    Pageable pageable = PageRequest.of(page, size);
    MyPageInfo<User> list = userService.list(userQueryRequest, pageable);
    return new BaseListResponse<>(list);
  }

  /**
   * 根据id获取对应的用户记录信息
   *
   * @param id 用户主键编号
   * @return 用户详情
   */
  @ApiOperation("获取用户详情")
  @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户编号")})
  @GetMapping(path = "/get/{id}")
  @Authorize({})
  public BaseBeanResponse<User> get(@PathVariable Long id) {
    User info = userService.getById(id);
    return new BaseBeanResponse<>(info);
  }

  /**
   * 更新(注意不能所有字段更新)
   *
   * @param userUpdateRequest 用户更新请求封装类
   * @return 用户主键编号
   */
  @ApiOperation("更新用户")
  @PutMapping(path = "/update")
  @Authorize({})
  public BaseResponse update(@RequestBody @Validated UserUpdateRequest userUpdateRequest) {
    userService.updateUser(userUpdateRequest);
    return BaseResponse.success();
  }

  /**
   * 根据主键删除对应的记录
   *
   * @param id 用户主键编号
   * @return 基本应答信息
   */
  @ApiOperation("删除用户-逻辑删除")
  @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
  @DeleteMapping(path = "/delete/{id}")
  @Authorize({})
  public BaseResponse delete(@PathVariable Long id) {
    userService.removeById(id);
    return BaseResponse.success();
  }
}
