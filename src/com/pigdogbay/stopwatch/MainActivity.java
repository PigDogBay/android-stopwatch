package com.pigdogbay.stopwatch;

import com.pigdogbay.androidutils.games.AssetsReader;
import com.pigdogbay.androidutils.games.GameView;
import com.pigdogbay.androidutils.games.ObjectTouchHandler;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity{

	public static final String MODEL_PREFERENCES_KEY = "MODEL_PREFERENCES_KEY";
	GameView _GameView;
	StopWatchGame _StopWatchGame;
	Button _StartStopBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
		loadResources();
		Model model = new Model();
		_StopWatchGame = new StopWatchGame(model);
		_GameView = new GameView(this, _StopWatchGame);
		setContentView(_GameView);
        float scaleX = (float) StopWatchGame.BUFFER_WIDTH
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) StopWatchGame.BUFFER_HEIGHT
                / getWindowManager().getDefaultDisplay().getHeight();
        ObjectTouchHandler touch = new ObjectTouchHandler();
        touch.setXScale(scaleX);
        touch.setYScale(scaleY);
        _StopWatchGame.setTouchHandler(touch);
        _GameView.setOnTouchListener(touch);
		new com.pigdogbay.androidutils.apprate.AppRate(this)
		.setMinDaysUntilPrompt(7).setMinLaunchesUntilPrompt(5)
		.init();
	}
	private void loadResources()
	{
		//Don't close the asset manager, as we will need if onCreate is called again
		AssetsReader assets = new AssetsReader(this);
		Assets.DigitsSheet =  assets.loadBitmap("digits.png", Config.RGB_565);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v("MPDB", "onResume");
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);		
		try{
			String data = prefs.getString(MODEL_PREFERENCES_KEY, "");
			_StopWatchGame.getModel().load(data);
		}catch (Exception e){
			_StopWatchGame.getModel().setRunning(false);
			_StopWatchGame.getModel().reset();
		}
        _StopWatchGame.initialize();
		_GameView.resume();
	}
	@Override
	protected void onPause() {
		super.onPause();
		Log.v("MPDB", "onPause");
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);		
		String data  = _StopWatchGame.getModel().save();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(MODEL_PREFERENCES_KEY, data);
		editor.commit();
		_GameView.pause();
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		Log.v("MPDB", "onWindowFocusChange");
		
	}

}
