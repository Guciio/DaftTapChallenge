package com.example.dafttapchallenge.Activities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.dafttapchallenge.Data.Score;
import com.example.dafttapchallenge.R;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	private static final long TIME_TO_PLAY_GAME_IN_MILLIS = 5000;

	public int numberOfCilcks;

	private TextView timerView,numberOfClicksView;
	private Button clickButton;

	public static Boolean canClick;

	ContentValues values = new ContentValues();
	Score scoreDataSaver;

	SimpleDateFormat simpleDateFormat;
	String format;

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
		final Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.bounce);
		scoreDataSaver = new Score(this);
		simpleDateFormat = new SimpleDateFormat("dd/MM hh-mm-ss");

		timer();

		clickButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(canClick)
				{
					numberOfCilcks++;
					numberOfClicksView.setText("Clicks: " + numberOfCilcks);
					clickButton.startAnimation(myAnim);
					Log.i("Number of cliks",String.valueOf(numberOfCilcks));
				}
			}
		});
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		setContentView(R.layout.timer_screen);
		Intent backToApp = new Intent(this,TimerActivity.class);
		startActivity(backToApp);
	}

	private void timer()
	{
		CountDownTimer mCountDownTimer = new CountDownTimer(timeToStart, 1000)
		{
			@Override
			public void onTick(long millisUntilFinished)
			{
				timeToStart = millisUntilFinished+1000;
				updateTimerView();
			}

			@Override
			public void onFinish()
			{
				timerView.setText("TIME OUT");
				clickButton.setEnabled(false);
				canClick = false;
				format = simpleDateFormat.format(new Date());

				values.put("Score", numberOfCilcks);
				values.put("TimeStamp", format);

				AddData(numberOfCilcks, format);
				Log.i("Data to save", numberOfCilcks + " " + format);
				Cursor data = scoreDataSaver.getData();
				String noTop5Score = "";

				while (data.moveToNext())
				{
					if (numberOfCilcks > Integer.valueOf(data.getString(0)))
					{
						noTop5Score = "\nYour score is in top 5";
					}
					else
					{
						noTop5Score = "\nYour score is not in top 5";
					}
				}
				new AlertDialog.Builder(MainActivity.this).setTitle("Score")
						.setMessage("Your score is " + numberOfCilcks + noTop5Score)
						.setCancelable(false)
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								Intent scoreScreen = new Intent(MainActivity.this, ScoreActivity.class);
								startActivity(scoreScreen, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
							}
						})
						.show();
			}
		}.start();
		canClick = true;
	}

	private void updateTimerView()
	{
		int seconds = (int) (timeToStart / 1000) % 60;
		timerView.setText(String.valueOf(seconds));
	}

	public void AddData(int score, String timeStamp)
	{
		boolean insertData = scoreDataSaver.addData(score,timeStamp);

		if (insertData) {
			Toast.makeText(getApplicationContext(),"Data Successfully Inserted!",Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
		}
	}
}
