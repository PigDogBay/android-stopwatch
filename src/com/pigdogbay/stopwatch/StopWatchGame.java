package com.pigdogbay.stopwatch;

import com.pigdogbay.androidutils.games.FrameBuffer;
import com.pigdogbay.androidutils.games.GameView.IGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class StopWatchGame implements IGame{

	final static int DIGIT_WIDTH = 240;
	final static int DIGIT_HEIGHT = 320;

	Model _Model;
	Bitmap _DigitsBitmap;
	FrameBuffer _Buffer;
	TimeDigits _TimeDigits = new TimeDigits();

	public void setDigitsBitmap(Bitmap value){
		_DigitsBitmap = value;
	}
	
	public StopWatchGame(Model model){
		_Model = model;
        _Buffer = new FrameBuffer(12*240, 320);
	}

	@Override
	public void Update() {
		_TimeDigits.updateTime(_Model.getElapsedTime());
	}
	@Override
	public void Render(Canvas c) {
		int[] digits = _TimeDigits.Digits;
		for (int i=0;i<12;i++){
			_Buffer.draw(_DigitsBitmap,DIGIT_WIDTH*i,0,digits[i]*DIGIT_WIDTH,0,DIGIT_WIDTH, DIGIT_HEIGHT);
		}
		_Buffer.scaleToFit(c);
		
	}
	
	public void close()
	{
		_DigitsBitmap.recycle();
		_Buffer.close();
		
	}

}
