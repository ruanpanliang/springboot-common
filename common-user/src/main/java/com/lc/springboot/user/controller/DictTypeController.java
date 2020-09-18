package com.lc.springboot.user.controller;

import com.lc.springboot.user.dto.request.DictTypeAddRequest;
import com.lc.springboot.user.dto.request.DictTypeQueryRequest;
import com.lc.springboot.user.dto.request.DictTypeUpdateRequest;
import com.lc.springboot.user.model.DictType;
import com.lc.springboot.user.service.DictTypeService;
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
* 字典类型 控制器
*
* @author: liangc
* @date: 2020-09-18 10:34
* @version 1.0
*/
@Api(tags = "字典类型 控制器处理类")
@RestController
@RequestMapping("/v1/user/dicttype")
public class DictTypeController {

    @Autowired DictTypeService dictTypeService;

    /**
    * 创建字典类型
    * @param dictTypeAddRequest 字典类型新增请求封装类
    * @return 字典类型基本信息
    */
    @ApiOperation("创建字典类型")
    @PostMapping(path = "/create")
    @Authorize({})
    public BaseBeanResponse<DictTypeUpdateRequest> create(
        @RequestBody @Validated DictTypeAddRequest dictTypeAddRequest) {
        DictTypeUpdateRequest dictTypeUpdateRequest = dictTypeService.create(dictTypeAddRequest);
        return new BaseBeanResponse<>(dictTypeUpdateRequest);
    }

    /**
    * 查询字典类型列表
    * @param dictTypeQueryRequest 字典类型查询请求封装类
    * @param page 第几页
    * @param size 每页显示条数
    * @return 字典类型列表信息，带分页信息
    */
    @ApiOperation("字典类型列表")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "第几页"),
    @ApiImplicitParam(name = "size", value = "每页条数")
    })
    @GetMapping(path = "/list/{page}/{size}")
    @Authorize({})
    public BaseListResponse<DictType> list(
        DictTypeQueryRequest dictTypeQueryRequest, @PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);
        MyPageInfo<DictType> list = dictTypeService.list(dictTypeQueryRequest, pageable);
            return new BaseListResponse<>(list);
    }

    /**
    * 根据id获取对应的字典类型记录信息
    *
    * @param id 字典类型主键编号
    * @return 字典类型详情
    */
    @ApiOperation("获取字典类型详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "字典类型编号")})
    @GetMapping(path = "/get/{id}")
    @Authorize({})
    public BaseBeanResponse<DictType> get(@PathVariable Long id) {
        DictType info = dictTypeService.getById(id);
        return new BaseBeanResponse<>(info);
    }

    /**
    * 更新(注意不能所有字段更新)
    *
    * @param dictTypeUpdateRequest 字典类型更新请求封装类
    * @return 字典类型主键编号
    */
    @ApiOperation("更新字典类型")
    @PutMapping(path = "/update")
    @Authorize({})
    public BaseResponse update(
        @RequestBody @Validated DictTypeUpdateRequest dictTypeUpdateRequest) {
        dictTypeService.updateDictType(dictTypeUpdateRequest);
        return BaseResponse.success();
    }

    /**
    * 根据主键删除对应的记录
    *
    * @param id 字典类型主键编号
    * @return 基本应答信息
    */
    @ApiOperation("删除字典类型-逻辑删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
    @DeleteMapping(path = "/delete/{id}")
    @Authorize({})
    public BaseResponse delete(@PathVariable Long id) {
        dictTypeService.removeById(id);
        return BaseResponse.success();
    }
}
