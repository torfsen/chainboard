package de.florianbrucker.chainboard;

import java.util.Collection;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MotionEventConverter implements OnTouchListener {
	
	final static String TAG = "MotionEventConverter";
			
	public interface TouchEventListener {
		public void onTouchEvent(TouchEvent e);
	}	
	
	Collection<TouchEventListener> listeners = new java.util.LinkedList<TouchEventListener>();
	
	public void addListener(TouchEventListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(TouchEventListener listener) {
		listeners.remove(listener);
	}
	
	void fireEvent(TouchEvent e) {
		Log.d(TAG, "fireEvent(" + e + ")");
		for (TouchEventListener listener : listeners) {
			listener.onTouchEvent(e);
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {				
		TouchEvent.Direction direction;
		switch (MotionEventCompat.getActionMasked(event)) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				Log.d(TAG, "DOWN");
				direction = TouchEvent.Direction.DOWN;
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				Log.d(TAG, "UP");
				direction = TouchEvent.Direction.UP;
				break;
			default:
				return false;
		}
		
		int buttonId = 1; // FIXME: Calculate correct button ID
		
		fireEvent(new TouchEvent(buttonId, direction));
		
		return true;
	}

}