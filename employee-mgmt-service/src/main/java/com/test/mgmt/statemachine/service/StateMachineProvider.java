package com.test.mgmt.statemachine.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;

import com.test.mgmt.statemachine.Events;
import com.test.mgmt.statemachine.States;
import com.test.mgmt.statemachine.action.ErrorAction;

@Service
public class StateMachineProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorAction.class);

    @Autowired
    private StateMachineService<States, Events> stateMachineService;

    public StateMachine<States, Events> getStateMachine(String machineId) {

        StateMachine<States, Events> currentStateMachine = stateMachineService.acquireStateMachine(machineId);
        currentStateMachine.startReactively().block();
        LOG.debug(currentStateMachine.toString());

        return currentStateMachine;
    }

}
