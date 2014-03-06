package de.florianbrucker.chainboard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements ButtonEventListener {
	
	final static String TAG = "MainActivity";
	
	Button[] buttons = new Button[2 * ChainBoard.BUTTONS_PER_BOARD];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EditText text = (EditText)findViewById(R.id.text);		
		TransitionTable table = new TransitionTable(text);
		InputStream inputStream = getResources().openRawResource(R.raw.transition_table);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		try {
			table.loadFromFile(new BufferedReader(inputStreamReader));
		} catch (Exception e) {
			Log.e(TAG, "Could not load transition table", e);
		}
		StateMachine machine = new StateMachine(table);
		
		TouchBar leftBar = (TouchBar)findViewById(R.id.leftBar);
		TouchBar rightBar = (TouchBar)findViewById(R.id.rightBar);
		buttons[0] = (Button)leftBar.findViewById(R.id.button0);
		buttons[2] = (Button)leftBar.findViewById(R.id.button1);
		buttons[4] = (Button)leftBar.findViewById(R.id.button2);
		buttons[6] = (Button)leftBar.findViewById(R.id.button3);
		buttons[1] = (Button)rightBar.findViewById(R.id.button0);
		buttons[3] = (Button)rightBar.findViewById(R.id.button1);
		buttons[5] = (Button)rightBar.findViewById(R.id.button2);
		buttons[7] = (Button)rightBar.findViewById(R.id.button3);

		MotionEventCollector collector = new MotionEventCollector();
		leftBar.setOnTouchListener(collector);
		rightBar.setOnTouchListener(collector);
		collector.addButtonEventListener(machine);
		collector.addButtonEventListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onButtonEvent(ButtonEvent e) {
		if (e.direction == ButtonEvent.Direction.DOWN) {
			buttons[e.buttonId].setBackgroundResource(R.drawable.button_down);
		} else {
			buttons[e.buttonId].setBackgroundResource(R.drawable.button_default);
		}
	}
	
}
