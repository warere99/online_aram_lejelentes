package com.wr.onlineramlejelentes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MeasurementsAcitivity extends AppCompatActivity {
        private FirebaseUser authenticated_user;
        private FirebaseFirestore fs;
    private CollectionReference fsMeasurement;
    private RecyclerView measurementRecView;
    private ArrayList<Measurement> measurementList;
    private MeasurementAdapter measurementAdapter;
    private String placeOfUsageIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurements_acitivity);
        placeOfUsageIdentifier = getIntent().getStringExtra("placeOfUsageIdenifier");

        authenticated_user = FirebaseAuth.getInstance().getCurrentUser();

        if(authenticated_user != null){

        }else{
            Toast.makeText(MeasurementsAcitivity.this,"Nincs bejelentkezett felhasználó!", Toast.LENGTH_SHORT).show();
            finish();
        }

        measurementRecView = findViewById(R.id.measurementRecView);
        measurementRecView.setLayoutManager(new GridLayoutManager(this, 1));
        measurementList = new ArrayList<>();

        measurementAdapter = new MeasurementAdapter(this, measurementList);

        fs = FirebaseFirestore.getInstance();
        fsMeasurement = fs.collection("Measurement");

        measurementRecView.setAdapter(measurementAdapter);

        queryData();
    }

    private void queryData() {
        measurementList.clear();
        fsMeasurement.whereEqualTo("placeOfUsageIdentifier",placeOfUsageIdentifier).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot item: queryDocumentSnapshots) {
                    measurementList.add(item.toObject(Measurement.class));
                    measurementList.get(measurementList.size()-1).setDocumentId(item.getId());
                }
                measurementAdapter.notifyDataSetChanged();
            }
        });
    }
}