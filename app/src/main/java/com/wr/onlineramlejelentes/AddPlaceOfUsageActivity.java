package com.wr.onlineramlejelentes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AddPlaceOfUsageActivity extends AppCompatActivity {
    EditText placeOfUsageIdentifier_field;
    EditText placeOfUsageAddress_field;

    private FirebaseUser authenticated_user;
    private FirebaseFirestore fs;
    private CollectionReference fsPlaceOfUsage;
    private CollectionReference fsUser;
    private User currentUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_of_usage);
        authenticated_user = FirebaseAuth.getInstance().getCurrentUser();

        placeOfUsageIdentifier_field = findViewById(R.id.placeOfUsageIdentifier);
        placeOfUsageAddress_field = findViewById(R.id.placeOfUsageAddress);

        fs = FirebaseFirestore.getInstance();
        fsPlaceOfUsage = fs.collection("PlaceOfUsage");
        fsUser = fs.collection("User");

        fsUser.whereEqualTo("email",authenticated_user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                currentUserData = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                //Toast.makeText(PlaceOfUsagesAcivity.this,currentUserData.getCustomerCode(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void addPlaceOfUsage(View view) {

        fsPlaceOfUsage.add(new PlaceOfUsage(currentUserData.getCustomerCode(), placeOfUsageIdentifier_field.getText().toString(), placeOfUsageAddress_field.getText().toString())).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AddPlaceOfUsageActivity.this,"Sikeres felhasználási hely rögzítés!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}