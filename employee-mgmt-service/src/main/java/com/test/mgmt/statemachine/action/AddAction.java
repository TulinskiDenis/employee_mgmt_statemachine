package com.test.mgmt.statemachine.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.test.mgmt.coordinator.client.EmployeeServiceClient;
import com.test.mgmt.coordinator.dto.EmployeeDto;
import com.test.mgmt.coordinator.dto.Response;
import com.test.mgmt.statemachine.Events;
import com.test.mgmt.statemachine.States;

@Component
public class AddAction implements Action<States, Events> {
    private static final Logger LOG = LoggerFactory.getLogger(AddAction.class);

    @Autowired
    private EmployeeServiceClient employeeServiceClient;

    @Override
    public void execute(StateContext<States, Events> context) {
        LOG.debug("Add action");
        EmployeeDto employee = context.getExtendedState().get("employee", EmployeeDto.class);

        Response response = employeeServiceClient.add(employee);
        if (!response.isSuccessful()) {
            throw new RuntimeException("Add operation failed");
        }
    }

}
