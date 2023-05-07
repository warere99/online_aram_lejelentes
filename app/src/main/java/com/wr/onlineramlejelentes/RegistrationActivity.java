package com.wr.onlineramlejelentes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {
    EditText name_field;
    EditText email_filed;
    EditText password_field;
    EditText password_again_field;
    EditText customer_code_field;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fs;
    private CollectionReference fsUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name_field = findViewById(R.id.name);
        email_filed = findViewById(R.id.email);
        password_field = findViewById(R.id.password);
        password_again_field = findViewById(R.id.password_again);
        customer_code_field = findViewById(R.id.customer_code);

        firebaseAuth = FirebaseAuth.getInstance();
        fs = FirebaseFirestore.getInstance();
        fsUser = fs.collection("User");
    }

    public void registrate(View view) {
        String name = name_field.getText().toString();
        String email = email_filed.getText().toString();
        String password = password_field.getText().toString();
        String password_again = password_again_field.getText().toString();
        String customer_code = password_again_field.getText().toString();

        if(!password.equals(password_again)){
            Toast.makeText(RegistrationActivity.this,"Sikertelen regisztráció! A jeszavak nem egyeznek!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    fsUser.add(new User(customer_code,email,name)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegistrationActivity.this,"Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                            start();
                        }
                    });
                }else{
                    Toast.makeText(RegistrationActivity.this,"Sikertelen regisztráció! A hiba oka: "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void close(View view) {
        finish();
    }

    public void start(){
        Intent pou_intent = new Intent(this, PlaceOfUsagesAcivity.class);
        startActivity(pou_intent);
    }
}