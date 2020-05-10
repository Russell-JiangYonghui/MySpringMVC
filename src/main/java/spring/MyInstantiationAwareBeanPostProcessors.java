package spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * Created by mac on 2020/5/16.
 */
public class MyInstantiationAwareBeanPostProcessors implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("before MyInstantiationAwareBeanPostProcessors");
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("after MyInstantiationAwareBeanPostProcessors");
        return null;
    }
}

