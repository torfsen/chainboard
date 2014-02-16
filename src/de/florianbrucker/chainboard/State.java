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
	
	public State transition(TouchEvent e) {
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

	@Override
	public State clone() {
		return new State(id);
	}
	
	@Override
	public String toString() {
		return "State(" + id + ")";
	}
}