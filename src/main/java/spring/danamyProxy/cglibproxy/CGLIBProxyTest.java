package spring.danamyProxy.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;

/**
 * Created by mac on 2020/5/18.
 */
public class CGLIBProxyTest {
    public static void main(String[] args) {
        CGlibIntercepter intercepter = new CGlibIntercepter();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGLIBService.class);
        enhancer.setCallback(intercepter);
        CGLIBService service = (CGLIBService) enhancer.create();
        service.query();
    }

}
