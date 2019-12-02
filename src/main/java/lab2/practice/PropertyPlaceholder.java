package lab2.practice;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.FieldPosition;

/**
 * Класс должен содержать логику подмены значений филдов заданых по умолчанию в контексте.
 * Заменяет строковые значение в бинах типа
 *
 * @see Printer
 * на значения в
 * @see PropertyRepository
 * Использует изначальные значения как ключи для поиска в PropertyRepository
 */

public class PropertyPlaceholder implements BeanFactoryPostProcessor {


    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String currentClassName = beanDefinition.getBeanClassName();
            try {
                Class<?> clazz = Class.forName(currentClassName);
                if (clazz.getName().equals(MessagePrinter.class.getName())) {
                    Object key = beanDefinition.getPropertyValues().get("message");
                    TypedStringValue typeKey = (TypedStringValue) key;

//                Field field = clazz.getField("message");
//                field.setAccessible(true);
                    String value = PropertyRepository.get(typeKey.getValue());
                    beanDefinition.getPropertyValues().getPropertyValue("message").setConvertedValue(value);
//                beanDefinition.
//                beanDefinition.setAttribute();
//                field.set(String.class,value);
//                field.setAccessible(false);
//                if (clazzAnnotation != null) {
//                    beanDefinition.setBeanClassName(clazzAnnotation.value().getCanonicalName());
//                }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
        }
    }
}
