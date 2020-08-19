package com.lc.springboot.common.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author liangchao
 */
@Documented
@Constraint(validatedBy = TimezoneValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Timezone {
    String message() default "Invalid timezone";
    Class[] groups() default {};
    Class[] payload() default {};
}
