package com.stream.inunavi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stream.inunavi.ui.calendar.CalendarFragment;
import com.stream.inunavi.ui.map.MapFragment;
import com.stream.inunavi.ui.recommend.RecommendFragment;
import com.stream.inunavi.ui.satisfied.SatisfiedFragment;

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

        /*BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_map, R.id.navigation_satisfied, R.id.navigation_calendar, R.id.navigation_recommend)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);*/

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