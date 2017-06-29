package com.erkprog.opentok;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Room extends AppCompatActivity implements View.OnClickListener  {
    String userId;
    private static final String TAG = "myLogs";
    private DatabaseReference sessionsRef;

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

        sessionsRef = FirebaseDatabase.getInstance().getReference("Sessions");
        sessionsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.hasChild(userId)){
                    String token = (String) dataSnapshot.child(userId).getValue();
                    Log.d(TAG, "============= TOKEN :" + token);
                }


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.createRoom){
            Log.d(TAG, "buttonCLicked");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Sessions");
            CallSession session = new CallSession(userId, "addressee");
            myRef.push().setValue(session);
        }
    }
}
