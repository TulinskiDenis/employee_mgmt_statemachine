package com.test.mgmt.employeeservice.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mgmt.employeeservice.dao.EmployeeDao;
import com.test.mgmt.employeeservice.entity.Employee;


@Service
public class EmployeeService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDao employeRepository;

    public void add(Employee employee) {
        Employee savedEntity = employeRepository.save(employee);
        LOG.debug("Employee added: {}", savedEntity);
    }

    public void updateState(Long employeeId, String state) {
        Optional<Employee> optional = employeRepository.findById(employeeId);

        optional.ifPresentOrElse(
                employee -> {
                    employee.setState(state);
                    employeRepository.save(employee);
                    LOG.debug("Employee state updated: state = [{}]", state);
                },
                () -> LOG.debug("Employee not found: id = [{}]", employeeId));

    }

}
