package ${package.DetailResponse};

<#list table.importPackages as pkg>
    <#if pkg != "com.baomidou.mybatisplus.annotation.TableName"
    && pkg != "com.baomidou.mybatisplus.annotation.TableField">
import ${pkg};
    </#if>
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
<#if entityLombokModel>
import lombok.*;
</#if>

/**
* ${table.comment!} 详情返回对象
* @author: ${author}
* @date: ${date}
* @version ${version}
*/

<#if entityLombokModel>
@Getter
@Setter
<#--    <#if chainModel>-->
<#--@Accessors(chain = true)-->
<#--    </#if>-->
</#if>
<#--<#if table.convert>-->
<#--@TableName("${table.name}")-->
<#--</#if>-->
<#if swagger2>
@ApiModel(value="${entity} 详情返回对象", description="${table.comment!} 详情返回实体对象")
</#if>
<#--<#if superEntityClass??>-->
<#--public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {-->
<#--<#elseif activeRecord>-->
<#--public class ${table.dtoUpdateName} extends Model<${entity}> {-->
<#--<#else>-->
<#if superEntityClass??>
public class ${table.dtoDetailName} extends ${superEntityClass} {
<#else>
public class ${table.dtoDetailName} implements Serializable {
</#if>
<#--</#if>-->

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
            <#if field.columnType == "INTEGER" || field.columnType == "LONG">
    @ApiModelProperty(value = "${field.comment}")
            <#elseif field.columnType == "BIG_DECIMAL">
    @ApiModelProperty(value = "${field.comment}")
            <#else>
    @ApiModelProperty(value = "${field.comment}")
            </#if>
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
<#--    <#if field.columnType== "STRING">-->
<#--    @NotBlank(message = "${field.comment}不能为空")-->
<#--    <#elseif field.columnType== "LONG">-->
<#--    @NotNull(message = "${field.comment}不能为空")-->
<#--    <#elseif field.columnType== "INT">-->
<#--    @NotNull(message = "${field.comment}不能为空")-->
<#--    <#elseif field.columnType== "DATE">-->
<#--    @NotNull(message = "${field.comment}不能为空")-->
<#--    <#else>-->
<#--    @NotNull(message = "${field.comment}不能为空")-->
<#--    </#if>-->
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
    }

    <#if chainModel>
    public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    <#else>
    public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
    </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if chainModel>
        return this;
        </#if>
    }
    </#list>
</#if>

<#if !entityLombokModel>
    @Override
    public String toString() {
        return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName}=" + ${field.propertyName} +
        <#else>
            ", ${field.propertyName}=" + ${field.propertyName} +
        </#if>
    </#list>
        "}";
    }
</#if>
}
