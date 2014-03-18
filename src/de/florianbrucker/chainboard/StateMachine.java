package de.florianbrucker.chainboard;


import java.util.LinkedList;
import java.util.List;

import android.util.Log;

public class StateMachine implements ButtonEventListener {
	
	final static String TAG = "StateMachine";
	
	public interface TransitionFunction {
		public State transition(State state);
	}
	
	
	public interface StateChangeListener {
		public void onStateChange(State oldState, State newState);
	}
	
	List<StateChangeListener> stateChangeListeners = new LinkedList<StateChangeListener>();
	
	public void addStateChangeListener(StateChangeListener listener) {
		stateChangeListeners.add(listener);
	}
	
	public void removeStateChangeListener(StateChangeListener listener) {
		stateChangeListeners.remove(listener);
	}
	
	void fireStateChangeEvent(State oldState, State newState) {
		for (StateChangeListener listener : stateChangeListeners) {
			listener.onStateChange(oldState, newState);
		}
	}
	
	TransitionFunction transitionFunction = null;
	
	private State state;
	
	public State getState() {
		return state;
	}
	
	public StateMachine(State state, TransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
		this.state = state;
	}
	
	@Override
	public void onButtonEvent(ButtonEvent e) {
		State oldState = state;
		State intermediateState = state.transition(e);
		state = transitionFunction.transition(intermediateState);
		if (!state.isCompatibleWith(intermediateState)) {
			throw new IllegalTransitionException(intermediateState, state);
		}
		Log.d(TAG, "State transition: " + oldState + " --[" + intermediateState + "]--> " + state);
		fireStateChangeEvent(oldState, state);
	}
	
}
