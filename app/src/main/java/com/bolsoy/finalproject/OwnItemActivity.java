package com.bolsoy.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class OwnItemActivity extends AppCompatActivity {

    private static final String TAG = "OwnItemActivity";
    private static final String ITEM = "item";

    private FirebaseAuth mAuth;
    //    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private FirebaseFirestore mFirestore;
    private final StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();


    private ConstraintLayout mCreateItem;
    private TextView mTitleLabel;
    private TextView mTitleField;
    private TextView mPriceLabel;
    private TextView mPriceField;
    private TextView mDescriptionLabel;
    private TextView mDescriptionField;
    private TextView mEmailField;
    private DocumentReference mItemRef;
    private String itemId;
    private ImageView mItemImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_item);

        mTitleLabel = findViewById(R.id.title_label);
        mTitleField = findViewById(R.id.title);
        mPriceLabel = findViewById(R.id.price_label);
        mPriceField = findViewById(R.id.price);
        mDescriptionLabel = findViewById(R.id.description_label);
        mDescriptionField = findViewById(R.id.description);
        mEmailField = findViewById(R.id.email);
        mItemImageView = findViewById(R.id.image);


//        String email = getIntent().getExtras().getString("email");
        String id = getIntent().getExtras().getString("id");
        itemId = id;
        mFirestore = FirebaseFirestore.getInstance();
        mItemRef = mFirestore.collection("item").document(id);

//        DocumentReference docRef = mFirestore.collection("item").document("APbuOTEPTuEvXr56plbo");
        mItemRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Item i = documentSnapshot.toObject(Item.class);
                mTitleField.setText(i.getTitle());
                mPriceField.setText(i.getPrice());
                String imagePath = i.getImageFile();
                mDescriptionField.setText(i.getDescription());
                mEmailField.setText(i.getEmail());
                if (imagePath != null) {
                    mItemImageView.setVisibility(View.VISIBLE);
                    StorageReference image = mStorageRef.child(imagePath);

                    // Glide is a 3rd party library that simplifies image downloading, caching,
                    // and injection into your UI. This tells Glide to load the image from Storage and
                    // put it in the ImageView when complete.
                    GlideApp.with(OwnItemActivity.this)
                            .load(image)
                            .into(mItemImageView);
                } else if (imagePath == null) {

                }
            }
        });
    }

    public void editItem(View view) {
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra("id", itemId);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        GlideApp.with(this).pauseAllRequests();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        GlideApp.with(this).resumeRequests();

        if (savedInstanceState != null) {
//            mCurrentImage = savedInstanceState.getInt(CURRENT_PLANT);
        }
    }

    public void deleteItem(View view) {
        Toast.makeText(this, "Item deleted.", Toast.LENGTH_SHORT).show();
        mItemRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}
