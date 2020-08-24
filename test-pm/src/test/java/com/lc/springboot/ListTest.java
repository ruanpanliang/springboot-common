package com.lc.springboot;

import com.lc.springboot.common.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @program: springboot-common
 * @description:
 * @author: liangc
 * @date: 2020-08-24 11:02
 * @version 1.0
 */
public class ListTest {

  public static void main(String[] args) {
    //
    List<Long> aa = new ArrayList<>();
    aa.add(1L);
    aa.add(2L);
    aa.add(4L);

    List<Long> bb = new ArrayList<>();
    bb.add(1L);
    bb.add(2L);
    bb.add(3L);

    Collection same = CollectionUtil.getSame(aa, bb);
    System.out.println(same);
    System.out.println(aa);
    System.out.println(bb);
  }
}
