package com.example.tasker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<DataClass> dataList = new ArrayList<>();
    DatabaseReference databaseReference;
    MyAdapter adapter;
    AlertDialog dialog;
    SearchView searchView;
    Button aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        aboutUs = findViewById(R.id.btnAboutUs);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();

        // Initialize the adapter and set it to RecyclerView
        adapter = new MyAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        // Show the dialog before fetching data
        dialog.show();

        // Fetch data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Tasker");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!isFinishing() && getWindow() != null) {
                    dataList.clear();
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                        if (dataClass != null) {
                            dataClass.setKey(itemSnapshot.getKey());
                            dataList.add(dataClass);
                        }
                    }
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    // Dismiss the dialog when data is fetched
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Dismiss the dialog if an error occurs
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        // Set click listener for FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for About Us button
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Member.class);
                startActivity(intent);
            }
        });
    }

    public void searchList(String text) {
        ArrayList<DataClass> searchList = new ArrayList<>();
        for (DataClass dataClass : dataList) {
            if (dataClass.getTaskName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(dataClass);
            }
        }
        adapter.searchDataList(searchList);
    }
}
