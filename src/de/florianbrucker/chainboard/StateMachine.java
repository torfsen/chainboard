package de.florianbrucker.chainboard;

public class StateMachine implements MotionEventConverter.TouchEventListener {
	
	public interface StateChangeListener {
		public State onStateChange(State s);
	}
	
	public void setStateChangeListener(StateChangeListener listener) {
		this.listener = listener;
	}
	
	StateChangeListener listener = null;
	
	State state = null;
	
	public StateMachine(State state) {
		this.state = state;
	}
	
	public StateMachine() {
		this.state = State.NULL_STATE;
	}
	
	void fireEvent() {
		if (listener != null) {
			state = listener.onStateChange(state);			
		}
	}
	
	@Override
	public void onTouchEvent(TouchEvent e) {
		state = state.transition(e);
		fireEvent();
	}
}
