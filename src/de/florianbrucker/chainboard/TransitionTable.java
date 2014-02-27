package de.florianbrucker.chainboard;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import de.florianbrucker.chainboard.StateMachine.TransitionFunction;

public class TransitionTable implements TransitionFunction {	
	
	final static String TAG = "TransitionTable";
	
	abstract class Value {
		final State state;
		
		public Value(State state) {
			this.state = state;
		}
		
		public abstract void performAction();
	}
	
	class StringValue extends Value {
		final String string;				
		
		public StringValue(State state, String string) {
			super(state);
			this.string = string;
		}
		
		public void performAction() {
			edit.getText().insert(edit.getSelectionStart(), string);
		}
	}
	
	class KeyCodeValue extends Value {
		final int keyCode;
		
		public KeyCodeValue(State state, int keyCode) {
			super(state);
			this.keyCode = keyCode;
		}
		
		public void performAction() {
			edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode));
			edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyCode));
		}
	}
	
	Map<State, Value> table = new HashMap<State, Value>();
	
	EditText edit;
	
	public TransitionTable(EditText edit) {
		this.edit = edit;
		put("00", "", "1");
		put("22", "", "2");
		put("11", "", KeyEvent.KEYCODE_DEL);
	}
	
	void put(String oldId, String newId, String s) {
		table.put(new State(oldId), new StringValue(new State(newId), s));
	}
	
	void put(String oldId, String newId, int keyCode) {
		table.put(new State(oldId), new KeyCodeValue(new State(newId), keyCode));
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
		value.performAction();
		return value.state;
	}

}
