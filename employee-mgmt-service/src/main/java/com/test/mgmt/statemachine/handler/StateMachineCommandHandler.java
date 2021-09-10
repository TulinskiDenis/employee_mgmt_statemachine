package com.test.mgmt.statemachine.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import com.test.mgmt.statemachine.Events;
import com.test.mgmt.statemachine.States;
import com.test.mgmt.statemachine.service.StateMachineProvider;

import reactor.core.publisher.Mono;

@Component
public class StateMachineCommandHandler {
    private static final Logger LOG = LoggerFactory.getLogger(StateMachineCommandHandler.class);

    @Autowired
    private StateMachineProvider stateMachineService;

    public void handleEvent(Events event, Long id) {
        final StateMachine<States, Events> stateMachine = stateMachineService.getStateMachine(id);

        Message<Events> eventToDispatch = MessageBuilder.withPayload(event).build();
        stateMachine.sendEvent(Mono.just(eventToDispatch))
                .doOnComplete(() -> {
                    LOG.debug("Event handling complete");
                })
                .subscribe();

    }

}
