package com.pigdogbay.stopwatch;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Maps touch events to registered listeners
 * Useful for bitmap buttons
 *
 */
public class ObjectTouchHandler implements OnTouchListener {

	public enum TouchState {
		None, Down, DragInside, DragOutside, UpInside, UpOutside, Cancel
	}

	public interface ITouchable {
		
		/**
		 * Checks to see if the event is inside the touch object
		 * Note - pre-scale any bounds before hand
		 * @param x x co-ord of the touch screen event
		 * @param y y co-ord of the touch screen event
		 * @return true if the event is inside
		 */
		boolean contains(int x, int y);

		/**
		 * Use this method to listen for touch events, keep any processing very lightweight e.g. just set flags
		 * @param state new state
		 */
		void setTouchState(TouchState state);
	}

	List<ITouchable> _Touchables;
	ITouchable _Selected = null;

	public ObjectTouchHandler() {
		_Touchables = new ArrayList<ObjectTouchHandler.ITouchable>();
	}

	public void add(ITouchable touchable) {
		_Touchables.add(touchable);
	}

	public void remove(ITouchable touchable) {
		_Touchables.remove(touchable);
	}

	public void clearTouchables() {
		_Touchables.clear();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getActionIndex() != 0) {
			// ignore multi-touch events
			return false;
		}
		TouchState state = TouchState.None;
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			findSelected(event);
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			if (_Selected != null) {
				state = _Selected.contains((int) event.getX(),
						(int) event.getY()) ? TouchState.UpInside
						: TouchState.UpOutside;
				_Selected.setTouchState(state);
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			if (_Selected != null) {
				_Selected.setTouchState(TouchState.Cancel);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (_Selected != null) {
				state = _Selected.contains((int) event.getX(),
						(int) event.getY()) ? TouchState.DragInside
						: TouchState.DragOutside;
				_Selected.setTouchState(state);
			}
			break;
		}
		//Selected null - return false - not handling event
		//Selected not null - return true - handling event
		return _Selected!=null;
	}

	private void findSelected(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		_Selected = null;
		for (ITouchable t : _Touchables) {
			if (t.contains(x, y)) {
				_Selected = t;
				break;
			}
		}

	}
}
