package com.bolsoy.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

// If the user is logged in and kills the app, when the user
// reopens the app they are still logged on.

// App should log off when the app is killed.

// "Home" screen

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "AccountActivity";
    private static final String ITEM = "item";

    private FirebaseAuth mAuth;
    private TextView mNameLabel;

//    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
//    private PersonRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();

        mNameLabel = findViewById(R.id.hello);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//
//        Query query = mDb.collection(PEOPLE)
//                .orderBy("createdTime", Query.Direction.ASCENDING);
//        FirestoreRecyclerOptions<Person> options = new FirestoreRecyclerOptions.Builder<Person>()
//                .setQuery(query, Person.class)
//                .build();
//
//        mAdapter = new PersonRecyclerAdapter(options, new PersonRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Person person = mAdapter.getSnapshots().getSnapshot(position).toObject(Person.class);
//                String id = mAdapter.getSnapshots().getSnapshot(position).getId();
//                mDb.collection(PEOPLE).document(id).delete();
//
//                Toast.makeText(getApplicationContext(),"Deleting " + person.getUserId(),Toast.LENGTH_SHORT).show();
//            }
//        });
//        recyclerView.setAdapter(mAdapter);
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

    public void createListing(View view) {
        Log.w(TAG, "createListing() called");
        Intent intent = new Intent(this, CreateItemActivity.class);
        startActivity(intent);
    }

    public void signOut(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
