package com.wr.onlineramlejelentes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser authenticated_user;
    private FirebaseFirestore fs;
    private CollectionReference fsUser;
    private User currentUserData;

    EditText name_field;
    EditText password_field;
    EditText password_again_field;
    EditText customer_code_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name_field = findViewById(R.id.name);
        password_field = findViewById(R.id.password);
        password_again_field = findViewById(R.id.password_again);

        authenticated_user = FirebaseAuth.getInstance().getCurrentUser();
        fs = FirebaseFirestore.getInstance();
        fsUser = fs.collection("User");

        if (authenticated_user != null) {

        } else {
            Toast.makeText(ProfileActivity.this, "Nincs bejelentkezett felhasználó!", Toast.LENGTH_SHORT).show();
            finish();
        }

        fsUser.whereEqualTo("email", authenticated_user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                currentUserData = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                currentUserData.setDocumentRefId(queryDocumentSnapshots.getDocuments().get(0).getId());
                name_field.setText(currentUserData.getName());
            }
        });
    }

    public void saveProfile(View view) {
        fsUser.document(currentUserData.getDocumentRefId()).update("name", name_field.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivity.this,"Sikeres profil frissítés!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}