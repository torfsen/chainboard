package de.florianbrucker.chainboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	final static String DEBUG_TAG = "KEYBOARD_DEBUG";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EditText text = (EditText)findViewById(R.id.text);
		TransitionTable table = new TransitionTable(text);
		StateMachine machine = new StateMachine(table);
		
		TouchBar leftBar = (TouchBar)findViewById(R.id.leftBar);
		TouchBar rightBar = (TouchBar)findViewById(R.id.rightBar);

		MotionEventCollector collector = new MotionEventCollector(ChainBoard.BUTTONS_PER_BOARD);
		leftBar.setOnTouchListener(collector);
		rightBar.setOnTouchListener(collector);
		collector.addButtonEventListener(machine);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
