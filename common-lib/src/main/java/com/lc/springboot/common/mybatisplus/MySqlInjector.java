// package com.lc.springboot.common.mybatisplus;
//
// import com.baomidou.mybatisplus.core.injector.AbstractMethod;
// import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
// import com.baomidou.mybatisplus.extension.injector.methods.LogicDeleteByIdWithFill;
// import com.lc.springboot.common.mybatisplus.delete.LogicBatchDeleteWithFill;
//
// import java.util.List;
//
// /**
//  * sql增强器
//  *
//  * @author liangchao
//  */
// public class MySqlInjector extends DefaultSqlInjector {
//
//   @Override
//   public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
//     List<AbstractMethod> methodList = super.getMethodList(mapperClass);
//     methodList.add(new LogicDeleteByIdWithFill());
//     methodList.add(new LogicBatchDeleteWithFill());
//     return methodList;
//   }
// }
