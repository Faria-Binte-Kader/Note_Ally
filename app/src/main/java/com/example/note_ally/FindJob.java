package com.example.note_ally;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FindJob extends AppCompatActivity implements View.OnClickListener {

    public FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_job);

        findViewById(R.id.addjob).setOnClickListener(this);
    }

    private void job() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, AddJob.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addjob:
                job();
                break;
        }
    }

}