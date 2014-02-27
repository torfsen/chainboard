package de.florianbrucker.chainboard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	final static String TAG = "MainActivity";
	
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
