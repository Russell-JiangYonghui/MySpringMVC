package spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

/**
 * Created by mac on 2020/5/13.
 */
public class MyBeanFactoryPostProccessor implements BeanFactoryPostProcessor, PriorityOrdered{
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("contain "+ beanFactory.getBeanDefinitionCount() + " bds");
    }

    @Override
    public int getOrder() {
        return this.hashCode();
    }
}
