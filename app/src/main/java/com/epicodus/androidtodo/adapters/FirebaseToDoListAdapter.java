package com.epicodus.androidtodo.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.epicodus.androidtodo.AndroidToDoApplication;
import com.epicodus.androidtodo.MainActivity;
import com.epicodus.androidtodo.R;
import com.epicodus.androidtodo.model.Task;
import com.epicodus.androidtodo.util.FirebaseRecyclerAdapter;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.snapshot.IndexedNode;

import java.util.HashMap;
import java.util.Map;

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
    public void onBindViewHolder(final ToDoListViewHolder holder, int position) {
        holder.bindTask(getItem(position));
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //can't use position argument here since position will change as items get deleted
                //use holder.getAdapterPosition() instead
                Task taskClickedOn = getItem(holder.getAdapterPosition());

                Firebase firebaseRef = AndroidToDoApplication.getAppInstance().getFirebaseRef();
                Firebase userRef = firebaseRef.child(firebaseRef.getAuth().getUid().toString());
                addDeleteListener(taskClickedOn, userRef);
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

    private void addDeleteListener(final Task taskClickedOn, final Firebase userRef) {
        userRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Task thisTask = dataSnapshot.getValue(Task.class);
                if (thisTask.equals(taskClickedOn)) {
                    dataSnapshot.getRef().removeValue();
                    notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
