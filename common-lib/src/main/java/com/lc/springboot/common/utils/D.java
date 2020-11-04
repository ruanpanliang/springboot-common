package com.lc.springboot.common.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * 日期工具类
 * @author liangc
 * @version V1.0
 * @date 2020/11/4
 **/
public class D {


    /**
     * 获取当前日期，格式yyyyMMdd
     * @return
     */
    public static String nowPureDate(){
        return DateUtil.format(new Date(), DatePattern.PURE_DATE_FORMAT);
    }

    /**
     * 获取当前日期，格式yyyy-MM-dd
     * @return
     */
    public static String nowNormDate(){
        return DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 获取当前日期，格式HH:mm:ss
     * @return
     */
    public static String nowNormTime(){
        return DateUtil.format(new Date(), DatePattern.NORM_TIME_PATTERN);
    }

}
