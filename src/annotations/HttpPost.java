package annotations;

import enums.MethodEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@HttpRoute(method = MethodEnum.POST)
public @interface HttpPost {
    String value() default "";
}