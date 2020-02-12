package com.locadoraveiculo.locadoraveiculo.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueProducerNameValidator.class)
public @interface UniqueProducerName {
    String message() default "producer.unique.name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
