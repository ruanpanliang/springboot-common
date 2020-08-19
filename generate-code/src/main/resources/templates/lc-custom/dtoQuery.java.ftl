package ${package.AddRequest};

<#list table.importPackages as pkg>
import ${pkg};
</#list>
<#if swagger2>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
import java.io.Serializable;
import java.util.Date;
<#if entityLombokModel>
import lombok.*;
</#if>

/**
* ${table.comment!} 查询请求对象
* @author: ${author}
* @date: ${date}
* @version ${version}
*/

<#if entityLombokModel>
@Data
<#--    <#if superEntityClass??>-->
<#--@EqualsAndHashCode(callSuper = true)-->
<#--    <#else>-->
<#--@EqualsAndHashCode-->
<#--    </#if>-->
@Builder
@AllArgsConstructor
@NoArgsConstructor
<#--    <#if chainModel>-->
<#--@Accessors(chain = true)-->
<#--    </#if>-->
</#if>
<#--<#if table.convert>-->
<#--@TableName("${table.name}")-->
<#--</#if>-->
<#if swagger2>
@ApiModel(value="${entity} 查询请求对象", description="${table.comment!} 查询请求实体对象")
</#if>
<#--<#if superEntityClass??>-->
<#--public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {-->
<#--<#elseif activeRecord>-->
<#--public class ${table.dtoUpdateName} extends Model<${entity}> {-->
<#--<#else>-->
public class ${table.dtoQueryName} implements Serializable {
<#--</#if>-->

<#if entitySerialVersionUID>
    private static final long serialVersionUID = 1L;
</#if>

<#-- ----------  加默认主键字段  -------------->
    /** ID */
    @ApiModelProperty(value = "ID", example = "0")
    private Long id;

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.comment!?length gt 0>
        <#if swagger2>
            <#if field.columnType == "INTEGER" || field.columnType == "LONG">
    @ApiModelProperty(value = "${field.comment}", example = "0")
            <#elseif field.columnType == "BIG_DECIMAL">
    @ApiModelProperty(value = "${field.comment}", example = "0.0")
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

    @ApiModelProperty(value = "创建开始时间")
    private Date queryStartDate;

    @ApiModelProperty(value = "创建结束时间")
    private Date queryEndDate;
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
