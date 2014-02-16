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
	public boolean equals(Object o) {
		return o instanceof State && ((State)o).id.equals(id);
	}
	
	@Override
	public State clone() {
		return new State(id);
	}
	
	
}