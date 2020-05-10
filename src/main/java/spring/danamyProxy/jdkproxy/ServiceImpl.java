package spring.danamyProxy.jdkproxy;

/**
 * Created by mac on 2020/5/18.
 */
public class ServiceImpl  implements IService{

    @Override
    public void query() {
        System.out.println("query start ");
    }
}
