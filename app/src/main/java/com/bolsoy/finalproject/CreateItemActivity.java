package com.bolsoy.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class CreateItemActivity extends AppCompatActivity {

    private static final String TAG = "CreateItemActivity";
    private FirebaseAuth mAuth;

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
        String title = mTitleField.toString();
        String price = mPriceField.toString();
        String description = mDescriptionField.toString();
        Date dateCreated = new Date();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();

        Item newItem = new Item(title, description, price, dateCreated);
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
