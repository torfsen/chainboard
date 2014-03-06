package de.florianbrucker.chainboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	}
	
	public void loadFromFile(BufferedReader reader) throws IOException {
		Pattern pattern = Pattern.compile("(\\d+)\\s+(\\w+|\".*\"|'.*')(?:\\s+(\\d+))?");
		String line;
		int lineNumber = 0;
		while ((line = reader.readLine()) != null) {
			lineNumber++;
			line = line.trim();
			if (line.length() == 0 || line.startsWith("#")) {
				continue;
			}
			Log.d(TAG, "Parsing \"" + line + "\"");
			Matcher matcher = pattern.matcher(line);
			if (!matcher.matches()) {
				throw new IllegalArgumentException("Could not parse line " + lineNumber + ": Illegal format.");
			}
			String oldId = matcher.group(1);
			String action = matcher.group(2);
			String newId = matcher.group(3);
			if (newId == null) {
				newId = "";
			}
			if (action.startsWith("\"") || action.startsWith("'")) {
				String string = String.format(Locale.US, action.substring(1, action.length() - 1));
				Log.d(TAG, "Parsed (" + line + ") into " + oldId + " " + string + " " + newId);
				put(oldId, newId, string);
			} else {
				String fieldName = "KEYCODE_" + action;
				int keyCode;
				try {
					Field field = KeyEvent.class.getDeclaredField(fieldName);
					keyCode = field.getInt(null);
				} catch (NoSuchFieldException e) {
					throw new IllegalArgumentException("Invalid keycode \"" + action + "\" on line " + lineNumber + ".");
				} catch (IllegalAccessException e) {
					throw new IllegalArgumentException("Invalid keycode \"" + action + "\" on line " + lineNumber + ".");
				}
				Log.d(TAG, "Parsed (" + line + ") into " + oldId + " " + keyCode + " " + newId);
				put(oldId, newId, keyCode);
			}
		}
	}
	
	
	void put(String oldId, String newId, String s) {
		State oldState = new State(oldId);
		State newState = new State(newId);
		if (!newState.isCompatibleWith(oldState)) {
			throw new IllegalTransitionException(oldState, newState);
		}
		table.put(oldState, new StringValue(newState, s));
	}
	
	void put(String oldId, String newId, int keyCode) {
		State oldState = new State(oldId);
		State newState = new State(newId);
		if (!newState.isCompatibleWith(oldState)) {
			throw new IllegalTransitionException(oldState, newState);
		}
		table.put(oldState, new KeyCodeValue(newState, keyCode));
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
