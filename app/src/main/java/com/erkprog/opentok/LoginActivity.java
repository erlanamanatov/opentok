package com.erkprog.opentok;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String GRAPH_PATH = "me/permissions";
    private static final String SUCCESS = "success";
    private final static String TAG = "myLogs";
    private CallbackManager mCallbackManager;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signout).setOnClickListener(this);
        findViewById(R.id.buttonGroups).setOnClickListener(this);
        findViewById(R.id.buttonDeAuth).setOnClickListener(this);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id._fb_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("Users");
                            User fbUser = new User(user.getUid(), user.getDisplayName(), user.getEmail());
                            myRef.child(user.getUid()).setValue(fbUser);

                            Log.d(TAG, user.getDisplayName());
                            Log.d(TAG, user.getEmail());
                            Log.d(TAG, user.getUid());
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]


    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
            // mStatusTextView.setText(getString(R.string.facebook_status_fmt, user.getDisplayName()));
            // mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            Log.d(TAG, getString(R.string.facebook_status_fmt, user.getDisplayName()));
            Log.d(TAG, getString(R.string.firebase_status_fmt, user.getUid()));


            findViewById(R.id._fb_login).setVisibility(View.GONE);
            findViewById(R.id.signout).setVisibility(View.VISIBLE);
        } else {
            //mStatusTextView.setText(R.string.signed_out);
            //mDetailTextView.setText(null);

            Log.d(TAG, getString(R.string.signed_out));

            findViewById(R.id._fb_login).setVisibility(View.VISIBLE);
            findViewById(R.id.signout).setVisibility(View.GONE);
        }
    }

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUI(null);
    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signout) {
            signOut();
        }
        if (i == R.id.buttonGroups){
            if (mAuth.getCurrentUser() != null){
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
                startActivity(new Intent(LoginActivity.this, Room.class));
            } else {
                Toast.makeText(this, "user == null", Toast.LENGTH_SHORT).show();
            }
        }
        if (i == R.id.buttonDeAuth){
            if (!isLoggedIn()) {
                Toast.makeText(
                        LoginActivity.this,
                        //R.string.app_not_logged_in,
                        "app+not_logged_in",
                        Toast.LENGTH_LONG).show();
                return;
            }
            GraphRequest.Callback callback = new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    try {
                        if(response.getError() != null) {
                            Toast.makeText(
                                    LoginActivity.this,
//                                        getResources().getString(
//                                                R.string.failed_to_deauth,
//                                                response.toString()),
                                    "failed to deauth",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                        else if (response.getJSONObject().getBoolean(SUCCESS)) {
                            LoginManager.getInstance().logOut();
                            // updateUI();?
                        }
                    } catch (JSONException ex) { /* no op */ }
                }
            };
            GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(),
                    GRAPH_PATH, new Bundle(), HttpMethod.DELETE, callback);
            request.executeAsync();
        }
    }

    private boolean isLoggedIn() {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }
}
