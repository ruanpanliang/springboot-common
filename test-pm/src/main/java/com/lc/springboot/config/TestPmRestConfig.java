package com.lc.springboot.config;

import com.lc.springboot.common.error.GlobalExceptionTranslator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Use this common config for Rest API
 *
 * @author liangchao
 */
@Configuration
@Import(value = {TestPmConfig.class, GlobalExceptionTranslator.class})
public class TestPmRestConfig {}
