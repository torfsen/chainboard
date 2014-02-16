package de.florianbrucker.chainboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import de.florianbrucker.chainboard.StateMachine.StateChangeListener;

public class MainActivity extends Activity implements StateChangeListener {
	
	final static String DEBUG_TAG = "KEYBOARD_DEBUG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StateMachine machine = new StateMachine();
		machine.setStateChangeListener(this);
		
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
	
	
	@Override
	public State onStateChange(State s) {
		EditText text = (EditText) findViewById(R.id.text);
		text.append(s.getId() + "\n");
		return s;
	}
	
}
