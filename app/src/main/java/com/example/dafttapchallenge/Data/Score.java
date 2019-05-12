package com.example.dafttapchallenge.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Score extends SQLiteOpenHelper
{

	private int score;
	private String timeStamp;

	private static final String TABLE_NAME = "DaftGameDataTable";
	private static final String SCORECOL = "Score";
	private static final String TIMESTAMPCOL= "TimeStamp";

	public Score(Context context)
	{
		super(context,TABLE_NAME,null,1);
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public String getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String createTable = "CREATE TABLE " + TABLE_NAME + "( "+SCORECOL+" INTEGER , " +
				TIMESTAMPCOL +" TEXT);";
		db.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
		onCreate(db);
	}

	public boolean addData(int score, String timeStamp)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(SCORECOL, score);
		contentValues.put(TIMESTAMPCOL, timeStamp);

		Log.d("DataBaseHelper", "addData: Adding " + score +"and" +timeStamp+ " to " + TABLE_NAME);

		long result = db.insert(TABLE_NAME, null, contentValues);

		if (result == -1) {
			return false;
		} else {
			return true;
		}
	}

	public Cursor getData(){
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM " + TABLE_NAME +" ORDER BY "+ SCORECOL+" DESC LIMIT 5";
		Cursor data = db.rawQuery(query, null);
		return data;
	}
}
