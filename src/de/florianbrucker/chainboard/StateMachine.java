package de.florianbrucker.chainboard;

import android.util.Log;

public class StateMachine implements MotionEventConverter.TouchEventListener {
	
	final static String TAG = "StateMachine";
	
	public interface TransitionFunction {
		public State transition(State oldState, TouchEvent event);
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
	public void onTouchEvent(TouchEvent e) {
		Log.d(TAG, "Received TouchEvent " + e);
		Log.d(TAG, "Current state is " + state);
		state = transitionFunction.transition(state, e);
		Log.d(TAG, "New state is " + state);
	}
}
