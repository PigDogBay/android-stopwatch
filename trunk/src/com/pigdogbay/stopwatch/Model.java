package com.pigdogbay.stopwatch;

import java.util.Date;

public class Model {

	private boolean _Running;
	private long _StartTime, _AccumulatedTime;

	public boolean isRunning() {
		return _Running;
	}

	public void setRunning(boolean running) {
		_Running = running;
	}

	public long getStartTime() {
		return _StartTime;
	}

	public void setStartTime(long startTime) {
		this._StartTime = startTime;
	}

	public long getAccumulatedTime() {
		return _AccumulatedTime;
	}

	public void setAccumulatedTime(long accumulatedTime) {
		this._AccumulatedTime = accumulatedTime;
	}

	public void toggle() {
		if (_Running) {
			// Pause timer, tot up time elapsed so far
			_AccumulatedTime += new Date().getTime() - _StartTime;
		} else {
			// new start time
			_StartTime = new Date().getTime();
		}
		_Running = !_Running;
	}

	public long getElapsedTime() {
		if (_Running) {
			long now = new Date().getTime();
			return _AccumulatedTime + now - getStartTime();
		}
		return _AccumulatedTime;
	}

	public void reset() {
		_AccumulatedTime = 0;
		_StartTime = new Date().getTime();
	}

	public void load(String data) {
		String[] parts = data.split("\n");
		_StartTime = Long.parseLong(parts[0]);
		_AccumulatedTime = Long.parseLong(parts[1]);
		_Running = Boolean.parseBoolean(parts[2]);
	}

	public String save() {
		StringBuilder sbuff = new StringBuilder();
		sbuff.append(Long.toString(_StartTime));
		sbuff.append("\n");
		sbuff.append(Long.toString(_AccumulatedTime));
		sbuff.append("\n");
		sbuff.append(Boolean.toString(_Running));
		return sbuff.toString();
	}

}
