package com.epicodus.androidtodo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.androidtodo.AndroidToDoApplication;
import com.epicodus.androidtodo.R;
import com.epicodus.androidtodo.model.Task;
import com.epicodus.androidtodo.util.FirebaseRecyclerAdapter;
import com.firebase.client.Firebase;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 3/29/16.
 */
public class ToDoListViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.taskTitleTextView) TextView mTaskTitleTextView;
    @Bind(R.id.taskDescriptionTextView) TextView mTaskDescriptionTextView;
    @Bind(R.id.deleteButton) Button mDeleteButton;
    private ArrayList<Task> mTasks = new ArrayList<>();
    private long mTaskId;
    private String key;
    private Context mContext;

    public ToDoListViewHolder(View itemView, ArrayList<Task> tasks) {
        super(itemView);
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
        mTasks = tasks;
    }


    public void bindTask(Task task) {
        mTaskTitleTextView.setText(task.getTitle());
        mTaskDescriptionTextView.setText(task.getDescription());
    }

}
