package com.pigdogbay.stopwatch;

import com.pigdogbay.androidutils.games.FrameBuffer;
import com.pigdogbay.stopwatch.ObjectTouchHandler.ITouchable;
import com.pigdogbay.stopwatch.ObjectTouchHandler.TouchState;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class BitmapButton implements ITouchable {

	public interface OnClickListener{
		void onClick(Object sender);
	}
	private OnClickListener _Listener;
	public void setOnClickListener(OnClickListener listener) {
		_Listener = listener;
	}
	
	private Bitmap _Bitmap, _PressedBitmap;
	private int _X, _Y;
	protected Rect _Bounds;
	protected volatile boolean _IsPressed;
	public BitmapButton(Bitmap bitmap, Bitmap pressedBitmap, int x, int y)  {
		super();
		_X=x;
		_Y=y;
		_Bitmap = bitmap;
		_PressedBitmap = pressedBitmap;
		_Bounds.left = x;
		_Bounds.top = y;
		_Bounds.right = x+bitmap.getWidth();
		_Bounds.bottom = y+bitmap.getHeight();
		
	}


	public void draw(FrameBuffer buffer){
		if (_IsPressed)
		{
			buffer.draw(_PressedBitmap, _X, _Y);
		}
		else
		{
			buffer.draw(_Bitmap, _X, _Y);
		}
	}

	@Override
	public boolean contains(int x, int y) {
		return _Bounds.contains(x, y);
	}
	@Override
	public void setTouchState(TouchState state) {
		if (_IsPressed)
		{
			setTouchStatePressed(state);
		}
		else if(TouchState.Down==state)
		{
			_IsPressed = true;
		}
	}

	private void setTouchStatePressed(TouchState state) {
		switch (state){
		case Cancel:
		case DragOutside:
		case UpOutside:
			_IsPressed = false;
			break;
		case UpInside:
			_IsPressed = false;
			fireOnClick();
			break;
		case None:
		case Down:
		case DragInside:
		default:
			break;
		
		}
	}

	private void fireOnClick()
	{
		if (_Listener!=null){
			_Listener.onClick(this);
		}
	}
}
