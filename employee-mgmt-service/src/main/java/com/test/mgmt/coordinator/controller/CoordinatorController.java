package com.test.mgmt.coordinator.controller;

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

import com.test.mgmt.coordinator.client.EmployeeServiceClient;
import com.test.mgmt.coordinator.dto.EmployeeDto;
import com.test.mgmt.coordinator.dto.Response;
import com.test.mgmt.statemachine.Events;
import com.test.mgmt.statemachine.handler.StateMachineCommandHandler;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/v1/employees")
public class CoordinatorController {
    private static final Logger LOG = LoggerFactory.getLogger(CoordinatorController.class);

    @Autowired
    StateMachineCommandHandler stateMachineCommandHandler;

    @Autowired
    private EmployeeServiceClient employeeServiceClient;

    @ExceptionHandler(Exception.class)
    protected Response handleException(Exception ex) {
        LOG.error("System error: ", ex);
        return Response.of(9, ex.getMessage());
    }

    @ApiOperation(value = "Add employee", response = Response.class)
    @PostMapping
    public Response addEmployee(@RequestBody EmployeeDto employee) {
        return employeeServiceClient.add(employee);
    }

    @ApiOperation(value = "Update employee state", response = Response.class)
    @PutMapping("{id}/{action}")
    public Response updateEmployeeState(@PathVariable Long id, @PathVariable Events action) {
        stateMachineCommandHandler.handleEvent(action, id);
        return Response.of(0, "Request sent");

    }

}
