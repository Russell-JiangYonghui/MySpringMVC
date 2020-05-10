package mymvc.annotation;

import java.lang.annotation.*;

/**
 * Created by mac on 2020/5/10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface MyRequestParam {

    String value() default "";
}
