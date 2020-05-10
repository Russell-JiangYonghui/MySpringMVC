package mymvc.controller;

import mymvc.annotation.MyRequestMapping;
import mymvc.service.ServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mymvc.annotation.MyAutowired;
import mymvc.annotation.MyRequestParam;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mac on 2020/5/10.
 */
@mymvc.annotation.MyController
@MyRequestMapping("/test")
public class MySimpleController {

    @MyAutowired("myService")
    private ServiceImpl service ;

    @MyRequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response,
                        @MyRequestParam("name") String name, @MyRequestParam("age") String age) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(service.query(name,age));
    }
}
