package com.pigdogbay.stopwatch;


/**
 * Converts the time in milliseconds to hh:mm:ss:SSS
 *
 */
public class TimeDigits {
	public int Digits[] = new int[12];
	public static final int SPACER = 10;
	
	public void updateTime(long time)
	{
		int t = (int) time;
		//Milliseconds
		Digits[8] = SPACER;
		Digits[9] = (t%1000)/100;
		Digits[10] = (t%100)/10;
		Digits[11] = t%10;
		//Seconds
		t=t/1000;
		Digits[5] = SPACER;
		Digits[6] = (t%60)/10;
		Digits[7] = t%10;
		//Minutes
		t=t/60;
		Digits[2] = SPACER;
		Digits[3] = (t%60)/10;
		Digits[4] = t%10;
		//Hours
		t=t/60;
		Digits[0] = (t%60)/10;
		Digits[1] = t%10;
	}
}
