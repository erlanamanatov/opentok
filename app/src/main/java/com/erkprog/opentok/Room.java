package com.erkprog.opentok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Room extends AppCompatActivity implements View.OnClickListener  {
    String userId;
    private static final String TAG = "myLogs";
    private DatabaseReference sessionsRef;
    private DatabaseReference usersRef;
    private ListView usersListView;
    private ArrayList<User> usersList;
    private UsersAdapter adapter;


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

        usersListView = (ListView)findViewById(R.id.usersListView);
        usersList = new ArrayList<>();
        adapter = new UsersAdapter(this, usersList);
        usersListView.setAdapter(adapter);

        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                usersList.add(dataSnapshot.getValue(User.class));
                adapter.notifyDataSetChanged();
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        sessionsRef = FirebaseDatabase.getInstance().getReference("Sessions");
        sessionsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.hasChild(userId)){
                    String token = (String) dataSnapshot.child(userId).getValue();
                    String sessionId = (String) dataSnapshot.child("sessionId").getValue();
                    Log.d(TAG, "============= TOKEN :" + token + "\n=============SESSION : " + sessionId);
                    Intent intent = new Intent(Room.this, MainActivity.class);
                    intent.putExtra("sessionId", sessionId);
                    intent.putExtra("token", token);
                    startActivity(intent);
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

    public void onCallButtonClicked(String addresseeId){
        Log.d(TAG, "buttonCLicked");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Sessions");
        CallSession session = new CallSession(userId, addresseeId);
        myRef.push().setValue(session);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.createRoom){
            onCallButtonClicked("addressee");
        }
    }
}
