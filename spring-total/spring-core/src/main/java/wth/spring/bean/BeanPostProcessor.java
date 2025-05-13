package wth.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * BeanPostProcessor
 * */
public interface BeanPostProcessor {
    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Optional.ofNullable(bean).map(o -> o.toString()).orElseGet(() -> "null");
        if (bean != null) {

        }
        return bean;
    }

    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
