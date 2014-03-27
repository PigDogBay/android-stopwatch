package com.pigdogbay.stopwatch;

import com.pigdogbay.androidutils.games.FrameBuffer;
import com.pigdogbay.androidutils.games.GameView.IGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class StopWatchGame implements IGame{

	final static int DIGIT_WIDTH = 100;
	final static int DIGIT_HEIGHT = 320;

	Model _Model;
	public Bitmap _DigitsBitmap, _StartBmp, _ResetBmp;
	FrameBuffer _Buffer;
	TimeDigits _TimeDigits = new TimeDigits();

	public StopWatchGame(Model model){
		_Model = model;
        _Buffer = new FrameBuffer(1280, 800);
	}

	@Override
	public void Update() {
		_TimeDigits.updateTime(_Model.getElapsedTime());
	}
	@Override
	public void Render(Canvas c) {
		int[] digits = _TimeDigits.Digits;
		for (int i=0;i<12;i++){
			_Buffer.draw(_DigitsBitmap,40+DIGIT_WIDTH*i,40,digits[i]*DIGIT_WIDTH,0,DIGIT_WIDTH, DIGIT_HEIGHT);
		}
		//Draw buttons
		_Buffer.draw(_ResetBmp,40,440);
		_Buffer.draw(_StartBmp,640,440);
		
		_Buffer.scaleToFit(c);
		
	}
	
	public void close()
	{
		_DigitsBitmap.recycle();
		_Buffer.close();
		
	}

}
