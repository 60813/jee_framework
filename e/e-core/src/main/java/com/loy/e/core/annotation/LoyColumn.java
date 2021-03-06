package com.loy.e.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loy.e.core.data.InputClazz;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.FIELD})
@Documented
public @interface LoyColumn {
	boolean list() default true;
	boolean edit() default true;
	boolean detail() default true;
	String description();
	String column() default "";
	boolean sortable() default false;
	InputClazz inputType() default InputClazz.NONE;
	String validate() default  "";//这里的值必需是一个json串;{required:true, minlength: 5}
	LoyField[] lists() default {};
	LoyField[] details() default {};
}
