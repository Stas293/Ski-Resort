package com.projects.ski_resort_project.bpp;

import com.projects.ski_resort_project.validation.ValidatorFiltration;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Validator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InjectValidatorListFiltrationBeanPostProcessor implements BeanPostProcessor {
    private static final String MAIN_PACKAGE = "com.projects.ski_resort_project";
    private static final String DTO_PACKAGE = MAIN_PACKAGE + ".dto";
    private static final String VALIDATION_PACKAGE = MAIN_PACKAGE + ".validation";
    private final Set<Class<? extends Record>> dtoClasses;

    public InjectValidatorListFiltrationBeanPostProcessor() {
        Reflections reflections = new Reflections(DTO_PACKAGE);
        this.dtoClasses = reflections.getSubTypesOf(Record.class);
    }

    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, String beanName) throws BeansException {
        if (!beanName.contains("Service")) {
            return bean;
        }
        String[] camelCaseBeanName = beanName.split("(?=\\p{Upper})");
        String capitalizedBeanName = StringUtils.capitalize(camelCaseBeanName[0]);
        List<Class<? extends Record>> validatorClassesForService = dtoClasses.stream()
                .filter(dtoClass -> dtoClass.getSimpleName().startsWith(capitalizedBeanName))
                .toList();
        Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(field ->
                        field.isAnnotationPresent(ValidatorFiltration.class))
                .forEach(field -> setListValidators(bean, validatorClassesForService, field));
        return bean;
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private void setListValidators(Object bean, List<Class<? extends Record>> validatorClassesForService, Field field) {
        field.setAccessible(true);
        Set<Validator> validators = ((Set<Validator>) field.get(bean)).stream()
                .filter(validator -> validator.getClass()
                        .getPackage()
                        .getName()
                        .contains(VALIDATION_PACKAGE))
                .filter(validator -> validatorClassesForService.stream()
                        .anyMatch(validator::supports))
                .collect(Collectors.toSet());
        field.set(bean, validators);
        field.setAccessible(false);
    }
}
