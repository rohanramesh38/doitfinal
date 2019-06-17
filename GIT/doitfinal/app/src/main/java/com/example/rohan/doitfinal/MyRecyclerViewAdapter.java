package com.example.rohan.doitfinal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>  {

    private List<String> mDataExam,mDataClient;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList examheading,clientheading;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<String> data, List<String> data1) {
        this.mInflater = LayoutInflater.from(context);
        this.mDataExam = data;
        this.mDataClient = data1;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String Exam = mDataExam.get(position);
        String Test = mDataClient.get(position);
        holder.myTextViewExam.setText(Exam);
        holder.myTextViewClients.setText(Test);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mDataClient.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextViewExam,myTextViewClients;

        ViewHolder(View itemView) {
            super(itemView);
            myTextViewExam = itemView.findViewById(R.id.heading);
            myTextViewClients = itemView.findViewById(R.id.test);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mDataExam.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void UpdateItemsList(List<String> listExam,List<String> listClient)
    {
        mDataExam=new ArrayList();
        mDataExam.addAll(listExam);
        mDataClient=new ArrayList();
        mDataClient.addAll(listClient);
        notifyDataSetChanged();

    }

}