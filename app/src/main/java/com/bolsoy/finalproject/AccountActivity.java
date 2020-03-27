package com.bolsoy.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// If the user is logged in and kills the app, when the user
// reopens the app they are still logged on.

// App should log off when the app is killed.

public class AccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView mNameLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();

        mNameLabel = findViewById(R.id.hello);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            mNameLabel.setText(String.format(getResources().getString(R.string.hello), currentUser.getEmail()));
        } else {
        }
    }

    public void signOut(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
