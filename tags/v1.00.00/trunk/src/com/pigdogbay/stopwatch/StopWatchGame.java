package com.pigdogbay.stopwatch;

import java.util.List;

import com.pigdogbay.androidutils.games.FrameBuffer;
import com.pigdogbay.androidutils.games.GameView.IGame;
import com.pigdogbay.androidutils.games.TouchEvent;
import com.pigdogbay.androidutils.games.TouchHandler;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class StopWatchGame implements IGame, IView{

	public static final int BUFFER_HEIGHT = 800;
	public static final int BUFFER_WIDTH = 1280;
	final static int DIGIT_WIDTH = 100;
	final static int DIGIT_HEIGHT = 320;
	
	Presenter _Presenter;
	Model _Model;
	FrameBuffer _Buffer;
	TimeDigits _TimeDigits = new TimeDigits();
	TouchHandler _TouchHandler;
	private Bitmap _ToggleBmp;

	public StopWatchGame(Model model){
		_Model = model;
		_Presenter = new Presenter(model, this);
        _Buffer = new FrameBuffer(BUFFER_WIDTH, BUFFER_HEIGHT);
	}
	public void initialize()
	{
		_ToggleBmp = Assets.StartBtn;
	}
	
	public void setTouchHandler(TouchHandler touch){
		_TouchHandler = touch;
	}

	@Override
	public void Update() {
		_TimeDigits.updateTime(_Model.getElapsedTime());
		List<TouchEvent> touchEvents = _TouchHandler.getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                if(inBounds(event, 40, 440, Assets.ResetBtn.getWidth(), Assets.ResetBtn.getHeight())) {
                	_Presenter.reset();
                	return;
                }
                if(inBounds(event, 640, 440, Assets.StartBtn.getWidth(), Assets.StartBtn.getHeight())) {
                	_Presenter.toggleTimer();
                	return;
                }
            }
        }
		
	}
	@Override
	public void Render(Canvas c) {
		int[] digits = _TimeDigits.Digits;
		for (int i=0;i<12;i++){
			_Buffer.draw(Assets.DigitsSheet,40+DIGIT_WIDTH*i,40,digits[i]*DIGIT_WIDTH,0,DIGIT_WIDTH, DIGIT_HEIGHT);
		}
		//Draw buttons
		_Buffer.draw(Assets.ResetBtn,40,440);
		_Buffer.draw(_ToggleBmp,640,440);
		
		_Buffer.scaleToFit(c);
		
	}
    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 && 
           event.y > y && event.y < y + height - 1) 
            return true;
        else
            return false;
    }
	
	public void close()
	{
		_Buffer.close();
	}

	@Override
	public void showPaused() {
		_ToggleBmp = Assets.StartBtn;
	}

	@Override
	public void showRunning() {
		_ToggleBmp = Assets.StopBtn;
	}

}
