package com.epicodus.androidtodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.androidtodo.adapters.FirebaseToDoListAdapter;
import com.epicodus.androidtodo.model.Task;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Firebase mFirebaseRef;
    private String mCurrentUserId;
    private Query mQuery;
    private FirebaseToDoListAdapter mAdapter;

    @Bind(R.id.newTaskButton) Button mNewTaskButton;
    @Bind(R.id.newTaskTitleEditText) EditText mNewTaskTitleEditText;
    @Bind(R.id.newTaskDescriptionEditText) EditText mNewTaskDescriptionEditText;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFirebaseRef = AndroidToDoApplication.getAppInstance().getFirebaseRef();
        checkForAuthenticatedUser();

        mNewTaskButton.setOnClickListener(this);

        setupFirebaseQuery();
        setupRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void checkForAuthenticatedUser() {
        AuthData authData = mFirebaseRef.getAuth();
        if (authData == null) {
            goToLoginActivity();
        } else {
            mCurrentUserId = mFirebaseRef.getAuth().getUid();
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void logout() {
        mFirebaseRef.unauth();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void createTask(String title, String description) {
        Task task = new Task(title, description);

        Firebase userRef = mFirebaseRef.child(mCurrentUserId.toString());
        Firebase taskRef = userRef.push();
        Log.d("NEW ID", taskRef.toString());
        task.setRef(taskRef.toString());
        taskRef.setValue(task);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                this.logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == mNewTaskButton) {
            String title = mNewTaskTitleEditText.getText().toString().trim();
            String description = mNewTaskDescriptionEditText.getText().toString().trim();
            if (!title.isEmpty() && !description.isEmpty()) {
                createTask(title, description);
                mNewTaskTitleEditText.setText("");
                mNewTaskDescriptionEditText.setText("");
            } else {
                Toast.makeText(this, "Please enter a title and description", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setupFirebaseQuery(){
        mQuery = mFirebaseRef.child(mFirebaseRef.getAuth().getUid());
    }

    private void setupRecyclerView() {
        mAdapter = new FirebaseToDoListAdapter(mQuery, Task.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

}
