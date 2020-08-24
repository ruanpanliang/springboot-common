package com.lc.springboot.common.utils;

import java.util.*;

/** @author liangchao */
public class CollectionUtil {

  private CollectionUtil() {}

  /**
   * 找出两个集合中不同的元素
   *
   * @param collmax
   * @param collmin
   * @return
   */
  public static Collection getDifferent(Collection collmax, Collection collmin) {
    // 使用LinkedList防止差异过大时,元素拷贝
    Collection csReturn = new LinkedList();
    Collection max = collmax;
    Collection min = collmin;
    // 先比较大小,这样会减少后续map的if判断次数
    if (collmax.size() < collmin.size()) {
      max = collmin;
      min = collmax;
    }
    // 直接指定大小,防止再散列
    Map<Object, Integer> map = new HashMap<>(max.size());
    for (Object object : max) {
      map.put(object, 1);
    }
    for (Object object : min) {
      if (map.get(object) == null) {
        csReturn.add(object);
      } else {
        map.put(object, 2);
      }
    }
    for (Map.Entry<Object, Integer> entry : map.entrySet()) {
      if (entry.getValue() == 1) {
        csReturn.add(entry.getKey());
      }
    }
    return csReturn;
  }

  /**
   * 找出两个集合中相同的元素
   *
   * @param collmax
   * @param collmin
   * @return
   */
  public static Collection getSame(Collection collmax, Collection collmin) {
    // 使用LinkedList防止差异过大时,元素拷贝
    Collection csReturn = new LinkedList();
    Collection max = collmax;
    Collection min = collmin;
    // 先比较大小,这样会减少后续map的if判断次数
    if (collmax.size() < collmin.size()) {
      max = collmin;
      min = collmax;
    }
    // 直接指定大小,防止再散列
    Map<Object, Integer> map = new HashMap<>(max.size());
    for (Object object : max) {
      map.put(object, 1);
    }
    for (Object object : min) {
      if (map.get(object) != null) {
        csReturn.add(object);
      }
    }
    return csReturn;
  }

  /**
   * 获取两个集合的不同元素,去除重复
   *
   * @param collmax
   * @param collmin
   * @return
   */
  public static Collection getDiffentNoDuplicate(Collection collmax, Collection collmin) {
    return new HashSet(getDifferent(collmax, collmin));
  }
}
