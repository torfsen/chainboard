package de.florianbrucker.chainboard;

import java.util.Collection;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MotionEventCollector implements OnTouchListener {
	
	final static String TAG = "MotionEventCollector";
	
	Collection<ButtonEventListener> listeners = new java.util.LinkedList<ButtonEventListener>();
	
	int buttonCount = 0;
	
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
	
	public MotionEventCollector(int buttonCount) {
		this.buttonCount = buttonCount;
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
		float buttonHeight = v.getHeight() / buttonCount;				
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
		int buttonId = buttonColumn + 2 * buttonRow;
		
		
		
		Log.d(TAG, buttonId + " " + direction + " " + x);
		
		fireEvent(new ButtonEvent(buttonId, direction));
		
		return true;
	}

}
