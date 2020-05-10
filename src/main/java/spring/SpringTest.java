package spring;

import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * Created by mac on 2020/5/13.
 */
public class SpringTest {
    public static void main(String[] args) {
        InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor = new MyInstantiationAwareBeanPostProcessors();
        System.out.println(instantiationAwareBeanPostProcessor instanceof  InstantiationAwareBeanPostProcessor);
    }
}
