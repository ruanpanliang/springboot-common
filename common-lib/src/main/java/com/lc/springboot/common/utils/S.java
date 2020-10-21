/*
 * Copyright (c) 2015-2020, www.dibo.ltd (service@dibo.ltd).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.lc.springboot.common.utils;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/***
 * String 操作类
 * @author mazc@dibo.ltd
 * @version v2.0
 * @date 2019/01/01
 */
public class S extends StringUtils{
    /***
     * 默认分隔符 ,
     */
    public static final String SEPARATOR = ",";

    /***
     * 裁剪字符串，显示前部分+...
     * @param input
     * @return
     */
    public static String cut(String input){
        return cut(input, 20);
    }

    /***
     * 裁剪字符串，显示前部分+...
     * @param input
     * @return
     */
    public static String cut(String input, int cutLength){
        return substring(input, 0, cutLength);
    }

    /***
     * 将list拼接成string，默认分隔符:,
     * @param stringList
     * @return
     */
    public static String join(List<String> stringList){
        return StringUtils.join(stringList, SEPARATOR);
    }

    /***
     * 将list拼接成string，默认分隔符:,
     * @param stringArray
     * @return
     */
    public static String join(String[] stringArray){
        return StringUtils.join(stringArray, SEPARATOR);
    }

    /***
     * 按,拆分字符串
     * @param joinedStr
     * @return
     */
    public static String[] split(String joinedStr){
        if(joinedStr == null){
            return null;
        }
        return joinedStr.split(SEPARATOR);
    }

    /***
     * 转换为String数组（避免转型异常）
     * @param stringList
     * @return
     */
    public static String[] toStringArray(List<String> stringList){
        return stringList.toArray(new String[stringList.size()]);
    }

    /***
     * 转换为Long类型（判空，避免NPE）
     * @param strValue
     * @return
     */
    public static Long toLong(String strValue){
        return toLong(strValue, null);
    }

    /***
     * 转换为Long类型（判空，避免NPE）
     * @param strValue 字符类型值
     * @param defaultLong 默认值
     * @return
     */
    public static Long toLong(String strValue, Long defaultLong){
        if(ObjectUtil.isEmpty(strValue)){
            return defaultLong;
        }
        return Long.parseLong(strValue);
    }

    /***
     * 转换为Integer类型(判空，避免NPE)
     * @param strValue
     * @return
     */
    public static Integer toInt(String strValue){
        return toInt(strValue, null);
    }

    /***
     * 转换为Integer类型(判空，避免NPE)
     * @param strValue
     * @param defaultInt 默认值
     * @return
     */
    public static Integer toInt(String strValue, Integer defaultInt){
        if(ObjectUtil.isEmpty(strValue)){
            return defaultInt;
        }
        return Integer.parseInt(strValue);
    }

    /***
     * 将多个空格替换为一个
     * @param input
     * @return
     */
    public static String removeDuplicateBlank(String input){
        if(ObjectUtil.isEmpty(input)){
            return input;
        }
        return input.trim().replaceAll(" +", " ");
    }

    /**
     * 获得随机串
     * @return
     */
    public static String newUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /***
     * 将object转换为字符串
     * @param o
     * @return
     */
    public static String valueOf(Object o) {
        if (o == null){
            return null;
        }
        return String.valueOf(o);
    }

    /***
     * 生成指定位数的数字/验证码
     */
    private static final String NUMBER_SET = "12345678901";
    private static Random random = new Random();
    public static String newRandomNum(int length){
        StringBuilder sb = new StringBuilder();
        sb.append(NUMBER_SET.charAt(random.nextInt(9)));
        for(int i=1; i<length; i++){
            sb.append(NUMBER_SET.charAt(random.nextInt(10)));
        }
        return sb.toString();
    }

    /***
     * 将首字母转为小写
     * @return
     */
    public static String uncapFirst(String input){
        if(input != null){
            return Character.toLowerCase(input.charAt(0)) + input.substring(1);
        }
        return null;
    }

    /***
     * 将首字母转为大写
     * @return
     */
    public static String capFirst(String input){
        if(input != null){
            return Character.toUpperCase(input.charAt(0)) + input.substring(1);
        }
        return null;
    }

}
