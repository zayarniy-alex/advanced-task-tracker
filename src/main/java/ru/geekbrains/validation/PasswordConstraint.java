package ru.geekbrains.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Constraint (validatedBy = PasswordConstraintValidator.class)
@Target ({TYPE, FIELD, ANNOTATION_TYPE})
@Retention (RUNTIME)
public @interface PasswordConstraint {

    String message() default "некорректный пароль";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}