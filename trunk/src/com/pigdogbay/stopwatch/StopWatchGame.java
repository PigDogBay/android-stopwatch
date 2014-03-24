package com.pigdogbay.stopwatch;

import java.text.SimpleDateFormat;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class StopWatchGame implements IGame{

	Paint _TextPaint;
	java.text.SimpleDateFormat _SimpleDateFormat;
	Model _Model;
	
	public StopWatchGame(Model model){
		_Model = model;
		_TextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		_TextPaint.setColor(Color.RED);
		_TextPaint.setTextSize(160);
		_SimpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
		
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Render(Canvas c) {
		String time = _SimpleDateFormat.format(new java.util.Date(_Model.getElapsedTime()));
		c.drawColor(Color.BLACK);
		c.drawText(time, 50, 250, _TextPaint);
		
	}

}
