package spring.danamyProxy.jdkproxy;

import spring.Person;

import java.lang.reflect.Proxy;

/**
 * Created by mac on 2020/5/18.
 */
public class JDKProxyTest {
    public static void main(String[] args) {
        IService service = new ServiceImpl();
        JDKDanymyProxy proxy = new JDKDanymyProxy(service);
        IService ss = (IService) Proxy.newProxyInstance(proxy.getClass().getClassLoader(),
                service.getClass().getInterfaces(),proxy);
        ss.query();
    }
}
