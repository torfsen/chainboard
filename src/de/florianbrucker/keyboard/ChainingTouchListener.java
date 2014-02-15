package de.florianbrucker.keyboard;

import java.util.Collection;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;

public class ChainingTouchListener implements OnTouchListener {
	
	public interface ChainedTouchEventListener {
		public void onChainedTouchEvent(int i);
	}
	
	class State {
		int currentlyDown = 0;
		int chainLength = 0;
	}
	
	Collection<ChainedTouchEventListener> listeners = new java.util.LinkedList<ChainedTouchEventListener>();
	
	public void addListener(ChainedTouchEventListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ChainedTouchEventListener listener) {
		listeners.remove(listener);
	}
	
	State state = new State();
	
	void fireEvent(int i) {
		for (ChainedTouchEventListener listener : listeners) {
			listener.onChainedTouchEvent(i);
		}
	}
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		int action = MotionEventCompat.getActionMasked(event);
		
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
			state.currentlyDown++;
			state.chainLength++;
			return true;
		} else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
			state.currentlyDown--;
			if (state.currentlyDown == 0) {
				fireEvent(state.chainLength);
				state.chainLength = 0;
			}
			return true;
		}
		
		return false;
	}

}
