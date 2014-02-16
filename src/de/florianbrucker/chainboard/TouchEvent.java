package de.florianbrucker.chainboard;


public class TouchEvent {
	
	public static enum Direction {
		UP("UP"), DOWN("DOWN");
		
		final String text;
		
		Direction(String text) {
			this.text = text;
		}
		
		@Override
		public String toString() {
			return this.text;
		}
	}
	
	final int buttonId;
	final Direction direction;
	
	public TouchEvent(int buttonId, Direction direction) {
		this.buttonId = buttonId;
		this.direction = direction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + buttonId;
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TouchEvent))
			return false;
		TouchEvent other = (TouchEvent) obj;
		if (buttonId != other.buttonId)
			return false;
		if (direction != other.direction)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TouchEvent(" + buttonId + ", " + direction + ")";
	}
	

}
