package com.example.note_ally;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FindEvent extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_event);

        findViewById(R.id.addevent).setOnClickListener(this);
    }

    private void event() {
        SharedPrefManager.getInstance(this).clear();
        Intent intent = new Intent(this, AddEvent.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addevent:
                event();
                break;
        }
    }

}