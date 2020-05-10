package spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by mac on 2020/5/13.
 */
public class SpringMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ct = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        Person p = (Person) ct.getBean("person");
        System.out.println(p.getAge());
    }
}
