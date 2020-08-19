package com.lc.springboot.common.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author liangchao
 */
@Documented
@Constraint(validatedBy = DayOfWeekValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DayOfWeek {
    String message() default "Unknown day of week";
    Class[] groups() default {};
    Class[] payload() default {};
}
