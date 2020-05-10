package spring.danamyProxy.jdkproxy;

import org.omg.CORBA.portable.InvokeHandler;
import spring.Person;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by mac on 2020/5/18.
 */
public class JDKDanymyProxy implements InvocationHandler {
    private IService service;
    JDKDanymyProxy(IService service){
        this.service = service;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");

        Object obj = method.invoke(service,args);
        System.out.println("after invoke");
        return obj;
    }
}
