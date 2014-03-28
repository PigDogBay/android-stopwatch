package com.pigdogbay.stopwatch;

import com.pigdogbay.androidutils.games.AssetsReader;
import com.pigdogbay.androidutils.games.GameView;
import com.pigdogbay.androidutils.games.MultiTouchHandler;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity{

	GameView _GameView;
	StopWatchGame _StopWatchGame;
	Button _StartStopBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Model model = new Model();
		_StopWatchGame = new StopWatchGame(model);
		_GameView = new GameView(this, _StopWatchGame);
		setContentView(_GameView);
		loadResources();
        float scaleX = (float) StopWatchGame.BUFFER_WIDTH
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) StopWatchGame.BUFFER_HEIGHT
                / getWindowManager().getDefaultDisplay().getHeight();
        MultiTouchHandler touch = new MultiTouchHandler(_GameView, scaleX,scaleY);
        _StopWatchGame.setTouchHandler(touch);
        _StopWatchGame.initialize();

		
	}
	private void loadResources()
	{
		//Don't close the asset manager, as we will need if onCreate is called again
		AssetsReader assets = new AssetsReader(this);
		Assets.DigitsSheet =  assets.loadBitmap("digits_red.png", Config.RGB_565);
		Assets.ResetBtn =  assets.loadBitmap("reset.png", Config.RGB_565);
		Assets.StartBtn =  assets.loadBitmap("start.png", Config.RGB_565);
		Assets.StopBtn =  assets.loadBitmap("stop.png", Config.RGB_565);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v("MPDB", "onResume");
		_GameView.resume();
	}
	@Override
	protected void onPause() {
		super.onPause();
		Log.v("MPDB", "onPause");
		_GameView.pause();
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		Log.v("MPDB", "onWindowFocusChange");
		
	}

}
