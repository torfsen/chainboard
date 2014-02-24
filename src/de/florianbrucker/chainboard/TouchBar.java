package de.florianbrucker.chainboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class TouchBar extends LinearLayout {
	

	public TouchBar(Context context) {
		super(context);
	}

	public TouchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		// Intercept all events
		return true;
	}		
	
	
				
	

}
