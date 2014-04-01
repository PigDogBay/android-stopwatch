package com.pigdogbay.stopwatch;


public class Presenter {
	Model _Model;
	IView _View;
	
	public Presenter(Model model, IView view)
	{
		_Model = model;
		_View = view;
	}
	
	public void toggleTimer(){
		_Model.toggle();
		updateView();
	}
	
	public void reset(){
		if (!_Model.isRunning())
		{
			_Model.reset();
		}
	}
	
	public void updateView(){
		if (_Model.isRunning())
		{
			_View.showRunning();
		}
		else
		{
			_View.showPaused();
		}
		
	}
	
}
