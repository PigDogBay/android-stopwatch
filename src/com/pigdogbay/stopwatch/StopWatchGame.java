package com.pigdogbay.stopwatch;

import com.pigdogbay.androidutils.games.FrameBuffer;
import com.pigdogbay.androidutils.games.GameView.IGame;
import com.pigdogbay.stopwatch.BitmapButton.OnClickListener;
import com.pigdogbay.stopwatch.ObjectTouchHandler.ITouchable;
import com.pigdogbay.stopwatch.ObjectTouchHandler.TouchState;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class StopWatchGame implements IGame, IView, ITouchable {

	public static final int BUFFER_HEIGHT = 800;
	public static final int BUFFER_WIDTH = 1280;
	final static int DIGIT_WIDTH = 100;
	final static int DIGIT_HEIGHT = 320;
	final static int DIGIT_Y_OFFSET = 200;
	final static int SWIPE_RESET_DISTANCE = 160;
	final static int FONT_SIZE = 36;
	final static int INSTRUCTION_X = 600;
	final static int INSTRUCTION_Y = 650;
	private Paint _TextPaint;

	private String INSTRUCTION_START = "Touch to Start";
	private String INSTRUCTION_STOP = "Touch to Stop";
	private String INSTRUCTION_RESET = "Swipe to Reset";
	private String _Instruction = INSTRUCTION_START;

	Presenter _Presenter;
	Model _Model;
	FrameBuffer _Buffer;
	TimeDigits _TimeDigits = new TimeDigits();
	ObjectTouchHandler _TouchHandler;

	public Model getModel() {
		return _Model;
	}

	public StopWatchGame(Model model) {
		_Model = model;
		_Presenter = new Presenter(model, this);
		_Buffer = new FrameBuffer(BUFFER_WIDTH, BUFFER_HEIGHT);
		_TextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		_TextPaint.setColor(Color.RED);
		_TextPaint.setTextSize(36);
	}

	public void initialize() {
		_Presenter.updateView();
	}

	public void setTouchHandler(ObjectTouchHandler touch) {
		_TouchHandler = touch;
		_TouchHandler.add(this);
	}

	@Override
	public void Update() {
		_TimeDigits.updateTime(_Model.getElapsedTime());
	}

	@Override
	public void Render(Canvas c) {
		int[] digits = _TimeDigits.Digits;
		_Buffer.clear(0);
		for (int i = 0; i < 12; i++) {
			_Buffer.draw(Assets.DigitsSheet, 40 + DIGIT_WIDTH * i,
					DIGIT_Y_OFFSET, digits[i] * DIGIT_WIDTH, 0, DIGIT_WIDTH,
					DIGIT_HEIGHT);
		}
		Canvas buffCanvas = _Buffer.getCanvas();
		buffCanvas.drawText(_Instruction, INSTRUCTION_X, INSTRUCTION_Y,
				_TextPaint);
		_Buffer.scaleToFit(c);
	}

	public void close() {
		_Buffer.close();
		_TouchHandler.remove(this);
	}

	@Override
	public void showReset(){
		_Instruction = INSTRUCTION_RESET;
	}
	@Override
	public void showPaused() {
		_Instruction = INSTRUCTION_START;
	}
	@Override
	public void showRunning() {
		_Instruction = INSTRUCTION_STOP;
	}

	@Override
	public boolean contains(int x, int y) {
		return true;
	}

	int _X, _Y;

	@Override
	public void setTouchState(TouchState state) {
		if (TouchState.Down == state) {
			_X = _TouchHandler._X;
			_Y = _TouchHandler._Y;
		} else if (TouchState.UpInside == state) {
			int xdist = (_TouchHandler._X - _X) * (_TouchHandler._X - _X);
			if (xdist > SWIPE_RESET_DISTANCE * SWIPE_RESET_DISTANCE) {
				_Presenter.reset();
			} else {
				_Presenter.toggleTimer();
			}
		}

	}

}
