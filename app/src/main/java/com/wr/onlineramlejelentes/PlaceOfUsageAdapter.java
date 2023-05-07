package com.wr.onlineramlejelentes;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaceOfUsageAdapter extends RecyclerView.Adapter<PlaceOfUsageAdapter.ViewHolder> {
    private ArrayList<PlaceOfUsage> pousData;

    private Context cont;
    private int lastPos = -1;

    PlaceOfUsageAdapter(Context cont, ArrayList<PlaceOfUsage> pousData){
        this.pousData = pousData;
        this.cont = cont;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(cont).inflate(R.layout.list_pou, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceOfUsageAdapter.ViewHolder holder, int position) {
        PlaceOfUsage curret_pou = pousData.get(position);
        holder.bindTo(curret_pou);
        holder.button_add_measurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_measurement = new Intent(cont, AddMeasurementActivity.class);
                add_measurement.putExtra("placeOfUsageIdenifier",curret_pou.getPlaceOfUsageIdentifier());
                cont.startActivity(add_measurement);
            }
        });

        holder.button_show_measurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent measurements = new Intent(cont, MeasurementsAcitivity.class);
                measurements.putExtra("placeOfUsageIdenifier",curret_pou.getPlaceOfUsageIdentifier());
                cont.startActivity(measurements);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.pousData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView place_of_usage_identifier;
        private TextView place_of_usage_address;
        private Button button_add_measurement;
        private Button button_show_measurements;

        public ViewHolder(View pouView){
            super(pouView);
            place_of_usage_identifier = pouView.findViewById(R.id.place_of_usage_identifier);
            place_of_usage_address = pouView.findViewById(R.id.place_of_usage_address);
            button_add_measurement = pouView.findViewById(R.id.button_add_measurement);
            button_show_measurements = pouView.findViewById(R.id.button_show_measurements);
        }

        public void bindTo(PlaceOfUsage curret_pou) {
            place_of_usage_identifier.setText(curret_pou.getPlaceOfUsageIdentifier());
            place_of_usage_address.setText(curret_pou.getPlaceOfUsageAddress());
        }
    }
}
