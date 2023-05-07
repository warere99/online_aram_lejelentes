package com.wr.onlineramlejelentes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.ViewHolder> {
    private ArrayList<Measurement> measurementsData;

    private Context cont;
    private FirebaseFirestore fs;
    private CollectionReference fsMeasurement;
    MeasurementAdapter(Context cont, ArrayList<Measurement> measurementData){
        this.cont = cont;
        this.measurementsData = measurementData;

        fs = FirebaseFirestore.getInstance();
        fsMeasurement = fs.collection("Measurement");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(cont).inflate(R.layout.list_measurement, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementAdapter.ViewHolder holder, int position) {
        Measurement measurement = measurementsData.get(position);
        holder.bindTo(measurement);

        holder.button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fsMeasurement.document(measurement.getDocumentId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(cont,"Mérőállás sikeresen törlésre került!", Toast.LENGTH_SHORT).show();
                        Intent pou = new Intent(cont, PlaceOfUsageAdapter.class);
                        cont.startActivity(pou);
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.measurementsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView measurementDate;
        private TextView measurementValue;

        private Button button_delete;

        public ViewHolder(View pouView){
            super(pouView);
            measurementDate = pouView.findViewById(R.id.textMeasurementDate);
            measurementValue = pouView.findViewById(R.id.textMeasurementValue);
            button_delete = pouView.findViewById(R.id.delete);
        }

        public void bindTo(Measurement current_measurement) {
            measurementDate.setText(current_measurement.getMeasurementDate());
            measurementValue.setText(Integer.toString(current_measurement.getMeasurementValue()));
        }
    }
}
