package com.lc.springboot.common.utils;

/** 雪花id生成器 */
public class SnowFlake {

  /** 起始的时间戳 */
  private static final long START_STAMP = 1480166465631L;

  /** 每一部分占用的位数 */
  // 序列号占用的位数
  private static final long SEQUENCE_BIT = 12;
  // 机器标识占用的位数
  private static final long MACHINE_BIT = 5;
  // 数据中心占用的位数
  private static final long DATA_CENTER_BIT = 5;

  /** 每一部分的最大值 */
  private static final long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);

  private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
  private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

  /** 每一部分向左的位移 */
  private static final long MACHINE_LEFT = SEQUENCE_BIT;

  private static final long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
  private static final long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

  // 数据中心
  private long datacenterId;
  // 机器标识
  private long machineId;
  // 序列号
  private long sequence = 0L;
  // 上一次时间戳
  private long lastStmp = -1L;

  public SnowFlake(long datacenterId, long machineId) {
    if (datacenterId > MAX_DATA_CENTER_NUM || datacenterId < 0) {
      throw new IllegalArgumentException(
          "datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
    }
    if (machineId > MAX_MACHINE_NUM || machineId < 0) {
      throw new IllegalArgumentException(
          "machineId can't be greater than MAX_MACHINE_NUM or less than 0");
    }
    this.datacenterId = datacenterId;
    this.machineId = machineId;
  }

  /**
   * 产生下一个ID
   *
   * @return
   */
  public synchronized long nextId() {
    long currStmp = timestamp();
    if (currStmp < lastStmp) {
      throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
    }

    if (currStmp == lastStmp) {
      // 相同毫秒内，序列号自增
      sequence = (sequence + 1) & MAX_SEQUENCE;
      // 同一毫秒的序列数已经达到最大
      if (sequence == 0L) {
        currStmp = getNextMill();
      }
    } else {
      // 不同毫秒内，序列号置为0
      sequence = 0L;
    }

    lastStmp = currStmp;

    return (currStmp - START_STAMP) << TIMESTAMP_LEFT // 时间戳部分
        | datacenterId << DATA_CENTER_LEFT // 数据中心部分
        | machineId << MACHINE_LEFT // 机器标识部分
        | sequence;
  }

  private long getNextMill() {
    long mill = timestamp();
    while (mill <= lastStmp) {
      mill = timestamp();
    }
    return mill;
  }

  private long timestamp() {
    return System.currentTimeMillis();
  }
}
