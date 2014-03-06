package de.florianbrucker.chainboard;

import java.util.ArrayList;
import java.util.Collection;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MotionEventCollector implements OnTouchListener {
	
	final static String TAG = "MotionEventCollector";
	
	Collection<ButtonEventListener> listeners = new java.util.LinkedList<ButtonEventListener>();
	
	ArrayList<Integer> pointerButtons = new ArrayList<Integer>();
	
	public void addButtonEventListener(ButtonEventListener listener) {
		listeners.add(listener);
	}
	
	public void removeButtonEventListener(ButtonEventListener listener) {
		listeners.remove(listener);
	}
	
	void fireEvent(ButtonEvent e) {
		for (ButtonEventListener listener : listeners) {
			listener.onButtonEvent(e);
		}
	}
	
	public MotionEventCollector() {
		
		// People with more than 10 fingers may cause an IndexOutOfBoundsException...
		for (int i = 0; i < 10; i++) {
			pointerButtons.add(-1);
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {				
		ButtonEvent.Direction direction;
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				direction = ButtonEvent.Direction.DOWN;
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				direction = ButtonEvent.Direction.UP;
				break;
			default:
				return false;
		}
		
		int index = event.getActionIndex();
		int pointerId = event.getPointerId(index);
		int buttonId;
		
		if (direction == ButtonEvent.Direction.DOWN) { 
			float buttonHeight = v.getHeight() / ChainBoard.BUTTONS_PER_BOARD;				
			int buttonRow = (int)(event.getY(index) / buttonHeight);
			
			/*
			 * It is possible to click below the lowest button. To avoid
			 * illegal button IDs we clamp the button row to the correct
			 * range.
			 */
			buttonRow = Math.min(ChainBoard.BUTTONS_PER_BOARD - 1, Math.max(buttonRow, 0));
			
			/*
			 * Android reports actions from the second pointer w.r.t.
			 * the view of the first pointer. This means we need to
			 * compare the pointer locations to the view geometries
			 * to figure out in which view the pointer actually is.
			 */
			float x = event.getX(index) + v.getLeft();
			int buttonColumn = x > v.getWidth() ? 1 : 0;
			buttonId = buttonColumn + 2 * buttonRow;
			
			pointerButtons.set(pointerId, buttonId);
		} else {
			/*
			 * For UP events we cannot use the pointer position because the
			 * pointer could have been dragged to another button.
			 */
			buttonId = pointerButtons.get(pointerId);
		}
		
		Log.d(TAG, buttonId + " " + direction);
		
		fireEvent(new ButtonEvent(buttonId, direction));
		
		return true;
	}

}
