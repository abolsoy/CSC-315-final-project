package com.bolsoy.finalproject;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewItemActivity extends AppCompatActivity {

    private static final String TAG = "ViewItemActivity";
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
    private ImageView mItemImageView;

    private DocumentReference mItemRef;

    private String itemSeller;
    private String itemTitle;
    private String imagePath;

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
        mEmailField = findViewById(R.id.email);
        mItemImageView = findViewById(R.id.image);

//        String email = getIntent().getExtras().getString("email");
        String id = getIntent().getExtras().getString("id");
        mFirestore = FirebaseFirestore.getInstance();
        mItemRef = mFirestore.collection("item").document(id);

        mItemRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Item i = documentSnapshot.toObject(Item.class);
                itemSeller = i.getEmail();
                itemTitle = i.getTitle();
                imagePath = i.getImageFile();
                mTitleField.setText(itemTitle);
                mPriceField.setText(i.getPrice());
                mDescriptionField.setText(i.getDescription());
                mEmailField.setText(itemSeller);
                if (imagePath != null) {
                    mItemImageView.setVisibility(View.VISIBLE);
                    StorageReference image = mStorageRef.child(imagePath);

                    // Glide is a 3rd party library that simplifies image downloading, caching,
                    // and injection into your UI. This tells Glide to load the image from Storage and
                    // put it in the ImageView when complete.
                    GlideApp.with(ViewItemActivity.this)
                            .load(image)
                            .into(mItemImageView);
                } else if (imagePath == null) {
                    Toast.makeText(ViewItemActivity.this, "There is no image for this item", Toast.LENGTH_LONG).show();
                }
            }
        });

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

    public void contactSeller(View view) {
//        Toast.makeText(this, "Email action not fully implemented yet.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{itemSeller});
        intent.putExtra(Intent.EXTRA_SUBJECT, itemTitle);
        intent.putExtra(Intent.EXTRA_TEXT, "I am interested in this item. Is it still available for purchase?");
        startActivity(intent);
    }
}
