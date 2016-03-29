package com.epicodus.androidtodo;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Guest on 3/28/16.
 */
public class AndroidToDoApplication extends Application {
    private static AndroidToDoApplication app;
    private Firebase mFirebaseRef;

    public static AndroidToDoApplication getAppInstance() {
        return app;
    }

    public Firebase getFirebaseRef() {
        return mFirebaseRef;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase(this.getString(R.string.firebase_url));
    }
}