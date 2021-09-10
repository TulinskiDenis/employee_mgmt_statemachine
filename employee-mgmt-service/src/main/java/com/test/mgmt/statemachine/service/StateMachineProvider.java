package com.test.mgmt.statemachine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;

import com.test.mgmt.statemachine.Events;
import com.test.mgmt.statemachine.States;

@Service
public class StateMachineProvider {

    @Autowired
    private StateMachineService<States, Events> stateMachineService;

    public StateMachine<States, Events> getStateMachine(Long machineId) {

        StateMachine<States, Events> currentStateMachine = stateMachineService
                .acquireStateMachine(String.valueOf(machineId));
        currentStateMachine.getExtendedState().getVariables().put("id", machineId);
        currentStateMachine.startReactively().block();

        return currentStateMachine;
    }

}
