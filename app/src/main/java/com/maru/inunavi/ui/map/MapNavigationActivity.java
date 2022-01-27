package com.maru.inunavi.ui.map;


import static java.lang.Thread.sleep;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.maru.inunavi.R;

import java.util.ArrayList;
import java.util.List;


public class MapNavigationActivity extends AppCompatActivity implements OnMapReadyCallback, SensorEventListener {

    private GoogleMap gMap;
    private SupportMapFragment mapFragment;
    private GPSTracker gpsTracker;
    private double bearing = 0;
    private Polyline polyline = null;

    private SensorManager m_sensor_manager;
    private Sensor m_ot_sensor;
    private int m_check_count = 0;

    private List<LatLng> latLngList = new ArrayList<>();

    private double azimuth;

    private boolean isMapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_activity_navigation);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_navigation);

        mapFragment.getMapAsync(this);

        TextView map_frag_navi_route_button_stop = findViewById(R.id.map_frag_navi_route_button_stop);
        map_frag_navi_route_button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        m_sensor_manager  = (SensorManager)getSystemService(SENSOR_SERVICE);
        // SensorManager 를 이용해서 방향 센서 객체를 얻는다.
        m_ot_sensor = m_sensor_manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);


        new Thread(new Runnable() {

            public void run(){

                    while(true) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                latLngList.clear();
                                gMap.clear();

                                gpsTracker = new GPSTracker(getBaseContext());

                                latLngList.add(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
                                latLngList.add(new LatLng(37.41334710580124, 126.6761408174503));
                                latLngList.add(new LatLng(37.41366646718886, 126.67584115338498));
                                latLngList.add(new LatLng(37.41428710900992, 126.67648220689182));
                                latLngList.add(new LatLng(37.4147330038427, 126.67671738628489));
                                latLngList.add(new LatLng(37.41501018037381, 126.67678566417317));


                            /*double dLon = (37.41501018037381 - gpsTracker.getLongitude());
                            double y = Math.sin(dLon) * Math.cos(37.41501018037381);
                            double x = Math.cos(gpsTracker.getLatitude()) * Math.sin(37.41501018037381) - Math.sin(gpsTracker.getLatitude()) * Math.cos(37.41501018037381) * Math.cos(dLon);
                            bearing = Math.toDegrees((Math.atan2(y, x)));
                            bearing = (360 - ((bearing + 360) % 360));*/

                                //updateCamera(gMap, bearing);

                                updateCamera(gMap, azimuth);

                                PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngList).color(R.color.main_color);
                                polyline = gMap.addPolyline(polylineOptions);
                                stylePolyline(polyline);

                            }

                        });

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }



            }

        }).start();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.gMap = googleMap;
        this.gpsTracker = new GPSTracker(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            System.exit(0);
        }

        gMap.setMyLocationEnabled(true);

        gMap.getUiSettings().setMyLocationButtonEnabled(true);

        gMap.getUiSettings().setMapToolbarEnabled(false);

        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        View compassButton = ((View) mapFragment.getView().findViewWithTag("GoogleMapCompass"));
        RelativeLayout.LayoutParams rlpLocation = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        RelativeLayout.LayoutParams rlpCompass = (RelativeLayout.LayoutParams) compassButton.getLayoutParams();

        // position on right bottom
        rlpLocation.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlpLocation.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlpLocation.setMargins(0, DpToPixel(12), 0, 0);

        rlpCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlpCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlpCompass.setMargins(0, DpToPixel(12), 0, 0);


        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(37.41346617430547, 126.67603169000573) , 17));

        gMap.getUiSettings().setScrollGesturesEnabled(false);

        //--------------------------맵 초기화 완료---------------------------------------------

    }


    public int DpToPixel(int dp) {

        Resources r = this.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );

        return px;

    }

    public void updateCamera(GoogleMap gMap, double bearing) {

        if(!isMapReady){

            isMapReady = true;

            CameraPosition currentPlace = new CameraPosition.Builder()
                    .target(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()))
                    .bearing((float)bearing).tilt(65.5f).zoom(20).build();

            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(currentPlace), new GoogleMap.CancelableCallback() {
                @Override
                public void onCancel() {
                    isMapReady = false;

                }

                @Override
                public void onFinish() {

                    isMapReady = false;


                }
            });

        }



    }


    private static final int COLOR_BLACK_ARGB = 0xff02468E;
    private static final int POLYLINE_STROKE_WIDTH_PX = 20;


    private void stylePolyline(Polyline polyline) {

        polyline.setStartCap(new RoundCap());
        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);


    }


    protected void onResume() {
        super.onResume();
        m_check_count = 0;

        // 센서 값을 이 컨텍스트에서 받아볼 수 있도록 리스너를 등록한다.
        m_sensor_manager.registerListener(this, m_ot_sensor, SensorManager.SENSOR_DELAY_UI);

    }

    protected void onPause() {
        super.onPause();
        m_sensor_manager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }


    public void onSensorChanged(SensorEvent event)
    {
        // 방향 센서가 전달한 데이터인 경우

        // 첫번째 데이터인 방위값으로 문자열을 구성하여 텍스트뷰에 출력한다.
        if(event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            String str;
            azimuth = event.values[0];

        }

    }






}