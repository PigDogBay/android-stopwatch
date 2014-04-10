package com.pigdogbay.stopwatch;

import com.pigdogbay.androidutils.games.FrameBuffer;
import com.pigdogbay.androidutils.games.GameView.IGame;
import com.pigdogbay.stopwatch.BitmapButton.OnClickListener;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class StopWatchGame implements IGame, IView{

	public static final int BUFFER_HEIGHT = 800;
	public static final int BUFFER_WIDTH = 1280;
	final static int DIGIT_WIDTH = 100;
	final static int DIGIT_HEIGHT = 320;
	final static int RESET_X = 40;
	final static int RESET_Y = 440;
	final static int START_X = 640;
	final static int START_Y = 440;
	
	Presenter _Presenter;
	Model _Model;
	FrameBuffer _Buffer;
	TimeDigits _TimeDigits = new TimeDigits();
	ObjectTouchHandler _TouchHandler;
	private Paint _TextPaint;
	private FramesPerSecond _FPS;
	private BitmapButton _ResetBtn, _StartBtn;

	public Model getModel()
	{
		return _Model;
	}
	public StopWatchGame(Model model){
		_Model = model;
		_Presenter = new Presenter(model, this);
        _Buffer = new FrameBuffer(BUFFER_WIDTH, BUFFER_HEIGHT);
        _FPS = new FramesPerSecond();
		_TextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		_TextPaint.setColor(Color.WHITE);
		_TextPaint.setTextSize(36);
		_ResetBtn= new BitmapButton();
		_StartBtn= new BitmapButton();
		_StartBtn.setBitmaps(Assets.StartBtn,Assets.StartPressedBtn,START_X,START_Y);
		_ResetBtn.setBitmaps(Assets.ResetBtn,Assets.ResetPressedBtn,RESET_X,RESET_Y);
		_ResetBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Object sender) {
				_Presenter.reset();
			}
		});
		_StartBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(Object sender) {
				_Presenter.toggleTimer();
			}
		});
	}
	public void initialize()
	{
		_Presenter.updateView();
	}
	
	public void setTouchHandler(ObjectTouchHandler touch){
		_TouchHandler = touch;
		_TouchHandler.add(_ResetBtn);
		_TouchHandler.add(_StartBtn);
	}

	@Override
	public void Update() {
		_TimeDigits.updateTime(_Model.getElapsedTime());
	}
	@Override
	public void Render(Canvas c) {
		int[] digits = _TimeDigits.Digits;
		for (int i=0;i<12;i++){
			_Buffer.draw(Assets.DigitsSheet,40+DIGIT_WIDTH*i,40,digits[i]*DIGIT_WIDTH,0,DIGIT_WIDTH, DIGIT_HEIGHT);
		}
		_StartBtn.draw(_Buffer);
		_ResetBtn.draw(_Buffer);
		_Buffer.scaleToFit(c);
		_FPS.CalculateFPS();
		c.drawText(_FPS.getFPS(), 0, 36, _TextPaint);
	}
	
	public void close()
	{
		_Buffer.close();
		_TouchHandler.remove(_ResetBtn);
		_TouchHandler.remove(_StartBtn);
		_ResetBtn.setOnClickListener(null);
		_StartBtn.setOnClickListener(null);
	}

	@Override
	public void showPaused() {
		_StartBtn.setBitmaps(Assets.StartBtn,Assets.StartPressedBtn,START_X,START_Y);
	}

	@Override
	public void showRunning() {
		_StartBtn.setBitmaps(Assets.StopBtn,Assets.StopPressedBtn,START_X,START_Y);
	}

}
