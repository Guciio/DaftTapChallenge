package com.example.dafttapchallenge;

import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
	private static final long TIME_TO_PLAY_GAME_IN_MILLIS = 5000;

	public int numberOfCilcks;

	private TextView timerView,numberOfClicksView;
	private Button clickButton;
	private FloatingActionButton goToScoreButton;

	public static Boolean canClick;
	private CountDownTimer mCountDownTimer;

	private long timeToStart = TIME_TO_PLAY_GAME_IN_MILLIS;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		numberOfClicksView = findViewById(R.id.numberOfClicks);
		timerView = findViewById(R.id.timer);
		clickButton = findViewById(R.id.clickButton);
		clickButton.setEnabled(true);

		timer();

		clickButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(canClick)
				{
					numberOfCilcks++;
					numberOfClicksView.setText(String.valueOf(numberOfCilcks));
					Log.i("Number of cliks",String.valueOf(numberOfCilcks));
				}
			}
		});
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
				timerView.setText("TIME OUT");
				clickButton.setEnabled(false);
				canClick=false;
			}
		}.start();
		canClick = true;
	}

	private void updateTimerView()
	{
		int seconds = (int) (timeToStart / 1000) % 60;
		timerView.setText(String.valueOf(seconds));
	}

}
