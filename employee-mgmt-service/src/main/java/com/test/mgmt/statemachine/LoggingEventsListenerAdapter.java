package com.test.mgmt.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public class LoggingEventsListenerAdapter extends StateMachineListenerAdapter<States, Events> {
    private static final Logger LOG = LoggerFactory.getLogger(LoggingEventsListenerAdapter.class);

    @Override
    public void stateChanged(State<States, Events> from, State<States, Events> to) {
        LOG.debug("State change to [{}]", to.getId());
    };

    @Override
    public void eventNotAccepted(Message<Events> event) {
        LOG.debug("Event [{}] not accepted. Transition not performed. ", event.getPayload().name());

    }

    @Override
    public void stateMachineError(StateMachine<States, Events> stateMachine, Exception exception) {
        LOG.error("State machine error:", exception);
    }

}
