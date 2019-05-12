package com.example.dafttapchallenge.Adapters;

import java.util.List;

import com.example.dafttapchallenge.R;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>
{
	private List<String> scoreData;
	private LayoutInflater mInflater;

	public MyRecyclerViewAdapter(Context context, List<String> data) {
		this.mInflater = LayoutInflater.from(context);
		this.scoreData = data;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
	{
		View view = mInflater.inflate(R.layout.recyclerview_row, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
	{
		String score = scoreData.get(i);
		viewHolder.myTextView.setText(score);
	}

	@Override
	public int getItemCount()
	{
		return scoreData.size();
	}

	static class ViewHolder extends RecyclerView.ViewHolder
	{
		TextView myTextView;

		ViewHolder(View itemView) {
			super(itemView);
			myTextView = itemView.findViewById(R.id.singleScoreView);
		}
	}
}

