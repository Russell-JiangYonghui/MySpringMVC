package mymvc.servlet;

import mymvc.annotation.*;
import mymvc.controller.MySimpleController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 2020/5/10.
 */
public class MyDispathServlet extends HttpServlet {

    List<String> classnames = new ArrayList<>();
    private Map<String,Object> beans = new HashMap<>();
    private Map<String,Method> handlermapping = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        //扫描包下面有哪些bean需要实例化
        doScanBean("mymvc");

        //实例化找到的类
        doinit();

        //实例进行依赖注入
        doAutowired();

        //建立handlerMapping
        doHandlerMapping();


    }

    private void doHandlerMapping() {
        for(Map.Entry<String,Object> entry : beans.entrySet()){
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(MyController.class)){
                MyRequestMapping classmapping = clazz.getAnnotation(MyRequestMapping.class);
                String classURL = classmapping.value();
                Method[]  methods = clazz.getDeclaredMethods();
                for(Method m : methods){
                    if(m.isAnnotationPresent(MyRequestMapping.class)){
                        String methodURL = m.getAnnotation(MyRequestMapping.class).value();
                        handlermapping.put(classURL+methodURL,m);//  ---> /test/query
                    }
                }
            }else{
                continue;
            }
        }

    }

    private void doAutowired() {

        for(Map.Entry<String,Object> entry : beans.entrySet()){
            Object instance = entry.getValue();
            Class<?> clazz = instance.getClass();
            if (clazz.isAnnotationPresent(MyController.class)){
                Field[] fileds = clazz.getDeclaredFields();
                for(Field filed : fileds){
                    if (filed.isAnnotationPresent(MyAutowired.class)){
                        String instanceName = filed.getAnnotation(MyAutowired.class).value();
                        Object bean  = beans.get(instanceName);
                        //注入依赖
                        filed.setAccessible(true);
                        try {
                            filed.set(instance, bean);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }else{continue;}
                }
            }else{
                continue;
            }
        }
    }

    private void doinit()  {

            for(String className : classnames){
                //com.mymvc ....service.class, 去掉后面的".class"
                String cn = className.replace(".class", "");

                try{
                    //进行实例化
                    Class<?> clazz = Class.forName(cn);
                    if (clazz.isAnnotationPresent(MyController.class)){
                        //控制类
                        Object instance = clazz.newInstance();//map.put(clazz,instance)
                        MyRequestMapping mapping = clazz.getAnnotation(MyRequestMapping.class);
                        String val = mapping.value();
                        beans.put(val,instance);
                    }else if (clazz.isAnnotationPresent(MyService.class)){
                        Object instance = clazz.newInstance();//map.put(clazz,instance)
                        MyService service = clazz.getAnnotation(MyService.class);
                        String val = service.value();
                        beans.put(val,instance);
                    }else{
                        continue;
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("ClassNotFoundException");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }

    }

    private void doScanBean(String basePackage) {
        //扫描这个编译好的类路径 ....class
        ClassLoader classLoader = this.getClass().getClassLoader();
        String p = "/"+basePackage.replaceAll("\\.","/");

        URL url = classLoader.getResource(p);
        String fileStr = url.getFile();
        File file = new File(fileStr);


        String[] filesStr = file.list();
        for (String path : filesStr
             ) {
            File filePath = new File(fileStr+path);
            if (filePath.isDirectory()){
                doScanBean(basePackage+"."+path);
            }else{
                //拿到需要的class文件
                classnames.add(basePackage+"."+filePath.getName());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();// ---> /projectname/test/query
        //String context = req.getContextPath(); // ---->/projectName
        String methodPath = "/"+url.split("/")[2]+"/"+url.split("/")[3];


        Method method = handlermapping.get(methodPath);
        String beanN = "/"+methodPath.split("/")[1];
        MySimpleController controller = (MySimpleController) beans.get(beanN);
        Object[] args = handleArgs(req,resp,method);
        try {
            method.invoke(controller,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private Object[] handleArgs(HttpServletRequest req, HttpServletResponse resp, Method method) {
        Class<?>[] paramClasses = method.getParameterTypes();
        Object[] objects = new Object[paramClasses.length];

        int args_i = 0;
        int index = 0;
        for(Class<?> paramClass : paramClasses){
            if (ServletRequest.class.isAssignableFrom(paramClass)){
                objects[args_i++] = req;
            }
            if (ServletResponse.class.isAssignableFrom(paramClass)){
                objects[args_i++] = resp;
            }
            Annotation[] annotations = method.getParameterAnnotations()[index];
            if (annotations.length > 0 ) {
                for (Annotation annotation : annotations
                        ) {
                    if (MyRequestParam.class.isAssignableFrom(annotation.getClass())){
                        MyRequestParam rp = (MyRequestParam) annotation;
                        objects[args_i++] = req.getParameter(rp.value());
                    }
                }
            }
            index ++;
        }
        return objects;
    }
}
