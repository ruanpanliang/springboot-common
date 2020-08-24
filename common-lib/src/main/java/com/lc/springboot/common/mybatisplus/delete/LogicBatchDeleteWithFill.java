// package com.lc.springboot.common.mybatisplus.delete;
//
// import com.baomidou.mybatisplus.annotation.FieldFill;
// import com.baomidou.mybatisplus.core.enums.SqlMethod;
// import com.baomidou.mybatisplus.core.injector.AbstractMethod;
// import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
// import com.baomidou.mybatisplus.core.metadata.TableInfo;
// import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
// import org.apache.ibatis.mapping.MappedStatement;
// import org.apache.ibatis.mapping.SqlSource;
//
// import java.util.List;
// import java.util.stream.Collectors;
//
// /**
//  * 逻辑删除自动增强处理类
//  *
//  * @author liangchao
//  */
// public class LogicBatchDeleteWithFill extends AbstractMethod {
//   /** mapper 对应的方法名 */
//   private static final String MAPPER_METHOD = "batchDeleteWithFill";
//
//   @Override
//   public MappedStatement injectMappedStatement(
//       Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
//     String sql;
//     SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE;
//     if (tableInfo.isLogicDelete()) {
//       List<TableFieldInfo> fieldInfos =
//           tableInfo.getFieldList().stream()
//               .filter(
//                   i ->
//                       i.getFieldFill() == FieldFill.UPDATE
//                           || i.getFieldFill() == FieldFill.INSERT_UPDATE)
//               .collect(Collectors.toList());
//       if (CollectionUtils.isNotEmpty(fieldInfos)) {
//         String sqlSet =
//             "SET "
//                 + fieldInfos.stream()
//                     .map(i -> i.getSqlSet(ENTITY_DOT))
//                     .collect(Collectors.joining(EMPTY))
//                 + tableInfo.getLogicDeleteSql(false, true);
//         sql =
//             String.format(
//                 sqlMethod.getSql(),
//                 tableInfo.getTableName(),
//                 sqlSet,
//                 sqlWhereEntityWrapper(true, tableInfo));
//       } else {
//         sql =
//             String.format(
//                 sqlMethod.getSql(),
//                 tableInfo.getTableName(),
//                 sqlLogicSet(tableInfo),
//                 sqlWhereEntityWrapper(true, tableInfo));
//       }
//       SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
//       return this.addUpdateMappedStatement(mapperClass, modelClass, MAPPER_METHOD, sqlSource);
//     } else {
//       sqlMethod = SqlMethod.DELETE;
//       sql =
//           String.format(
//               sqlMethod.getSql(), tableInfo.getTableName(), sqlWhereEntityWrapper(true, tableInfo));
//       SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
//       return this.addDeleteMappedStatement(mapperClass, MAPPER_METHOD, sqlSource);
//     }
//   }
// }
