package com.example.note_ally;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FindHelp extends AppCompatActivity implements View.OnClickListener {

    public FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_help);

        findViewById(R.id.addhelp).setOnClickListener(this);
    }

    private void help() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, AddHelp.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addhelp:
                help();
                break;
        }
    }

}