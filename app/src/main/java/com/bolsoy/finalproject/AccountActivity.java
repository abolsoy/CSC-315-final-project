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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

// TODO:
// Landscape orientation
// Back button on toolbar
// If user created the item, the "Contact Seller" button should
// say "Edit Item" instead
// Menu bar with options

// "Home" screen

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "AccountActivity";
    private static final String ITEM = "item";

    private FirebaseAuth mAuth;
    private TextView mNameLabel;

    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private ItemRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();

        mNameLabel = findViewById(R.id.hello);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Query query = mDb.collection(ITEM)
                .orderBy("createdTime", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Item> options = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();

        mAdapter = new ItemRecyclerAdapter(options, new ItemRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                // Try passing item object through intent
                Item item = mAdapter.getSnapshots().getSnapshot(position).toObject(Item.class);
                String email = item.getEmail();
                String title = item.getTitle();
                String id = mAdapter.getSnapshots().getSnapshot(position).getId();
                itemClick(id, email);

//                Item item = mAdapter.getSnapshots().getSnapshot(position).toObject(Item.class);
//                String id = mAdapter.getSnapshots().getSnapshot(position).getId();
//                mDb.collection(ITEM).document(id).delete();
//
//                Toast.makeText(getApplicationContext(),"Deleting " + item.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void itemClick(String id, String item_email) {
        Log.w(TAG, "itemClick() function");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String user_email = currentUser.getEmail();
        if (item_email.equals(user_email)) {
            Intent intent = new Intent(this, OwnItemActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ViewItemActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
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
