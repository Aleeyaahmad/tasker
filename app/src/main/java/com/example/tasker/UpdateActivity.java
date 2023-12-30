package com.example.tasker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UpdateActivity extends AppCompatActivity {

    Button updateButton;
    EditText updateTask, updateDesc;
    String task, desc;
    String key;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateButton = findViewById(R.id.updateButton);
        updateTask = findViewById(R.id.updateTask);
        updateDesc = findViewById(R.id.updateDesc);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            updateTask.setText(bundle.getString("Task"));
            updateDesc.setText(bundle.getString("Task Description"));
            key = bundle.getString("Key");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Tasker").child(key);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(UpdateActivity.this , MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveData(){
       updateData();
    }

    public void updateData(){
        task = updateTask.getText().toString().trim();
        desc = updateDesc.getText().toString().trim();

        DataClass dataClass = new DataClass(task, desc);
        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}