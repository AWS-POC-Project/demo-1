package com.cfg;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.state.State;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ProcessResourceController {

	
	// process flow
	// request -> validate -> dispatch
	BankProcessState request = new BankProcessState(state.values()[0].toString());
	BankProcessState validate = new BankProcessState(state.values()[1].toString());
	BankProcessState dispatch = new BankProcessState(state.values()[2].toString());
	BankProcessState end = new BankProcessState(state.values()[3].toString());
	//
	BankProcessEvent requestComplete = new BankProcessEvent(event.values()[0].toString());
	BankProcessEvent validateComplete = new BankProcessEvent(event.values()[1].toString());
	BankProcessEvent dispatchComplete = new BankProcessEvent(event.values()[2].toString());
	// collect states and events
	Set<BankProcessState> allStates = new HashSet<>(Arrays.asList(request, validate, dispatch, end));
	Map<String, BankProcessEvent> eventMap=new HashMap<String, BankProcessEvent>();
	Map<String, BankProcessState> stateMap=new HashMap<String, BankProcessState>();

	StateMachine<BankProcessState, BankProcessEvent> stateMachine = null;

	public ProcessResourceController() {
		try {
			stateMachine=buildMachine();
			stateMachine.start();
			// map of events
			eventMap.put(requestComplete.getName(), requestComplete);
			eventMap.put(validateComplete.getName(), validateComplete);
			eventMap.put(dispatchComplete.getName(), dispatchComplete);
			// map of states
			stateMap.put("request", request);
			stateMap.put("validate", validate);
			stateMap.put("dispatch", dispatch);
			stateMap.put("end", end);
		}
		catch(Exception e) {
			System.out.println("error starting state machine");
		}
		
	}

	@RequestMapping(path = "/process-resource", method = RequestMethod.PUT)
	public String getNextStep(@RequestParam String eventName) throws Exception {
			System.out.println("Invoking Process Controller with Event: "+eventName);
		
			stateMachine.sendEvent(eventMap.get(eventName));
			// get the state : map to the resource
			State<BankProcessState, BankProcessEvent> currentState = stateMachine.getState();
			eventName=currentState.getId().getName()+"Event";
		



		return stateMachine.getState().getId().name;
	}

	public StateMachine<BankProcessState, BankProcessEvent> buildMachine() throws Exception {

		Builder<BankProcessState, BankProcessEvent> builder = StateMachineBuilder.builder();
		// TO DO: convert this to scenario, and use config db to pick this up
		// actual process flow
		builder.configureStates().withStates().initial(request).states(allStates);

		builder.configureTransitions().withExternal().source(request).target(validate).event(requestComplete).and()
				.withExternal().source(validate).target(dispatch).event(validateComplete).and().withExternal()
				.source(dispatch).target(end).event(dispatchComplete).and().withExternal()
				.source(end).target(validate).event(requestComplete);

		return builder.build();
	}

}