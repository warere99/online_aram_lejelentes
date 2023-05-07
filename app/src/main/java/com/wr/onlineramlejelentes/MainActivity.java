package com.wr.onlineramlejelentes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private EditText edittext_email;
    private EditText edittext_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edittext_email = findViewById(R.id.email);
        edittext_password = findViewById(R.id.password);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(View view) {

        firebaseAuth.signInWithEmailAndPassword(edittext_email.getText().toString(), edittext_password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                    start();
                }else{
                    Toast.makeText(MainActivity.this,"Sikertelen bejelentkezés!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registration(View view) {
        Intent reg_intent = new Intent(this, RegistrationActivity.class);
        startActivity(reg_intent);
    }

    public void start(){
        Intent pou_intent = new Intent(this, PlaceOfUsagesAcivity.class);
        startActivity(pou_intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}