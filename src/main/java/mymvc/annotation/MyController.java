package mymvc.annotation;

import java.lang.annotation.*;

/**
 * Created by mac on 2020/5/10.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyController {
    String value() default "";
}
