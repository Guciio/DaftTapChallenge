package com.example.dafttapchallenge.Activities;

import java.util.ArrayList;

import com.example.dafttapchallenge.Adapters.MyRecyclerViewAdapter;
import com.example.dafttapchallenge.Data.Score;
import com.example.dafttapchallenge.R;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ScoreActivity extends AppCompatActivity
{
	private ArrayList<String> score = new ArrayList<>();
	private ArrayList<Integer> scoreToSort = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_screen);

		RecyclerView scoreTable = findViewById(R.id.scoreTableView);
		Button startNew = findViewById(R.id.playButton);
		Score scoreDataToGet = new Score(this);

		Cursor data = scoreDataToGet.getData();

		int i = 0;
		while(data.moveToNext()){
			score.add(i,"Score: "+ data.getString(0) +"\n Time of getting:"+ data.getString(1));
			scoreToSort.add(Integer.valueOf(data.getString(0)));
			i++;
		}

		//Collections.sort(scoreToSort);

		Log.i("TEST", String.valueOf(score));
		scoreTable.setLayoutManager(new LinearLayoutManager(this));
		MyRecyclerViewAdapter recAdapter = new MyRecyclerViewAdapter(this, score);
		scoreTable.setAdapter(recAdapter);

		startNew.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent newGame = new Intent(ScoreActivity.this,TimerActivity.class);
				startActivity(newGame);
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
}
