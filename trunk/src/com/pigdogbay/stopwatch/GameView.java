package com.pigdogbay.stopwatch;

import com.pigdogbay.stopwatch.GameThread.GameThreadClient;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements GameThreadClient {
	private GameThread _GameThread;
	private SurfaceHolder _Holder;
	private IGame _Game;

	public GameView(Context context, IGame game) {
		super(context);
		_Game = game;
		_Holder = getHolder();
	}

	public void pause() {
		Log.v("MPDB", "GameView pause");
		_GameThread.setRunning(false);
		while (true) {
			try {
				_GameThread.join();
				_GameThread = null;
				break;

			} catch (InterruptedException e) {
			}
		}

	}

	public void resume() {
		Log.v("MPDB", "GameView resume");
		_GameThread = new GameThread(this);
		_GameThread.setRunning(true);
		_GameThread.start();
	}

	public void Update() {
		_Game.Update();
	}

	public void Render() {
		if (!_Holder.getSurface().isValid()) {
			return;
		}
		Canvas c = null;
		try {
			c = _Holder.lockCanvas();
			synchronized (_Holder) {
				_Game.Render(c);
			}
		} finally {
			if (c != null) {
				_Holder.unlockCanvasAndPost(c);
			}
		}

	}
}
