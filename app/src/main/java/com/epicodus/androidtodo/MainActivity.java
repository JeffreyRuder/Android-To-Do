package com.epicodus.androidtodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epicodus.androidtodo.model.Task;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Firebase mFirebaseRef;
    private String mCurrentUserId;

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
        mFirebaseRef.child(mCurrentUserId.toString()).push().setValue(task);
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
            } else {
                Toast.makeText(this, "Please enter a title and description", Toast.LENGTH_LONG).show();
            }
        }
    }
}
