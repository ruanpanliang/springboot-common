package com.lc.springboot.common.mybatisplus.handler;

import org.apache.ibatis.executor.result.ResultMapException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将null转换成空字符串
 *
 * @author liangchao
 */
@MappedTypes({String.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class CustomStringTypeHandler extends BaseTypeHandler<String> {

  @Override
  public String getResult(ResultSet rs, String columnName) {
    String result;
    try {
      result = getNullableResult(rs, columnName);
    } catch (Exception e) {
      throw new ResultMapException(
          "Error attempting to get column '" + columnName + "' from result set.  Cause: " + e, e);
    }
    return result;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, parameter);
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return (rs.getString(columnName) == null || "null".equalsIgnoreCase(rs.getString(columnName)))
        ? ""
        : rs.getString(columnName);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return (rs.getString(columnIndex) == null || "null".equalsIgnoreCase(rs.getString(columnIndex)))
        ? ""
        : rs.getString(columnIndex);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return (cs.getString(columnIndex) == null || "null".equalsIgnoreCase(cs.getString(columnIndex)))
        ? ""
        : cs.getString(columnIndex);
  }
}
