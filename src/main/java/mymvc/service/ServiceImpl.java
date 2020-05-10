package mymvc.service;

import mymvc.annotation.MyService;

/**
 * Created by mac on 2020/5/10.
 */

@MyService("myService")
public class ServiceImpl implements IService {
    public String query(String name, String age) {
        return name + " -----> "+ age;
    }
}
