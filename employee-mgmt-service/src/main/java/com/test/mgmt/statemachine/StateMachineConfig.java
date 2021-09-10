package com.test.mgmt.statemachine;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

import com.test.mgmt.statemachine.action.ActivateAction;
import com.test.mgmt.statemachine.action.ApproveAction;
import com.test.mgmt.statemachine.action.CheckAction;
import com.test.mgmt.statemachine.action.ErrorAction;

@Configuration
@ComponentScan(value = "com.test.mgmt.statemachine.action")
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Autowired
    private StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister;

    @Autowired
    private List<Action<States, Events>> actions;

    @Bean
    public Map<Class<?>, Action<States, Events>> actions() {
        return actions.stream()
                .collect(HashMap::new,
                        (map, action) -> map.put(action.getClass(), action),
                        (map1, map2) -> map1.putAll(map2));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
            .withConfiguration()
                .autoStartup(true)
                .listener(listener())
                .and()
            .withPersistence()
                .runtimePersister(stateMachineRuntimePersister);

    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
                .withStates()
                .initial(States.ADDED)
                .end(States.ACTIVE)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
            .withExternal()
                .source(States.ADDED)
                .target(States.IN_CHECK)
                .event(Events.CHECK)
                .action(actions().get(CheckAction.class), actions().get(ErrorAction.class))
                .and()
            .withExternal()
                .source(States.IN_CHECK)
                .target(States.APPROVED)
                .event(Events.APPROVE)
                .action(actions().get(ApproveAction.class), actions().get(ErrorAction.class))
                .and()
            .withExternal()
                .source(States.APPROVED)
                .target(States.ACTIVE)
                .event(Events.ACTIVATE)
                .action(actions().get(ActivateAction.class), actions().get(ErrorAction.class));
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new LoggingEventsListenerAdapter();
    }

    @Bean
    public StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister(
            JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<States, Events, String>(jpaStateMachineRepository);
    }

    @Bean
    public StateMachineService<States, Events> stateMachineService(
            StateMachineFactory<States, Events> stateMachineFactory,
            StateMachineRuntimePersister<States, Events, String> stateMachineRuntimePersister) {
        return new DefaultStateMachineService<States, Events>(stateMachineFactory, stateMachineRuntimePersister);
    }


}
