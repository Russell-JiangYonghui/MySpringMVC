package spring;

/**
 * Created by mac on 2020/5/13.
 */
public class Person {
    int age;
    String name;


    public Person(){}

    public void init(){
        System.out.println("init person ...");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
