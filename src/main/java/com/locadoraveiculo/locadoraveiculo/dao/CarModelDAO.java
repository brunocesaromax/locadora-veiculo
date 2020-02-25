package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.filter.CarModelFilter;
import com.locadoraveiculo.locadoraveiculo.model.CarModel;
import com.locadoraveiculo.locadoraveiculo.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CarModelDAO {

    @PersistenceContext
    private final EntityManager em;

    public CarModel save(CarModel carModel) {
        em.merge(carModel);
        return carModel;
    }

    public List<CarModel> findAll() {
        return em.createQuery("select cm from CarModel cm", CarModel.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        CarModel carModelTemp = em.find(CarModel.class, id);

        em.remove(carModelTemp);
        em.flush();
    }

    public CarModel findById(Long carModelId) {
        return em.find(CarModel.class, carModelId);
    }

    public List<String> findProducerNames() {
        return em.createQuery("select cm.producer.name from CarModel cm", String.class).getResultList();
    }

    public List<CarModel> findAllByProducerName(String producerName) {
        return em.createQuery("select cm from CarModel cm where cm.producer.name = :producerName", CarModel.class)
                .setParameter("producerName", producerName)
                .getResultList();
    }

    public List<CarModel> findAllByFilter(CarModelFilter filter) {
        List<CarModel> models;
        String jpql = "select cm from CarModel cm ";
        StringBuilder whereClause = new StringBuilder("where ");

        if (filter.getProducerName() != null) {
            whereClause.append("cm.producer.name = ").append("'").append(filter.getProducerName()).append("'").append(" ");
        }

        if (filter.getCategories() != null && !filter.getCategories().isEmpty()) {
            whereClause.append("and cm.category in (");

            for (Category category : filter.getCategories()) {
                whereClause.append("'").append(category).append("'").append(",");
            }

            whereClause.deleteCharAt(whereClause.length() - 1);
            whereClause.append(")");
        }

        if (!whereClause.equals("where ")) {
            models = em.createQuery(jpql.concat(whereClause.toString()), CarModel.class).getResultList();
        } else {
            models = em.createQuery(jpql, CarModel.class).getResultList();
        }

        return models;
    }
}
