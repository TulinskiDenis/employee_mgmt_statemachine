package com.test.mgmt;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

import com.test.mgmt.coordinator.client.EmployeeServiceClient;
import com.test.mgmt.coordinator.dto.Response;
import com.test.mgmt.statemachine.Events;
import com.test.mgmt.statemachine.States;
import com.test.mgmt.statemachine.service.StateMachineProvider;

@SpringBootTest(classes = EmployeeMgmtServiceApplication.class)
public class StateMachineTest {
    private static final long STATE_MACHINE_ID = 1L;

    @MockBean
    private EmployeeServiceClient employeeServiceClient;

    @Autowired
    private StateMachineProvider stateMachineProvider;


    @Test
    public void testSuccessfulTransitionsFlow() throws Exception {
        when(employeeServiceClient.updateState(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Response.of(0, ""));

        StateMachine<States, Events> machine = stateMachineProvider.getStateMachine(STATE_MACHINE_ID);
        StateMachineTestPlan<States, Events> plan =
                StateMachineTestPlanBuilder.<States, Events>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .expectStates(States.ADDED)
                        .expectStateChanged(0)
                        .and()
                        .step()
                        .sendEvent(Events.CHECK)
                        .expectState(States.IN_CHECK)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(Events.APPROVE)
                        .expectState(States.APPROVED)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(Events.ACTIVATE)
                        .expectState(States.ACTIVE)
                        .expectStateChanged(1)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    public void testFailedApproveTransition() throws Exception {
        when(employeeServiceClient.updateState(STATE_MACHINE_ID, Events.CHECK.toString()))
                .thenReturn(Response.of(0, ""));
        when(employeeServiceClient.updateState(STATE_MACHINE_ID, Events.APPROVE.toString()))
        .thenReturn(Response.of(9, ""));

        StateMachine<States, Events> machine = stateMachineProvider.getStateMachine(STATE_MACHINE_ID);
        StateMachineTestPlan<States, Events> plan =
                StateMachineTestPlanBuilder.<States, Events>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .expectStates(States.ADDED)
                        .expectStateChanged(0)
                        .and()
                        .step()
                        .sendEvent(Events.CHECK)
                        .expectState(States.IN_CHECK)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(Events.APPROVE)
                        .expectState(States.IN_CHECK)
                        .expectStateChanged(0)
                        .expectEventNotAccepted(1)
                        .and()
                        .build();
        plan.test();
    }

}
