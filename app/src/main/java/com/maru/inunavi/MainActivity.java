package com.maru.inunavi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maru.inunavi.ui.calendar.CalendarFragment;
import com.maru.inunavi.ui.map.MapFragment;
import com.maru.inunavi.ui.recommend.RecommendFragment;
import com.maru.inunavi.ui.satisfied.SatisfiedFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        MapFragment mapFragment = new MapFragment();
        SatisfiedFragment satisfiedFragment= new SatisfiedFragment();
        CalendarFragment calendarFragment = new CalendarFragment();
        RecommendFragment recommendFragment = new RecommendFragment();



        bottomNavigationView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main, mapFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.navigation_map:

                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main,mapFragment).commit();
                        return true;

                    case R.id.navigation_satisfied:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main,satisfiedFragment).commit();
                        return true;

                    case R.id.navigation_calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main,calendarFragment).commit();
                        return true;

                    case R.id.navigation_recommend:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main,recommendFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }
}