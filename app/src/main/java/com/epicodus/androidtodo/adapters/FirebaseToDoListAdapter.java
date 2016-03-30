package com.epicodus.androidtodo.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epicodus.androidtodo.AndroidToDoApplication;
import com.epicodus.androidtodo.R;
import com.epicodus.androidtodo.model.Task;
import com.epicodus.androidtodo.util.FirebaseRecyclerAdapter;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

/**
 * Created by Guest on 3/29/16.
 */
public class FirebaseToDoListAdapter extends FirebaseRecyclerAdapter<ToDoListViewHolder, Task> {
    public FirebaseToDoListAdapter(Query query, Class<Task> itemClass) {
        super(query, itemClass);
    }

    @Override
    public ToDoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);
        return new ToDoListViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(ToDoListViewHolder holder, final int position) {
        holder.bindTask(getItem(position));
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = getItem(position);
                Firebase ref = new Firebase(task.getRef());
                ref.removeValue();
            }
        });
    }

    @Override
    protected void itemAdded(Task item, String key, int position) {

    }

    @Override
    protected void itemChanged(Task oldItem, Task newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(Task item, String key, int position) {
    }

    @Override
    protected void itemMoved(Task item, String key, int oldPosition, int newPosition) {

    }

}
