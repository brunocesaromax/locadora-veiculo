package com.locadoraveiculo.locadoraveiculo.service;

import com.locadoraveiculo.locadoraveiculo.dao.EmployeeDAO;
import com.locadoraveiculo.locadoraveiculo.exception.NotFoundException;
import com.locadoraveiculo.locadoraveiculo.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeDAO employeeDAO;

    @Transactional
    @Override
    public Employee save(Employee employee) {
        return employeeDAO.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        employeeDAO.delete(id);
    }

    @Transactional
    @Override
    public Employee update(Long employeeId, Employee employee) {
        Employee employeeOld = employeeDAO.findById(employeeId);

        if (employeeOld == null){
            throw new NotFoundException();
        }else{
            BeanUtils.copyProperties(employee, employeeOld, "id");
            return employeeDAO.save(employeeOld);
        }
    }
}
