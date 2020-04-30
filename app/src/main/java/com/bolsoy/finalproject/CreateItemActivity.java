package com.bolsoy.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import io.grpc.Context;

import static java.lang.System.*;

public class CreateItemActivity extends AppCompatActivity {


    private static final String TAG = "CreateItemActivity";
    private static final String ITEM = "item";

    private FirebaseAuth mAuth;
    private final FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private StorageReference mStorageReference;

    private ConstraintLayout mCreateItem;
    private TextView mTitleLabel;
    private EditText mTitleField;
    private TextView mPriceLabel;
    private EditText mPriceField;
    private TextView mDescriptionLabel;
    private EditText mDescriptionField;
    public Uri imguri;
    private ImageView img;
    private Boolean imageSelected = false;
    public String storageName;

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
        img = findViewById(R.id.image);

        mStorageReference = FirebaseStorage.getInstance().getReference("images");

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
        String imagePath;
        if (imageSelected) {
            Fileuploader();
            imagePath = "images/" + storageName;
        } else {
            imagePath = null;
        }

        Item newItem = new Item(title, description, price, dateCreated, email, imagePath);

        Toast.makeText(this, "Posting " + title + " for $" + price, Toast.LENGTH_SHORT).show();
        mDb.collection(ITEM)
                .add(newItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Item added with ID: " + documentReference.getId());
                        goHome();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding item", e);
                    }
                });
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


    public void selectImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
            img.setImageURI(imguri);
            imageSelected = true;
        }
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader() {
        long millis = System.currentTimeMillis();
        String mill = String.valueOf(millis);
        storageName = mill + "." + getExtension(imguri);
        StorageReference Ref = mStorageReference.child(storageName);

        Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        Toast.makeText(CreateItemActivity.this, "Image uploaded", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
