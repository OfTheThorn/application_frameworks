package com.matthiasvdd.opdracht.validation;

import com.matthiasvdd.opdracht.models.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordValidator, Object> {

    @Override
    public void initialize(PasswordValidator constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        User u = (User) value;
        System.out.println("Password isValid");
        boolean isValid = u.getPassword().equals(u.getConfirmPassword());
        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode( "password" ).addConstraintViolation();
        }
        return isValid;
    }
}
