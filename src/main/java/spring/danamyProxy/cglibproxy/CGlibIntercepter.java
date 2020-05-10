package spring.danamyProxy.cglibproxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by mac on 2020/5/18.
 */
public class CGlibIntercepter implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before incvoke cglib");
        Object obj = methodProxy.invokeSuper(o,objects);
        System.out.println("after invoke cglib");
        return obj;
    }
}
