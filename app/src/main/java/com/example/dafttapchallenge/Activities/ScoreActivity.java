package com.example.dafttapchallenge.Activities;

import java.util.ArrayList;

import com.example.dafttapchallenge.Adapters.MyRecyclerViewAdapter;
import com.example.dafttapchallenge.Data.Score;
import com.example.dafttapchallenge.R;
import com.google.gson.Gson;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class ScoreActivity extends AppCompatActivity
{
	private RecyclerView scoreTable;
	private MyRecyclerViewAdapter recAdapter;
	private ArrayList<String> score = new ArrayList<>();
	private Button startNew;
	private Score scoreDataToGet;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_screen);

		scoreTable = findViewById(R.id.scoreTableView);
		startNew = findViewById(R.id.playButton);
		scoreDataToGet = new Score(this);

		Cursor data = scoreDataToGet.getData();
		int i = 0;
		while(data.moveToNext()){
			score.add(i,"Score: "+data.getString(0) +"\n Time of getting:"+ data.getString(1));
			i++;
		}

		scoreTable.setLayoutManager(new LinearLayoutManager(this));
		recAdapter = new MyRecyclerViewAdapter(this,score);
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
}
