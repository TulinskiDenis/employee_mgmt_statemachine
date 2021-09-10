package com.test.mgmt.statemachine.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.test.mgmt.statemachine.Events;
import com.test.mgmt.statemachine.States;

@Component
public class ErrorAction implements Action<States, Events> {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorAction.class);

    @Override
    public void execute(StateContext<States, Events> context) {
        LOG.error("Error action");
        /// set error here to be caught and logged by
        /// LoggingEventsListenerAdapter.stateMachineError
        context.getStateMachine().setStateMachineError(context.getException());
    }

}
