package com.locadoraveiculo.locadoraveiculo.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueProducerNameValidator.class)
public @interface UniqueProducerName {
    String message() default "Producer.unique.name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
