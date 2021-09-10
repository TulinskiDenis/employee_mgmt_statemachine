package com.test.mgmt.employeeservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mgmt.employeeservice.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long>{

}
