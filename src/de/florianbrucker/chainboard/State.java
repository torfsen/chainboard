package de.florianbrucker.chainboard;

import android.util.Log;

public class State {
	
	final static String TAG = "STATE";
	
	final static State NULL_STATE = new State("");
	
	final String id;
	
	public State(String id) {
		this.id = decompressId(id);
	}
	
	public String getId() {
		return id;
	}
	
	public State transition(ButtonEvent e) {
		return new State(id + Integer.toString(e.buttonId));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof State)) {
			return false;
		}
		State other = (State) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	public boolean[] getButtonState() {
		boolean isDown[] = new boolean[2 * ChainBoard.BUTTONS_PER_BOARD];
		for (int i = 0; i < id.length(); i++) {
			int key = id.charAt(i) - '0';
			if (key < 0 || key >= isDown.length) {
				throw new IllegalArgumentException("Invalid state ID character \"" + id.charAt(i) + "\" in ID \"" + id + "\".");
			}
			isDown[key] = !isDown[key];
		}
		return isDown;
	}
	
	public int getNumberOfPressedButtons() {
		boolean[] buttonState = getButtonState();		
		int numDown = 0;
		for (int i = 0; i < buttonState.length; i++) {
			if (buttonState[i]) {
				numDown++;
			}
		}
		return numDown;
	}
	
	public boolean isCompatibleWith(State other) {
		boolean[] thisState = getButtonState();
		boolean[] otherState = other.getButtonState();		
		if (thisState.length != otherState.length) {
			return false;
		}
		for (int i = 0; i < thisState.length; i++) {
			if (thisState[i] != otherState[i]) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public State clone() {
		return new State(id);
	}
	
	@Override
	public String toString() {
		return "State(" + id + ")";
	}
	
	public static String decompressId(String id) {
		StringBuilder builder = new StringBuilder();
		int[] handDown = {-1, -1};
		for (int i = 0; i < id.length(); i++) {
			char c = id.charAt(i);
			int key = c - '0';
			if (key < 0 || key >= 2 * ChainBoard.BUTTONS_PER_BOARD) {
				throw new IllegalArgumentException("Illegal character \"" + c + "\" in state \"" + id + "\".");
			}
			int hand = key % 2;
			if (handDown[hand] == -1) {
				builder.append(c);
				handDown[hand] = key;
			} else if (handDown[hand] == key) {
				builder.append(c);
				handDown[hand] = -1;
			} else {
				builder.append((char)(handDown[hand] + '0'));
				builder.append(c);
				handDown[hand] = key;
			}
			if (i < id.length() - 1 && handDown[0] == -1 && handDown[1] == -1) {
				throw new IllegalArgumentException("State \"" + id + "\" is not connected.");
			}
		}
		Log.d(TAG, "decompressId(" + id + ") = " + builder.toString());
		return builder.toString();
	}
}