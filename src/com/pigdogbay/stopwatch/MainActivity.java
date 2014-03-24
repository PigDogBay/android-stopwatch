package com.pigdogbay.stopwatch;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
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
		_Presenter = new Presenter(model, this);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
