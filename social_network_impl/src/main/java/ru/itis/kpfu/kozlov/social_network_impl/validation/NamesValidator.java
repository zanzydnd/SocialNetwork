package ru.itis.kpfu.kozlov.social_network_impl.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NamesValidator implements ConstraintValidator<ValidNames, Object> {
    private String passwordPropertyName;
    private String password2PropertyName;

    @Override
    public void initialize(ValidNames constraintAnnotation) {
        this.passwordPropertyName = constraintAnnotation.password();
        this.password2PropertyName = constraintAnnotation.password2();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object password = new BeanWrapperImpl(value).getPropertyValue(passwordPropertyName); //получили конкретные значения
        Object password2 = new BeanWrapperImpl(value).getPropertyValue(password2PropertyName);

        return password != null && password.equals(password2);
    }
}
