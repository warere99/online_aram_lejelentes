package com.wr.onlineramlejelentes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PlaceOfUsagesAcivity extends AppCompatActivity {
    //firestorekapcsolathoz
    private FirebaseUser authenticated_user;
    private FirebaseFirestore fs;
    private CollectionReference fsPlaceOfUsage;
    private CollectionReference fsUser;
    private RecyclerView pouView;
    private ArrayList<PlaceOfUsage> pouList;
    private PlaceOfUsageAdapter pouAdapter;

    private User currentUserData;

    private int gridNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_of_usages);
        authenticated_user = FirebaseAuth.getInstance().getCurrentUser();

        if(authenticated_user != null){

        }else{
            Toast.makeText(PlaceOfUsagesAcivity.this,"Nincs bejelentkezett felhasználó!", Toast.LENGTH_SHORT).show();
            finish();
        }

        pouView = findViewById(R.id.recView);
        pouView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        pouList = new ArrayList<>();

        pouAdapter = new PlaceOfUsageAdapter(this, pouList);

        pouView.setAdapter(pouAdapter);

        currentUserData = new User();
        fs = FirebaseFirestore.getInstance();
        fsPlaceOfUsage = fs.collection("PlaceOfUsage");
        fsUser = fs.collection("User");
        queryData();
        //initData();
    }

    private void queryData() {
        pouList.clear();
        fsUser.whereEqualTo("email",authenticated_user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                currentUserData = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                //Toast.makeText(PlaceOfUsagesAcivity.this,currentUserData.getCustomerCode(), Toast.LENGTH_SHORT).show();

                fsPlaceOfUsage.whereEqualTo("customerCode",currentUserData.getCustomerCode()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot item: queryDocumentSnapshots) {
                            pouList.add(item.toObject(PlaceOfUsage.class));
                        }
                        pouAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initData() {
        String [] itemIdentifier = getResources().getStringArray(R.array.pou_identifiery);
        String [] itemAddress = getResources().getStringArray(R.array.pou_address);

        pouList.clear();

        for (int i = 0; i < itemIdentifier.length; i++) {
            fsPlaceOfUsage.add(new PlaceOfUsage(itemIdentifier[i], itemAddress[i]));
        }
        pouAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_pou, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_place_of_usage){
            Intent pou_intent = new Intent(this, PlaceOfUsagesAcivity.class);
            startActivity(pou_intent);
        }else if(item.getItemId() == R.id.menu_profile) {
            Intent profile_intent = new Intent(this, ProfileActivity.class);
            startActivity(profile_intent);
        }else if(item.getItemId() == R.id.menu_add_place_of_usage) {
            Intent add_pou_intent = new Intent(this, AddPlaceOfUsageActivity.class);
            startActivity(add_pou_intent);
        }else if(item.getItemId() == R.id.menu_logout){
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}