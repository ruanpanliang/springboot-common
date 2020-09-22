package com.lc.springboot.common.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lc.springboot.common.mybatisplus.model.BaseModel;
import com.lc.springboot.common.auth.AuthContext;
import com.lc.springboot.common.utils.StringGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 时间填充处理
 *
 * @author liangchao
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

  /** 插入时的填充策略 */
  @Override
  public void insertFill(MetaObject metaObject) {
    // 创建时间
    setFieldValByName(BaseModel.CREATED_TIME, new Date(), metaObject);
    // 更新时间
    setFieldValByName(BaseModel.UPDATED_TIME, new Date(), metaObject);
    // 随机码
    setFieldValByName(BaseModel.RANDOM_CODE, StringGenerator.uuid(), metaObject);
    // 创建人
    setFieldValByName(BaseModel.CREATED_BY, String.valueOf(AuthContext.getUserId()), metaObject);
    // 更新人
    setFieldValByName(BaseModel.UPDATED_BY, String.valueOf(AuthContext.getUserId()), metaObject);
  }

  /** 更新时的填充策略 */
  @Override
  public void updateFill(MetaObject metaObject) {
    // 更新时间
    setFieldValByName(BaseModel.UPDATED_TIME, new Date(), metaObject);
    // 更新人
    setFieldValByName(BaseModel.UPDATED_BY, String.valueOf(AuthContext.getUserId()), metaObject);
  }
}
