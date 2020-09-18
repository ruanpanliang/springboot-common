package com.lc.springboot.user.controller;

import com.lc.springboot.user.dto.request.DictAddRequest;
import com.lc.springboot.user.dto.request.DictQueryRequest;
import com.lc.springboot.user.dto.request.DictUpdateRequest;
import com.lc.springboot.user.model.Dict;
import com.lc.springboot.user.service.DictService;
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
* 字典 控制器
*
* @author: liangc
* @date: 2020-09-18 10:34
* @version 1.0
*/
@Api(tags = "字典 控制器处理类")
@RestController
@RequestMapping("/v1/user/dict")
public class DictController {

    @Autowired DictService dictService;

    /**
    * 创建字典
    * @param dictAddRequest 字典新增请求封装类
    * @return 字典基本信息
    */
    @ApiOperation("创建字典")
    @PostMapping(path = "/create")
    @Authorize({})
    public BaseBeanResponse<DictUpdateRequest> create(
        @RequestBody @Validated DictAddRequest dictAddRequest) {
        DictUpdateRequest dictUpdateRequest = dictService.create(dictAddRequest);
        return new BaseBeanResponse<>(dictUpdateRequest);
    }

    /**
    * 查询字典列表
    * @param dictQueryRequest 字典查询请求封装类
    * @param page 第几页
    * @param size 每页显示条数
    * @return 字典列表信息，带分页信息
    */
    @ApiOperation("字典列表")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "第几页"),
    @ApiImplicitParam(name = "size", value = "每页条数")
    })
    @GetMapping(path = "/list/{page}/{size}")
    @Authorize({})
    public BaseListResponse<Dict> list(
        DictQueryRequest dictQueryRequest, @PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);
        MyPageInfo<Dict> list = dictService.list(dictQueryRequest, pageable);
            return new BaseListResponse<>(list);
    }

    /**
    * 根据id获取对应的字典记录信息
    *
    * @param id 字典主键编号
    * @return 字典详情
    */
    @ApiOperation("获取字典详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "字典编号")})
    @GetMapping(path = "/get/{id}")
    @Authorize({})
    public BaseBeanResponse<Dict> get(@PathVariable Long id) {
        Dict info = dictService.getById(id);
        return new BaseBeanResponse<>(info);
    }

    /**
    * 更新(注意不能所有字段更新)
    *
    * @param dictUpdateRequest 字典更新请求封装类
    * @return 字典主键编号
    */
    @ApiOperation("更新字典")
    @PutMapping(path = "/update")
    @Authorize({})
    public BaseResponse update(
        @RequestBody @Validated DictUpdateRequest dictUpdateRequest) {
        dictService.updateDict(dictUpdateRequest);
        return BaseResponse.success();
    }

    /**
    * 根据主键删除对应的记录
    *
    * @param id 字典主键编号
    * @return 基本应答信息
    */
    @ApiOperation("删除字典-逻辑删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
    @DeleteMapping(path = "/delete/{id}")
    @Authorize({})
    public BaseResponse delete(@PathVariable Long id) {
        dictService.removeById(id);
        return BaseResponse.success();
    }
}
