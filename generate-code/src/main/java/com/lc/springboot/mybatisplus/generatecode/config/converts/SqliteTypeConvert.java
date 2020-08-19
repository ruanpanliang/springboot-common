/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
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
package com.lc.springboot.mybatisplus.generatecode.config.converts;


import com.lc.springboot.mybatisplus.generatecode.config.GlobalConfig;
import com.lc.springboot.mybatisplus.generatecode.config.ITypeConvert;
import com.lc.springboot.mybatisplus.generatecode.config.rules.DbColumnType;
import com.lc.springboot.mybatisplus.generatecode.config.rules.IColumnType;

import static com.lc.springboot.mybatisplus.generatecode.config.converts.TypeConverts.contains;
import static com.lc.springboot.mybatisplus.generatecode.config.converts.TypeConverts.containsAny;

/**
 * SQLite 字段类型转换
 *
 * @author chen_wj, hanchunlin
 * @since 2019-05-08
 */
public class SqliteTypeConvert implements ITypeConvert {
    public static final SqliteTypeConvert INSTANCE = new SqliteTypeConvert();

    /**
     * @inheritDoc
     * @see MySqlTypeConvert#toDateType(GlobalConfig, String)
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig config, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(contains("bigint").then(DbColumnType.LONG))
            .test(containsAny("tinyint(1)", "boolean").then(DbColumnType.BOOLEAN))
            .test(contains("int").then(DbColumnType.INTEGER))
            .test(containsAny("text", "char", "enum").then(DbColumnType.STRING))
            .test(containsAny("decimal", "numeric").then(DbColumnType.BIG_DECIMAL))
            .test(contains("clob").then(DbColumnType.CLOB))
            .test(contains("blob").then(DbColumnType.BLOB))
            .test(contains("float").then(DbColumnType.FLOAT))
            .test(contains("double").then(DbColumnType.DOUBLE))
            .test(containsAny("date", "time", "year").then(t -> MySqlTypeConvert.toDateType(config, t)))
            .or(DbColumnType.STRING);
    }

}
