package com.example.dafttapchallenge.Activities;

import static java.security.AccessController.getContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.example.dafttapchallenge.Data.Score;
import com.example.dafttapchallenge.R;
import com.google.gson.Gson;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	private static final long TIME_TO_PLAY_GAME_IN_MILLIS = 5000;

	public int numberOfCilcks;

	private TextView timerView,numberOfClicksView;
	private Button clickButton;
	private FloatingActionButton goToScoreButton;

	public static Boolean canClick;
	private CountDownTimer mCountDownTimer;

	ContentValues values = new ContentValues();
	Score scoreDataSaver;

	SimpleDateFormat simpleDateFormat;
	String format,jsonScore;

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
					Log.i("Number of cliks",String.valueOf(numberOfCilcks));
				}
			}
		});
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
				format = simpleDateFormat.format(new Date());

				values.put("Score", numberOfCilcks);
				values.put("TimeStamp", format);

				AddData(numberOfCilcks,format);
				Log.i("Data to save",numberOfCilcks +" "+ format);

				new AlertDialog.Builder(MainActivity.this)
						.setTitle("Score")
						.setMessage("Your score is " + numberOfCilcks)
						.setCancelable(false)
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								Intent scoreScreen = new Intent(MainActivity.this,ScoreActivity.class);
								startActivity(scoreScreen);
							}
						}).show();
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
