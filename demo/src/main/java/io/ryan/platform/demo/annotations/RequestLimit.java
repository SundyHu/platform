package io.ryan.platform.demo.annotations;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * RequestLimit
 *
 * @author hkc
 * @version 1.0.0
 * @date 2021-07-29 11:15
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
public @interface RequestLimit {

    int value() default 100;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
