package com.test.mgmt.employeeservice.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.mgmt.employeeservice.dto.Response;
import com.test.mgmt.employeeservice.entity.Employee;
import com.test.mgmt.employeeservice.service.EmployeeService;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeService employeeService;

    @ExceptionHandler(Exception.class)
    protected Response handleException(Exception ex) {
        LOG.error("System error: ", ex);
        return Response.of(9, ex.getMessage());
    }

    @PostMapping
    public Response addEmployee(@RequestBody Employee employee) {
        Long id = employeeService.add(employee);
        return Response.of(0, "Employee ID = [ " + id + " ]");
    }

    @PutMapping("/{id}")
    public Response updateEmployeeState(@PathVariable Long id, @RequestBody String state) {
        employeeService.updateState(id, state);
        return Response.of(0, "ok");
    }

}
