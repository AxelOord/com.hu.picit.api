package main.java.com.hu.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import main.java.com.hu.core.enums.MethodEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod(method = MethodEnum.DELETE)
public @interface HttpDelete {
    String value() default "";
}