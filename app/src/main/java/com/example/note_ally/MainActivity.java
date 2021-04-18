package com.example.note_ally;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new fragment_mynotes()).commit();
            navigationView.setCheckedItem(R.id.nav_mynotes);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_mynotes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_mynotes()).commit();
                break;
            case R.id.nav_feed:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_feed()).commit();
                break;
            case R.id.nav_repository:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_repository()).commit();
                break;
            case R.id.nav_job:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_job()).commit();
                break;
            case R.id.nav_marketplace:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_marketplace()).commit();
                break;
            case R.id.nav_admission:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragment_admission()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
