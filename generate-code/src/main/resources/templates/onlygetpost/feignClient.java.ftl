package ${package.FeignClient};

import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.DetailResponse}.${table.dtoDetailName};
import com.lc.springboot.common.api.BaseBeanResponse;
import com.lc.springboot.common.api.BaseListResponse;
import com.lc.springboot.common.api.BaseResponse;
import com.lc.springboot.common.auth.AuthConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* ${table.comment!} FeignClient接口类
* @author: ${author}
* @date: ${date}
* @version ${version}
*/

@FeignClient(
name = ${package.ModuleName?cap_first}FeignClientConst.${package.ModuleName?upper_case}_SERVER_NAME + "-${entity?lower_case}",
path = "${controllerRootMapPath}${entity?lower_case}",
url = "${feignClientEndpoint}")
public interface ${table.feignClientName} {


    /**
    * 创建${table.comment!}
    * @param authz token信息
    * @param ${table.dtoAddName?uncap_first} ${table.comment!}新增请求封装类
    * @return ${table.comment!}基本信息
    */
    @PostMapping(path = "/create")
    BaseBeanResponse<${table.dtoUpdateName}> create(
        @RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz,
        @RequestBody @Validated ${table.dtoAddName} ${table.dtoAddName?uncap_first});

    /**
    * 查询列表群列表
    * @param authz token信息
    * @param ${table.dtoQueryName?uncap_first} ${table.comment!}查询请求封装类
    * @param page 第几页
    * @param size 每页查询条数
    * @return ${table.comment!}基本列表信息，包括分页信息
    */
    @GetMapping(
    path = "/list/{page}/{size}",
    consumes = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
    BaseListResponse<${table.dtoDetailName}> list(
        @RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz,
        @SpringQueryMap ${table.dtoQueryName} ${table.dtoQueryName?uncap_first},
        @PathVariable int page,
        @PathVariable int size);

    /**
    * 获取${table.comment!}详情
    * @param authz token信息
    * @param id ${table.comment!}主键编号
    * @return ${table.comment!}详情
    */
    @GetMapping(path = "/get/{id}")
    BaseBeanResponse<${table.dtoDetailName}> get(
        @RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz, @PathVariable Long id);

    /**
    * 更新${table.comment!}
    * @param authz token信息
    * @param ${table.dtoUpdateName?uncap_first} ${table.comment!}更新请求封装类
    * @return 基本应答信息
    */
    @PutMapping(path = "/update")
    BaseResponse update(
        @RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz,
        @RequestBody @Validated ${table.dtoUpdateName} ${table.dtoUpdateName?uncap_first});

    /**
    * 删除${table.comment!}
    * @param authz token信息
    * @param id ${table.comment!}主键编号
    * @return 基本应答信息
    */
    @DeleteMapping(path = "/delete/{id}")
        BaseResponse delete(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz,@PathVariable Long id);
}
