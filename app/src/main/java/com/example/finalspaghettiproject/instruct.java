package com.example.finalspaghettiproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class instruct extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruct);

        //just a button to go back to the main menu or activity
        Button button = findViewById(R.id.buttonInfo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(instruct.this,MainActivity.class));
            }
        });


    }
}
