package de.florianbrucker.chainboard;

import android.util.Log;

public class StateMachine implements ButtonEventListener {
	
	final static String TAG = "StateMachine";
	
	public interface TransitionFunction {
		public State transition(State state);
	}
	
	TransitionFunction transitionFunction = null;
	
	State state = null;
	
	public StateMachine(TransitionFunction transitionFunction, State state) {
		this.transitionFunction = transitionFunction;
		this.state = state;
	}
	
	public StateMachine(TransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
		this.state = State.NULL_STATE;
	}
	
	@Override
	public void onButtonEvent(ButtonEvent e) {
		Log.d(TAG, "Received ButtonEvent " + e);
		Log.d(TAG, "Old state is " + state);
		state = state.transition(e);
		Log.d(TAG, "State after button event is " + state);
		state = transitionFunction.transition(state);
		Log.d(TAG, "New state is " + state);
	}
}
