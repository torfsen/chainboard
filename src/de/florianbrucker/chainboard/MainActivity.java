package de.florianbrucker.chainboard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import de.florianbrucker.chainboard.ChainedLinearLayout.ChainedTouchEventListener;

public class MainActivity extends Activity implements ChainedTouchEventListener {
	
	final static String DEBUG_TAG = "KEYBOARD_DEBUG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ChainedLinearLayout buttongroup = (ChainedLinearLayout) findViewById(R.id.buttongroup);
		buttongroup.addListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public void onChainedTouchEvent(int i) {
		EditText text = (EditText) findViewById(R.id.text);
		String s = Integer.toString(i);
		text.append(s + "\n");
	}
	

}
