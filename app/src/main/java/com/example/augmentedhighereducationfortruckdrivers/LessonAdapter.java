package com.example.augmentedhighereducationfortruckdrivers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyViewHolder>  {

    private List<String> mDataset;
    private static ClickListener clickListener;





    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;

        public MyViewHolder(TextView v) {
            super(v);
            v.setOnClickListener(this);
            textView = v;
        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }



    public interface ClickListener {
        void onItemClick(int position, View v);
    }


    public LessonAdapter(List<String> myDataset) {

        mDataset = myDataset;

    }

    public void setOnItemClickListener(ClickListener clickListener) {
        LessonAdapter.clickListener = clickListener;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public LessonAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lesson_row, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;


    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}