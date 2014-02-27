package de.florianbrucker.chainboard;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import de.florianbrucker.chainboard.StateMachine.TransitionFunction;

public class TransitionTable implements TransitionFunction {	
	
	final static String TAG = "TransitionTable";
	
	class Value {
		final State state;
		final int keyCode;
		
		public Value(State state, int keyCode) {
			this.state = state;
			this.keyCode = keyCode;
		}
	}
	
	Map<State, Value> table = new HashMap<State, Value>();
	
	View view;
	
	public TransitionTable(View view) {
		this.view = view;
		put("00", "", KeyEvent.KEYCODE_1);
		put("11", "", KeyEvent.KEYCODE_2);
	}
	
	void put(String oldId, String newId, int keyCode) {
		table.put(new State(oldId), new Value(new State(newId), keyCode));
	}
	
	void pressKey(int code) {
		view.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, code));
		view.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, code));
	}

	@Override
	public State transition(State state) {
		if (!table.containsKey(state)) {
			if (state.getNumberOfPressedButtons() == 0) {
				Log.d(TAG, "Reached unset dead-end state " + state);
				// TODO: Notify the user about reaching an unset dead-end state
				return State.NULL_STATE;
			}
			return state;
		}
		Value value = table.get(state);
		pressKey(value.keyCode);
		return value.state;
	}

}
