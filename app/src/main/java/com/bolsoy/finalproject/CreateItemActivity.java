package com.bolsoy.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class CreateItemActivity extends AppCompatActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        mTitleLabel = findViewById(R.id.title_label);
        mTitleField = findViewById(R.id.title);
        mPriceLabel = findViewById(R.id.price_label);
        mPriceField = findViewById(R.id.price);
        mDescriptionLabel = findViewById(R.id.description_label);
        mDescriptionField = findViewById(R.id.description);

    }

    public void postItem(View view) {
        if (!validateForm()) {
            return;
        }
        String title = mTitleField.getText().toString();
        String price = mPriceField.getText().toString();
        String description = mDescriptionField.getText().toString();
        Date dateCreated = new Date();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String email = currentUser.getEmail();

        Item newItem = new Item(title, description, price, dateCreated, email);

        Toast.makeText(this, "Listing " + title + " for $" + price, Toast.LENGTH_SHORT).show();
        mDb.collection(ITEM)
                .add(newItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Item added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding item", e);
                    }
                });

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
