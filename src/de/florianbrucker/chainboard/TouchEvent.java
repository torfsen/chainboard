package de.florianbrucker.chainboard;


public class TouchEvent {
	
	public static enum Direction {
		UP, DOWN 
	}
	
	int buttonId = 0;
	Direction direction = Direction.DOWN;
	
	public TouchEvent(int buttonId, Direction direction) {
		this.buttonId = buttonId;
		this.direction = direction;
	}
}
