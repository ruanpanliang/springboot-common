package ${package.Service};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ${superServiceImplClassPackage};
import ${package.AddRequest}.${table.dtoAddName};
import ${package.AddRequest}.${table.dtoQueryName};
import ${package.AddRequest}.${table.dtoUpdateName};
import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
import com.lc.springboot.common.api.MyPageInfo;
import com.lc.springboot.common.api.ResultCode;
import com.lc.springboot.common.error.ServiceException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* ${table.comment!}业务处理类
*
* @author: ${author}
* @date: ${date}
* @version ${version}
*/
@Service
@Slf4j
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> {

 @Autowired private ModelMapper modelMapper;
 @Resource private ${table.mapperName} ${table.mapperName?uncap_first};

 /**
 * 创建${table.comment!}
 *
 * @param ${table.dtoAddName?uncap_first} ${table.comment!}新增对象
 * @return ${table.comment!}更新对象
 */
 @Transactional(rollbackFor = Exception.class)
 public ${table.dtoUpdateName} create(${table.dtoAddName} ${table.dtoAddName?uncap_first}) {
   ${entity} ${entity?uncap_first} = convertToModel(${table.dtoAddName?uncap_first});

   ${table.mapperName?uncap_first}.insert(${entity?uncap_first});
   log.info("创建${table.comment!},{}", ${entity?uncap_first});

   return convertToDto(${entity?uncap_first});
 }

 /**
 * 更新${table.comment!}信息
 *
 * @param ${table.dtoUpdateName?uncap_first} ${table.comment!}更新对象
 * @return 返回更新条数
 */
 @Transactional(rollbackFor = Exception.class)
 public int update${entity}(${table.dtoUpdateName} ${table.dtoUpdateName?uncap_first}) {
   // 先取回之前数据
   ${entity} ${entity?uncap_first} = getById(${table.dtoUpdateName?uncap_first}.getId());

   // 如果不存在，需要报异常
   if (${entity?uncap_first} == null) {
   throw new ServiceException(ResultCode.RECORD_NOT_FOUND);
   }

 <#list table.fields as field>
   ${entity?uncap_first}.set${field.propertyName?cap_first}(${table.dtoUpdateName?uncap_first}.get${field.propertyName?cap_first}());
 </#list>

   return ${table.mapperName?uncap_first}.updateById(${entity?uncap_first});
 }

 /**
 * 获取${table.comment!}列表信息
 *
 * @param ${table.dtoQueryName?uncap_first} ${table.comment!}查询对象
 * @param pageable 分页信息
 * @return ${table.comment!}结果集
 */
 public MyPageInfo<${entity}> list(${table.dtoQueryName} ${table.dtoQueryName?uncap_first}, Pageable pageable) {
   PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
   QueryWrapper<${entity}> mapper = new QueryWrapper(convertToModel(${table.dtoQueryName?uncap_first}));
   mapper.ge(${table.dtoQueryName?uncap_first}.getQueryStartDate() != null ,${entity}.COL_CREATED_TIME,${table.dtoQueryName?uncap_first}.getQueryStartDate());
   mapper.le(${table.dtoQueryName?uncap_first}.getQueryEndDate()!= null ,${entity}.COL_CREATED_TIME,${table.dtoQueryName?uncap_first}.getQueryEndDate());
   mapper.orderByDesc(${entity}.COL_CREATED_TIME);
   return new MyPageInfo(${table.mapperName?uncap_first}.selectList(mapper));
 }

 private ${table.dtoUpdateName} convertToDto(${entity} ${entity?uncap_first}) {
   return modelMapper.map(${entity?uncap_first}, ${table.dtoUpdateName}.class);
 }

 private ${entity} convertToModel(${table.dtoAddName} ${table.dtoAddName?uncap_first}) {
   return modelMapper.map(${table.dtoAddName?uncap_first}, ${entity}.class);
 }

 private ${entity} convertToModel(${table.dtoQueryName} ${table.dtoQueryName?uncap_first}) {
   return modelMapper.map(${table.dtoQueryName?uncap_first}, ${entity}.class);
 }
}
