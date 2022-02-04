package com.maru.inunavi;

import static com.maru.inunavi.IpAddress.DemoIP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.CookieManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maru.inunavi.ui.timetable.CalendarFragment;
import com.maru.inunavi.ui.map.MapFragment;
import com.maru.inunavi.ui.recommend.RecommendFragment;
import com.maru.inunavi.ui.satisfied.SatisfiedFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    public static CookieManager cookieManager;

    public static String sessionURL = IpAddress.isTest ? "http://192.168.0.101/inuNavi/" :
            "http://" + DemoIP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        cookieManager = CookieManager.getInstance();

        MapFragment mapFragment = new MapFragment();
        SatisfiedFragment satisfiedFragment= new SatisfiedFragment();
        CalendarFragment calendarFragment = new CalendarFragment();
        RecommendFragment recommendFragment = new RecommendFragment();

        bottomNavigationView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main, mapFragment).commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main, mapFragment, "map").commit();



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                switch (menuItem.getItemId()) {
                    case R.id.navigation_map:

                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main,mapFragment).commitNowAllowingStateLoss();
                        return true;

                    case R.id.navigation_satisfied:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main,satisfiedFragment).commitNowAllowingStateLoss();
                        return true;

                    case R.id.navigation_calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main,calendarFragment).commitNowAllowingStateLoss();
                        return true;

                    case R.id.navigation_recommend:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_frameLayout_activity_main,recommendFragment).commitNowAllowingStateLoss();
                        return true;
                }

               /* switch (menuItem.getItemId()) {
                    case R.id.navigation_map:

                        if(fragmentManager.findFragmentByTag("map") != null){
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("map")).commit();
                        }else{
                            fragmentManager.beginTransaction().add(R.id.nav_host_frameLayout_activity_main, new MapFragment(), "map").commit();
                        }

                        if(fragmentManager.findFragmentByTag("satisfied") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("satisfied")).commit();
                        }

                        if(fragmentManager.findFragmentByTag("calendar") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("calendar")).commit();

                        }
                        if(fragmentManager.findFragmentByTag("recommend") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("recommend")).commit();
                        }

                        return true;

                    case R.id.navigation_satisfied:

                        if(fragmentManager.findFragmentByTag("satisfied") != null){
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("satisfied")).commit();
                        }else{
                            fragmentManager.beginTransaction().add(R.id.nav_host_frameLayout_activity_main, new SatisfiedFragment(), "satisfied").commit();
                        }

                        if(fragmentManager.findFragmentByTag("map") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("map")).commit();
                        }

                        if(fragmentManager.findFragmentByTag("calendar") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("calendar")).commit();

                        }
                        if(fragmentManager.findFragmentByTag("recommend") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("recommend")).commit();
                        }

                        return true;

                    case R.id.navigation_calendar:

                        if(fragmentManager.findFragmentByTag("calendar") != null){
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("calendar")).commit();
                        }else{
                            fragmentManager.beginTransaction().add(R.id.nav_host_frameLayout_activity_main, new CalendarFragment(), "calendar").commit();
                        }

                        if(fragmentManager.findFragmentByTag("map") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("map")).commit();
                        }

                        if(fragmentManager.findFragmentByTag("satisfied") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("satisfied")).commit();

                        }
                        if(fragmentManager.findFragmentByTag("recommend") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("recommend")).commit();
                        }

                        return true;

                    case R.id.navigation_recommend:

                        if(fragmentManager.findFragmentByTag("recommend") != null){
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("recommend")).commit();
                        }else{
                            fragmentManager.beginTransaction().add(R.id.nav_host_frameLayout_activity_main, new RecommendFragment(), "recommend").commit();
                        }

                        if(fragmentManager.findFragmentByTag("map") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("map")).commit();
                        }

                        if(fragmentManager.findFragmentByTag("satisfied") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("satisfied")).commit();

                        }
                        if(fragmentManager.findFragmentByTag("calendar") != null) {
                            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("calendar")).commit();
                        }
                        return true;
                }

                */
                return false;
            }
        });




    }

    public CookieManager getCookieManager(){
        return this.cookieManager;
    }


}