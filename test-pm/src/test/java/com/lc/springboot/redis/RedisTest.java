package com.lc.springboot.redis;

import cn.hutool.core.date.DateBetween;
import cn.hutool.core.date.DateUnit;
import com.lc.springboot.common.redis.util.RedisUtil;
import com.lc.springboot.common.utils.StringGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/** 测试redis的基本功能 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
@Slf4j
public class RedisTest {

  @Autowired
  RedisUtil redisUtil;

  @Before
  public void setUp() {}

  @Test
  public void testGetHelloKey() {
    // 新建一个key
    String value = "world" + System.currentTimeMillis();
    boolean hello = redisUtil.set("hello1", value);
    assertEquals(true, hello);

    // 获取对应的key
    Object getHelloKeyValue = redisUtil.get("hello1");
    assertEquals(value, getHelloKeyValue);
  }

  /** 向redis串行多次存放数据 */
  @Test
  public void testListRightPush() {
    Date date = new Date();
    for (int i = 0; i < 1000; i++) {
      redisUtil.lRightPush("hxz-test:" + StringGenerator.uuid(), System.currentTimeMillis());
    }
    log.info("耗时：" + new DateBetween(date, new Date()).between(DateUnit.MS) + " 毫秒");
  }

  /** 向指定队列批量新增数据 */
  @Test
  public void testListRightPushAll() {
    List<Object> collect =
        IntStream.range(0, 300)
            .mapToObj(i -> (StringGenerator.uuid() + i))
            .collect(Collectors.toList());
    Date date = new Date();
    redisUtil.lRightPushAll("hxz-test-list:" + StringGenerator.uuid(), collect);
    log.info("耗时：" + new DateBetween(date, new Date()).between(DateUnit.MS));
  }

  @Test
  public void testListRightPushAllWithExpireTime() {
    String key = "hxz-test-list:" + StringGenerator.uuid();
    long expireTime = 10;
    List<Object> collect =
        IntStream.range(0, 40)
            .mapToObj(i -> (StringGenerator.uuid() + i))
            .collect(Collectors.toList());
    Date date = new Date();
    redisUtil.lRightPushAll(key, collect, expireTime);
    log.info("耗时：" + new DateBetween(date, new Date()).between(DateUnit.MS));

    // 启动一个定时任务检测数据
    Thread thread =
        new Thread(
            () -> {
              boolean b = redisUtil.hasKey(key);
              log.info("hasKey [" + key + "]=" + b);
            });
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture<?> scheduledFuture = scheduledExecutorService
            .scheduleAtFixedRate(thread, 1, 1, TimeUnit.SECONDS);
    try {
      scheduledExecutorService.awaitTermination(20,TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    assertEquals(false,redisUtil.hasKey(key));

  }

  /** 向指定队列串行多次取出数据 */
  @Test
  public void testListLeftPop() {
    String key = "hxz-test-list:7a45b3f943d34de3a35cfa3cf7c2c3cd";
    for (int i = 0; i < 1000; i++) {
      Date date = new Date();
      Object o = redisUtil.lLeftPop(key);
      log.info("取出数据=" + o);
      log.info("耗时：" + new DateBetween(date, new Date()).between(DateUnit.MS));
    }
  }

  // @After
  // public void destroy() {
  // }
}
