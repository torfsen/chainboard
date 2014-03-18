package de.florianbrucker.chainboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class StatePool {
	
	final static String TAG = "StatePool";
	
	Map<String, State> states = new HashMap<String, State>();
	
	final State NULL_STATE;
	
	public StatePool() {
		NULL_STATE = new State(this, "");
		states.put("", NULL_STATE);
	}
	
	public State getState(String id) {
		id = State.decompressId(id);
		if (states.containsKey(id)) {
			return states.get(id);
		}
		State state = new State(this, id);
		states.put(id, state);
		return state;
	}
	
	public void loadLabels(BufferedReader reader) throws IOException {
		Pattern pattern = Pattern.compile("((?:\\w+)|~)\\s+(.*)");
		Pattern subPattern = Pattern.compile("(\\d+)\\s*:\\s*\"([^\"]*)\"");
		String line;
		int lineNumber = 0;
		while ((line = reader.readLine()) != null) {
			lineNumber++;
			line = line.trim();
			if (line.length() == 0 || line.startsWith("#")) {
				continue;
			}
			Matcher matcher = pattern.matcher(line);
			if (!matcher.matches()) {
				throw new IllegalArgumentException("Could not parse line " + lineNumber + ": Illegal format.");
			}
			String id = matcher.group(1);
			if (id.equals("~")) {
				id = "";
			}
			State state = getState(id);
			Log.d(TAG, "Loading labels for " + state);
			Matcher subMatcher = subPattern.matcher(matcher.group(2));
			while (subMatcher.find()) {
				int button = Integer.parseInt(subMatcher.group(1));
				String label = subMatcher.group(2);
				Log.d(TAG, "  label[" + button + "] = \"" + label + "\"");
				state.labels[button] = label;
			}
		}
	}

}
