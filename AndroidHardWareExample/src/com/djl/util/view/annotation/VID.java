package com.djl.util.view.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author DJL E-mail:
 * @date 2015-6-25 下午2:27:46
 * @version 1.0
 * @parameter
 * @since
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VID {

	int value();

	int pId() default 0;
}
