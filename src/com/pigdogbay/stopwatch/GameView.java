package com.pigdogbay.stopwatch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	Paint _TextPaint;
	
	private GameThread _GameThread;
	private SurfaceHolder _Holder;
	private IGame _Game;
	public GameView(Context context, IGame game)
	{
		super(context);
		_Game = game;
		_GameThread = new GameThread(this);
		_Holder = getHolder();
		_Holder.addCallback(this);
	}
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
	{
		// The code to resize the screen ratio when it flips from landscape to portrait and vice versa
	}

	public void surfaceCreated(SurfaceHolder holder)
	{
		_GameThread.setRunning(true);
		_GameThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder)
	{
        boolean retry = true;
        _GameThread.setRunning(false);
        while (retry) 
        {
            try 
            {
                _GameThread.join();
                retry = false;

            } catch (InterruptedException e) {}
        }
	}
	
    /**
     * Standard window-focus override. Notice focus lost so we can pause on
     * focus lost. e.g. user switches to take a call.
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            if (_GameThread != null)
            	_GameThread.pause();

        }
    }
	public void Update()
	{
		_Game.Update();
	}
	public void Render()
	{
        Canvas c = null;
        try
        {
            c = _Holder.lockCanvas();
            synchronized (_Holder)
            { 
                _Game.Render(c);
            }
        }
        finally
        {
            if(c != null) 
            {
                _Holder.unlockCanvasAndPost(c); 
            }
        }
		
	}
}
