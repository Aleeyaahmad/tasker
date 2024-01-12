package com.example.tasker;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Member extends AppCompatActivity {


    TextView textView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_member);

        textView = findViewById(R.id.textLink);

        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }

}