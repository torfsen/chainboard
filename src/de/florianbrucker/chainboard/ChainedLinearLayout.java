package de.florianbrucker.chainboard;

import java.util.Collection;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class ChainedLinearLayout extends LinearLayout {
	
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
	
	public ChainedLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ChainedLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event){
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		
		int action = MotionEventCompat.getActionMasked(event);
		int index = MotionEventCompat.getActionIndex(event);
		int id = MotionEventCompat.getPointerId(event, index);
		
		final String DEBUG_TAG = "KEYBOARD";
		
		if (action != MotionEvent.ACTION_MOVE){
			Log.d(DEBUG_TAG, action + "," + index + ", " + id);
			//Log.d(DEBUG_TAG,Integer.toString(state.currentlyDown));
		}
		
		if (action == MotionEvent.ACTION_DOWN || action == MotionEventCompat.ACTION_POINTER_DOWN) {
			state.currentlyDown++;
			state.chainLength++;
		} else if (action == MotionEvent.ACTION_UP || action == MotionEventCompat.ACTION_POINTER_UP) {
			state.currentlyDown--;
			if (state.currentlyDown == 0) {
				fireEvent(state.chainLength);
				state.chainLength = 0;
			}
		}
		
		return true;
		
	}

}
