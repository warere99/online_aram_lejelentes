package com.wr.onlineramlejelentes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMeasurementActivity extends AppCompatActivity {

    private FirebaseFirestore fs;
    private CollectionReference fsMeasurement;
    private Notification notification ;
    EditText measurementValue;
    String placeOfUsageIdentifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measurement);
        notification = new Notification(this);

        //intenten keresztül kapott felhasználási hely azonosító
        placeOfUsageIdentifier = getIntent().getStringExtra("placeOfUsageIdenifier");

        //
        measurementValue= findViewById(R.id.measurementValue);

        fs = FirebaseFirestore.getInstance();
        fsMeasurement = fs.collection("Measurement");
    }

    //ha mérőállás diktálás közben háttérbe teszi az alkalmazást
    @Override
    protected void onPause() {
        super.onPause();
        //notification.send("A méróállás lejelentés megszakadt!");
        finish();
    }
    public void saveMeasurementValue(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String currentTime = sdf.format(new Date());
        fsMeasurement.add(new Measurement(placeOfUsageIdentifier,currentTime,Integer.parseInt(measurementValue.getText().toString()))).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                notification.send("Sikeres mérőállás rögzítés!");
                //Toast.makeText(AddMeasurementActivity.this,"Sikeres mérőállás lejelentés!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}