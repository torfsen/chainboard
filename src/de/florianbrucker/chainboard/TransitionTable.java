package de.florianbrucker.chainboard;

import java.util.HashMap;
import java.util.Map;

import android.view.KeyEvent;
import android.view.View;
import de.florianbrucker.chainboard.StateMachine.TransitionFunction;

public class TransitionTable implements TransitionFunction {
	
	class Key {
		final State state;
		final int buttonId;
		
		public Key(State state, int buttonId) {
			this.state = state;
			this.buttonId = buttonId;
		}

		private TransitionTable getOuterType() {
			return TransitionTable.this;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + buttonId;
			result = prime * result + ((state == null) ? 0 : state.hashCode());
			return result;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Key)) {
				return false;
			}
			Key other = (Key) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (buttonId != other.buttonId) {
				return false;
			}
			if (state == null) {
				if (other.state != null) {
					return false;
				}
			} else if (!state.equals(other.state)) {
				return false;
			}
			return true;
		}
	}
	
	class Value {
		final State state;
		final int keyCode;
		
		public Value(State state, int keyCode) {
			this.state = state;
			this.keyCode = keyCode;
		}
	}
	
	Map<Key, Value> table = new HashMap<Key, Value>();
	
	View view;
	
	public TransitionTable(View view) {
		this.view = view;
		
		put("", 1, "1", KeyEvent.KEYCODE_1);
		put("1", 1, "", KeyEvent.KEYCODE_2);
	}
	
	void put(String oldId, int buttonId, String newId, int keyCode) {
		table.put(new Key(new State(oldId), buttonId), new Value(new State(newId), keyCode));
	}
	
	void pressKey(int code) {
		view.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, code));
		view.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, code));
	}

	@Override
	public State transition(State oldState, ButtonEvent event) {
		Key key = new Key(oldState, event.buttonId);
		if (!table.containsKey(key)) {
			pressKey(KeyEvent.KEYCODE_ENTER);
			return State.NULL_STATE;
		}
		Value value = table.get(key);
		pressKey(value.keyCode);
		return value.state;
	}

}
