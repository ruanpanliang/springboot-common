package com.lc.springboot.common.redis.serializer;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * redis序列化和反序列化工具类 <br>
 * 此时定义的序列化操作表示可以序列化所有类的对象，当然，这个对象所在的类一定要实现序列化接口
 *
 * @author liangchao
 */
public class RedisObjectSerializer implements RedisSerializer<Object> {

  /** 序列化转换器 */
  private final Converter<Object, byte[]> serializingConverter = new SerializingConverter();
  /** 反序列化转换器 */
  private final Converter<byte[], Object> deserializingConverter = new DeserializingConverter();

  /** 做一个空数组，不是null */
  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

  @Override
  public byte[] serialize(Object obj) throws SerializationException {
    // 这个时候没有要序列化的对象出现，所以返回的字节数组应该就是一个空数组
    if (obj == null) {
      return EMPTY_BYTE_ARRAY;
    }
    return serializingConverter.convert(obj);
  }

  @Override
  public Object deserialize(byte[] data) throws SerializationException {
    if (data == null || data.length == 0) {
      return null;
    }
    return deserializingConverter.convert(data);
  }
}
