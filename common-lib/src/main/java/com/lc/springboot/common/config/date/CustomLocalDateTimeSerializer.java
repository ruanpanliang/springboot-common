package com.lc.springboot.common.config.date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @description: 用户自定义时间格式转换,一般用于数据库的date或datetime类型转成字符串类型
 * @author: liangc
 * @date: 2019-10-31 14:28
 * @version 1.0
 */

public class CustomLocalDateTimeSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider arg2)
			throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		//formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String formattedDate = formatter.format(value);
        jgen.writeString(formattedDate);
	}

}
