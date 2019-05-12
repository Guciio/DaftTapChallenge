package com.example.dafttapchallenge;

import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
	private static final long TIME_TO_START_GAME_IN_MILLIS = 3000;

	private TextView timerView;
	private Button clickButton;
	private FloatingActionButton goToScoreButton;

	private CountDownTimer mCountDownTimer;

	private long timeToStart = TIME_TO_START_GAME_IN_MILLIS;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		timerView = findViewById(R.id.timer);
		clickButton = findViewById(R.id.clickButton);

		timer();
	}

	private void game()
	{

	}

	private void timer()
	{
		mCountDownTimer = new CountDownTimer(timeToStart,1000)
		{
			@Override
			public void onTick(long millisUntilFinished)
			{
				timeToStart=millisUntilFinished;
				updateTimerView();
			}

			@Override
			public void onFinish()
			{
				timerView.setText("GO");
			}
		}.start();
	}

	private void updateTimerView()
	{
		int seconds = (int) (timeToStart / 1000) % 60;
		timerView.setText(String.valueOf(seconds));
	}

	private void clickCount()
	{

	}
}
