package com.locadoraveiculo.locadoraveiculo.dao;

import com.locadoraveiculo.locadoraveiculo.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class EmployeeDAO {

    @PersistenceContext
    private final EntityManager em;

    public Employee save(Employee employee) {
        em.merge(employee);
        return employee;
    }

    public List<Employee> findAll() {
        return em.createQuery("select e from Employee e", Employee.class).getResultList();
    }

    @Transactional
    public void delete(Long id) {
        Employee employeeTemp = em.find(Employee.class, id);

        em.remove(employeeTemp);
        em.flush();
    }

    public Employee findById(Long employeeId) {
        return em.find(Employee.class, employeeId);
    }
}
