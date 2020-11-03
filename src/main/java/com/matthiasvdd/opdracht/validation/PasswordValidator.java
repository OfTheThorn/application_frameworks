package com.matthiasvdd.opdracht.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordValidator {
    String message() default "{validation.password}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
