package com.example.dafttapchallenge.Activities;

import java.util.ArrayList;

import com.example.dafttapchallenge.Data.Score;
import com.example.dafttapchallenge.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity
{
	private static final long TIME_TO_START_GAME_IN_MILLIS = 3000;
	private long timeToStart = TIME_TO_START_GAME_IN_MILLIS;

	private TimerActivity dataToIntent;
	private TextView timerView;
	private CountDownTimer mCountDownTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_screen);

		timerView = findViewById(R.id.timerToStart);
		dataToIntent = this;
		timer();
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
				timerView.setText("PLAY");
				MainActivity.canClick=true;
				Intent gameIntent = new Intent(dataToIntent,MainActivity.class);
				startActivity(gameIntent);
			}
		}.start();
		MainActivity.canClick=false;
	}

	private void updateTimerView()
	{
		int seconds = (int) (timeToStart / 1000) % 60;
		timerView.setText(String.valueOf(seconds));
	}
}
