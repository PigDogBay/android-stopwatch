package com.pigdogbay.stopwatch;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

	java.text.SimpleDateFormat _SimpleDateFormat;
	Date _Date;
	int _Digits[] = new int[12];

	public void setDigitsBitmap(Bitmap value){
		_DigitsBitmap = value;
	}
	
	public StopWatchGame(Model model){
		_Model = model;
		_SimpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS",Locale.US);
        _Buffer = new FrameBuffer(12*240, 320);
        _Date = new Date();
	}

	@Override
	public void Update() {
		_Date.setTime(_Model.getElapsedTime());
		String time = _SimpleDateFormat.format(_Date);
		for (int i=0;i<12;i++)
		{
			_Digits[i] = time.charAt(i)-'0';
		}
		
	}
	@Override
	public void Render(Canvas c) {
		for (int i=0;i<12;i++){
			_Buffer.draw(_DigitsBitmap,DIGIT_WIDTH*i,0,_Digits[i]*DIGIT_WIDTH,0,DIGIT_WIDTH, DIGIT_HEIGHT);
		}
		_Buffer.scaleToFit(c);
		
	}
	
	public void close()
	{
		_DigitsBitmap.recycle();
		_Buffer.close();
		
	}

}
