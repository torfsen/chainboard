package de.florianbrucker.chainboard;

public class State {
	
	final static State NULL_STATE = new State("");
	
	final String id;
	
	public State(String id) {
		this.id = id;
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
	
	
	public int getNumberOfPressedButtons() {
		boolean isDown[] = new boolean[2 * ChainBoard.BUTTONS_PER_BOARD];
		for (int i = 0; i < id.length(); i++) {
			int key = id.charAt(i) - '0';
			isDown[key] = !isDown[key];
		}
		int numDown = 0;
		for (int i = 0; i < isDown.length; i++) {
			if (isDown[i]) {
				numDown++;
			}
		}
		return numDown;
	}
	

	@Override
	public State clone() {
		return new State(id);
	}
	
	@Override
	public String toString() {
		return "State(" + id + ")";
	}
}