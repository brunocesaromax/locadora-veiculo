package com.locadoraveiculo.locadoraveiculo.constraint;

import com.locadoraveiculo.locadoraveiculo.dao.ProducerDAO;
import com.locadoraveiculo.locadoraveiculo.exception.UniqueProducerNameException;
import com.locadoraveiculo.locadoraveiculo.model.Producer;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class UniqueProducerNameValidator implements ConstraintValidator<UniqueProducerName, Producer> {

    private ProducerDAO producerDAO;

    public UniqueProducerNameValidator(ProducerDAO producerDAO) {
        this.producerDAO = producerDAO;
    }

    @Override
    public void initialize(UniqueProducerName constraintAnnotation) {
    }

    @Override
    public boolean isValid(Producer producer, ConstraintValidatorContext constraintValidatorContext) {

        if (producer.getId() == null) {
            if (producerDAO.findByName(producer.getName()).isPresent()) {
                throw new UniqueProducerNameException();
            }

        } else {
            Optional<Producer> producerOptional = producerDAO.findByName(producer.getName());

            if (producerOptional.isPresent()) {
                if (!producerOptional.get().getId().equals(producer.getId())) {
                    throw new UniqueProducerNameException();
                }
            }
        }

        return Boolean.TRUE;
    }
}
