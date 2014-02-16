package de.florianbrucker.chainboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	final static String DEBUG_TAG = "KEYBOARD_DEBUG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		View text = findViewById(R.id.text);
		TransitionTable table = new TransitionTable(text);
		StateMachine machine = new StateMachine(table);
		
		MotionEventConverter touchListener = new MotionEventConverter();
		touchListener.addListener(machine);
		
		((Button) findViewById(R.id.button1)).setOnTouchListener(touchListener);
		((Button) findViewById(R.id.button2)).setOnTouchListener(touchListener);
		((Button) findViewById(R.id.button3)).setOnTouchListener(touchListener);
		((Button) findViewById(R.id.button4)).setOnTouchListener(touchListener);
		((Button) findViewById(R.id.button5)).setOnTouchListener(touchListener);
		((Button) findViewById(R.id.button6)).setOnTouchListener(touchListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
