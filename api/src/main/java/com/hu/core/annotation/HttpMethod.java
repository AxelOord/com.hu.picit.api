package main.java.com.hu.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import main.java.com.hu.core.enums.MethodEnum;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMethod {
    MethodEnum method();  // HTTP method
    String value() default "";  // Route path
}