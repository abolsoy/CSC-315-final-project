package com.bolsoy.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class EditItemActivity extends AppCompatActivity {


    private static final String TAG = "CreateItemActivity";
    private static final String ITEM = "item";

    private FirebaseAuth mAuth;
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private ConstraintLayout mCreateItem;
    private TextView mTitleLabel;
    private EditText mTitleField;
    private TextView mPriceLabel;
    private EditText mPriceField;
    private TextView mDescriptionLabel;
    private EditText mDescriptionField;
    private DocumentReference mItemRef;
    private String itemId;

    private FirebaseFirestore mFirestore;

    // Should updating the item bring it to the top??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        mTitleLabel = findViewById(R.id.title_label);
        mTitleField = findViewById(R.id.title);
        mPriceLabel = findViewById(R.id.price_label);
        mPriceField = findViewById(R.id.price);
        mDescriptionLabel = findViewById(R.id.description_label);
        mDescriptionField = findViewById(R.id.description);

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
                mDescriptionField.setText(i.getDescription());
            }
        });
    }

    public void updateItem(View view) {
        if (!validateForm()) {
            return;
        }
        String updated_title = mTitleField.getText().toString();
        String updated_price = mPriceField.getText().toString();
        String updated_description = mDescriptionField.getText().toString();
        Date dateUpdated = new Date();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail();

        Toast.makeText(this, "Item updated.", Toast.LENGTH_SHORT).show();
        mItemRef
                .update("price", updated_price)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot price successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
        mItemRef
                .update("title", updated_title)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot title successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
        mItemRef
                .update("createdTime", dateUpdated)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot createdTime successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
        mItemRef
                .update("description", updated_description)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot description successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
        goHome();
    }

    public void goHome() {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    private boolean validateForm() {
        boolean valid = true;

        String title = mTitleField.getText().toString();
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError("Required.");
            valid = false;
        } else {
            mTitleField.setError(null);
        }

        // Allow decimal points - probably should add more
        // error checking here
        String price = mPriceField.getText().toString();
        price = price.replace(".", "");
        if (TextUtils.isEmpty(price)) {
            mPriceField.setError("Required.");
            valid = false;
        } else if (!TextUtils.isDigitsOnly(price)) {
            mPriceField.setError("Invalid Price.");
            valid = false;
        } else {
            mPriceField.setError(null);
        }

        String description = mDescriptionField.getText().toString();
        if (TextUtils.isEmpty(description)) {
            mDescriptionField.setError("Required.");
            valid = false;
        } else {
            mDescriptionField.setError(null);
        }

        return valid;
    }
}
