package com.test.managementsystem.employeeservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.mgmt.employeeservice.EmployeeserviceApplication;
import com.test.mgmt.employeeservice.dao.EmployeeDao;
import com.test.mgmt.employeeservice.entity.Employee;

@SpringBootTest(classes = EmployeeserviceApplication.class)
class RepositoryTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void addEntitySuccess() {
        Employee employee = new Employee();
        employee.setName("Name");
        employee.setAge(33);

        employeeDao.save(employee);
        List<Employee> list = employeeDao.findAll();

        assertEquals(1, list.size());
    }

}
