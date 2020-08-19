package ${package.Controller};

import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceImplName};
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
* ${table.comment!} 控制器
*
* @author: ${author}
* @date: ${date}
* @version ${version}
*/
@Api(tags = "${table.comment!} 控制器处理类")
@RestController
@RequestMapping("${controllerRootMapPath}${entity?lower_case}")
public class ${table.controllerName} {

    @Autowired ${table.serviceImplName} ${table.serviceImplName?uncap_first};

    /**
    * 创建${table.comment!}
    * @param ${table.dtoAddName?uncap_first} ${table.comment!}新增请求封装类
    * @return ${table.comment!}基本信息
    */
    @ApiOperation("创建${table.comment!}")
    @PostMapping(path = "/create")
    @Authorize({})
    public BaseBeanResponse<${table.dtoUpdateName}> create(
        @RequestBody @Validated ${entity}AddRequest ${table.dtoAddName?uncap_first}) {
        ${table.dtoUpdateName} ${table.dtoUpdateName?uncap_first} = ${table.serviceImplName?uncap_first}.create(${table.dtoAddName?uncap_first});
        return new BaseBeanResponse<>(${table.dtoUpdateName?uncap_first});
    }

    /**
    * 查询${table.comment!}列表
    * @param ${table.dtoQueryName?uncap_first} ${table.comment!}查询请求封装类
    * @param page 第几页
    * @param size 每页显示条数
    * @return ${table.comment!}列表信息，带分页信息
    */
    @ApiOperation("${table.comment!}列表")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "第几页"),
    @ApiImplicitParam(name = "size", value = "每页条数")
    })
    @GetMapping(path = "/list/{page}/{size}")
    @Authorize({})
    public BaseListResponse<${entity}> list(
        ${entity}QueryRequest ${table.dtoQueryName?uncap_first}, @PathVariable int page, @PathVariable int size) {
        Pageable pageable = PageRequest.of(page, size);
        MyPageInfo<${entity}> list = ${table.serviceImplName?uncap_first}.list(${table.dtoQueryName?uncap_first}, pageable);
            return new BaseListResponse<>(list);
    }

    /**
    * 根据id获取对应的${table.comment!}记录信息
    *
    * @param id ${table.comment!}主键编号
    * @return ${table.comment!}详情
    */
    @ApiOperation("获取${table.comment!}详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "${table.comment!}编号")})
    @GetMapping(path = "/get/{id}")
    @Authorize({})
    public BaseBeanResponse<${entity}> get(@PathVariable Long id) {
        ${entity} info = ${table.serviceImplName?uncap_first}.getById(id);
        return new BaseBeanResponse<>(info);
    }

    /**
    * 更新(注意不能所有字段更新)
    *
    * @param ${table.dtoUpdateName?uncap_first} ${table.comment!}更新请求封装类
    * @return ${table.comment!}主键编号
    */
    @ApiOperation("更新${table.comment!}")
    @PutMapping(path = "/update")
    @Authorize({})
    public BaseResponse update(
        @RequestBody @Validated ${table.dtoUpdateName} ${table.dtoUpdateName?uncap_first}) {
        ${table.serviceImplName?uncap_first}.update${entity}(${table.dtoUpdateName?uncap_first});
        return BaseResponse.success();
    }

    /**
    * 根据主键删除对应的记录
    *
    * @param id ${table.comment!}主键编号
    * @return 基本应答信息
    */
    @ApiOperation("删除${table.comment!}-逻辑删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号")})
    @DeleteMapping(path = "/delete/{id}")
    @Authorize({})
    public BaseResponse delete(@PathVariable Long id) {
        ${table.serviceImplName?uncap_first}.removeById(id);
        return BaseResponse.success();
    }
}
