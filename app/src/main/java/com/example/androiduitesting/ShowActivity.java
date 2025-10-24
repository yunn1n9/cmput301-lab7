package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {
    public static final String EXTRA_CITY_NAME = "extra_city_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView cityText = findViewById(R.id.text_city_name);
        Button backBtn = findViewById(R.id.button_back);

        Intent i = getIntent();
        String name = i != null ? i.getStringExtra(EXTRA_CITY_NAME) : "";
        cityText.setText(name == null ? "" : name);

        backBtn.setOnClickListener(v -> finish());
    }
}