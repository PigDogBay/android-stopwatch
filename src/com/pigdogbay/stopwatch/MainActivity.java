package com.pigdogbay.stopwatch;

import com.pigdogbay.androidutils.games.AssetsReader;
import com.pigdogbay.androidutils.games.GameView;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity implements IView {

	GameView _GameView;
	StopWatchGame _StopWatchGame;
	Button _StartStopBtn;
	
	Presenter _Presenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_main);
		Model model = new Model();
		_StopWatchGame = new StopWatchGame(model);
		_GameView = new GameView(this, _StopWatchGame);
		((ViewGroup)findViewById(R.id.surfaceViewContainer)).addView(_GameView);
		_StartStopBtn = (Button) findViewById(R.id.btnStart);
		_StartStopBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_Presenter.toggleTimer();
			}
		});
		((Button) findViewById(R.id.btnReset)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_Presenter.reset();
			}
		});
		loadResources();
		_Presenter = new Presenter(model, this);
		
	}
	private void loadResources()
	{
		AssetsReader assets = new AssetsReader(this);
		_StopWatchGame.setDigitsBitmap(assets.loadBitmap("digits_red.png", Config.RGB_565));
		assets.close();
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

	@Override
	public void showPaused() {
		_StartStopBtn.setText("START");
		
	}

	@Override
	public void showRunning() {
		_StartStopBtn.setText("STOP");
		
	}

}
