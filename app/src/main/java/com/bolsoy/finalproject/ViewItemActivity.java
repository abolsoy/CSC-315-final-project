package com.bolsoy.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewItemActivity extends AppCompatActivity {

    private static final String TAG = "CreateItemActivity";
    private static final String ITEM = "item";

    private FirebaseAuth mAuth;
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private ConstraintLayout mCreateItem;
    private TextView mTitleLabel;
    private TextView mTitleField;
    private TextView mPriceLabel;
    private TextView mPriceField;
    private TextView mDescriptionLabel;
    private TextView mDescriptionField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        mTitleLabel = findViewById(R.id.title_label);
        mTitleField = findViewById(R.id.title);
        mPriceLabel = findViewById(R.id.price_label);
        mPriceField = findViewById(R.id.price);
        mDescriptionLabel = findViewById(R.id.description_label);
        mDescriptionField = findViewById(R.id.description);

        // Get restaurant ID from extras
//        String restaurantId = getIntent().getExtras().getString(KEY_RESTAURANT_ID);
//        if (restaurantId == null) {
//            throw new IllegalArgumentException("Must pass extra " + KEY_RESTAURANT_ID);
//        }
//
//        // Initialize Firestore
//        mFirestore = FirebaseFirestore.getInstance();
//
//        // Get reference to the restaurant
//        mRestaurantRef = mFirestore.collection("restaurants").document(restaurantId);
//
//        mTitleField.setText();
    }


    public void buyItem(View view) {

    }
}
