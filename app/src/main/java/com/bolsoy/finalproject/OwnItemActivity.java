package com.bolsoy.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OwnItemActivity extends AppCompatActivity {

    private static final String TAG = "OwnItemActivity";
    private static final String ITEM = "item";

    private FirebaseAuth mAuth;
    //    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private FirebaseFirestore mFirestore;


    private ConstraintLayout mCreateItem;
    private TextView mTitleLabel;
    private TextView mTitleField;
    private TextView mPriceLabel;
    private TextView mPriceField;
    private TextView mDescriptionLabel;
    private TextView mDescriptionField;
    private TextView mEmailField;
    private DocumentReference mItemRef;

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

//        String email = getIntent().getExtras().getString("email");
        String id = getIntent().getExtras().getString("id");
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
                mEmailField.setText(i.getEmail());
            }
        });
    }

    public void editItem(View view) {
        Toast.makeText(this, "Edit item action not fully implemented yet.", Toast.LENGTH_SHORT).show();
    }

    public void deleteItem(View view) {
        Toast.makeText(this, "Delete item action not fully implemented yet.", Toast.LENGTH_SHORT).show();
    }
}
