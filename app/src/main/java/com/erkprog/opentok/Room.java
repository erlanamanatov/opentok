package com.erkprog.opentok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Room extends AppCompatActivity implements View.OnClickListener  {
    String userId;
    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        init();
    }

    private void init(){
        Log.d(TAG, "Room activity created");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        findViewById(R.id.createRoom).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.createRoom){
            Log.d(TAG, "buttonCLicked");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Sessions");
            myRef.push().child("creator").setValue(userId);
        }
    }
}
