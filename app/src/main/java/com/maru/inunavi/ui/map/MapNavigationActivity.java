package com.maru.inunavi.ui.map;


import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.IpAddress.DemoIP_ClientTest;
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
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.maru.inunavi.IpAddress;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MapNavigationActivity extends AppCompatActivity implements OnMapReadyCallback, SensorEventListener {

    private GoogleMap gMap;
    private SupportMapFragment mapFragment;
    private double bearing = 0;
    private Polyline polyline = null;

    private SensorManager m_sensor_manager;
    private Sensor m_ot_sensor;
    private int m_check_count = 0;

    private TextView textView1;
    private TextView textView2;

    private TextView map_activity_navigation_detail_time;
    private TextView map_activity_navigation_detail_distance;

    private String endPlaceCode;
    private LatLng endLocation;

    private Thread naviThread;

    private List<LatLng> latLngList = new ArrayList<>();

    private double azimuth;

    private boolean isMapReady = false;

    private FusedLocationProviderClient fusedLocationClient;

    private Boolean isThreadRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_activity_navigation);

        Intent intent = getIntent();

        if(intent!= null){

            endPlaceCode = intent.getStringExtra("endPlaceCode");
            endLocation = new LatLng(intent.getDoubleExtra("endLocationLatitude",0),intent.getDoubleExtra("endLocationLongitude",0));

        }


        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);

        map_activity_navigation_detail_time = findViewById(R.id.map_activity_navigation_detail_time);
        map_activity_navigation_detail_distance = findViewById(R.id.map_activity_navigation_detail_distance);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_navigation);

        mapFragment.getMapAsync(this);

        TextView map_frag_navi_route_button_stop = findViewById(R.id.map_activity_navigation_detail_button_stop);
        map_frag_navi_route_button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isThreadRun = false;
                finish();
                overridePendingTransition(0, 0);
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        m_sensor_manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // SensorManager 를 이용해서 방향 센서 객체를 얻는다.
        m_ot_sensor = m_sensor_manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.gMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            System.exit(0);
        }

        gMap.setMyLocationEnabled(true);

        gMap.getUiSettings().setMyLocationButtonEnabled(false);

        gMap.getUiSettings().setMapToolbarEnabled(false);

        //View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        View compassButton = ((View) mapFragment.getView().findViewWithTag("GoogleMapCompass"));
        //RelativeLayout.LayoutParams rlpLocation = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        RelativeLayout.LayoutParams rlpCompass = (RelativeLayout.LayoutParams) compassButton.getLayoutParams();

        // position on right bottom
        /*rlpLocation.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlpLocation.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlpLocation.setMargins(0, DpToPixel(12), 0, 0);*/

        rlpCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlpCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlpCompass.setMargins(0, DpToPixel(12), 0, 0);


        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(37.37532099190484, 126.63285407077159) , 17));

        gMap.getUiSettings().setScrollGesturesEnabled(false);

        //--------------------------맵 초기화 완료---------------------------------------------

        gMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

            @Override
            public void onMapLoaded() {

                isThreadRun = true;

                naviThread = new Thread(new Runnable() {

                    public void run() {

                        while (isThreadRun) {

                            runOnUiThread(new Runnable() {


                                @Override
                                public void run() {

                                    if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                        return;
                                    }

                                    fusedLocationClient.getLastLocation().addOnSuccessListener(MapNavigationActivity.this, new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {

                                            latLngList.clear();
                                            gMap.clear();

                                            textView1.setText(location.getLatitude()+"");
                                            textView2.setText(location.getLongitude()+"");

                                            GetRootBackgroundTask(new NaviInfo("LOCATION", endPlaceCode ,new LatLng(location.getLatitude(), location.getLongitude()), endLocation ));



                                        }
                                    });


                            /*double dLon = (37.41501018037381 - gpsTracker.getLongitude());
                            double y = Math.sin(dLon) * Math.cos(37.41501018037381);
                            double x = Math.cos(gpsTracker.getLatitude()) * Math.sin(37.41501018037381) - Math.sin(gpsTracker.getLatitude()) * Math.cos(37.41501018037381) * Math.cos(dLon);
                            bearing = Math.toDegrees((Math.atan2(y, x)));
                            bearing = (360 - ((bearing + 360) % 360));*/

                                    //updateCamera(gMap, bearing);



                                }

                            });

                            try {
                                sleep(1000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }



                    }

                });

                naviThread.start();


            }
        });

    }


    public int DpToPixel(int dp) {

        try{
            Resources r = this.getResources();

            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp,
                    r.getDisplayMetrics()
            );

            return px;

        }catch (Exception e){

        }

        return 0;

    }

    public void updateCamera(GoogleMap gMap, double bearing) {

        if(!isMapReady){

            isMapReady = true;

            if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            fusedLocationClient.getLastLocation().addOnSuccessListener(MapNavigationActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    CameraPosition currentPlace = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .bearing((float)bearing).tilt(60).zoom(20).build();

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
            });

        }



    }


    private static final int COLOR_BLACK_ARGB = 0xff02468E;
    private static final int POLYLINE_STROKE_WIDTH_PX = 36;


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

    // 경로 가져오는 서버 통신 코드

    Disposable getRouteBackgroundTask;

    void GetRootBackgroundTask(NaviInfo naviInfo) {

        getRouteBackgroundTask = Observable.fromCallable(() -> {

            // doInBackground

            String target = (IpAddress.isTest ? "http://"+ DemoIP_ClientTest +"/inuNavi/GetRootLive.php" :
                    "http://" + DemoIP + "/getRootLive")+ "?startPlaceCode=\"" + naviInfo.getStartPlaceCode() + "\"&endPlaceCode=\"" + naviInfo.getEndPlaceCode()
                    + "\"&startLocation=\"" + naviInfo.getStartLocation().latitude + "," + naviInfo.getStartLocation().longitude
                    + "\"&endLocation=\"" + naviInfo.getEndLocation().latitude + "," + naviInfo.getEndLocation().longitude + "\"";

            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("@@@MapNavigationActivity396", e.toString());
            }

            return null;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorReturn(___ -> "{response : []}").subscribe((result) -> {

            // onPostExecute

            try {

                Log.d("@@@MapNavigationActivity 407", result);

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");

                int count = 0;

                Boolean isArrived = false;
                String route = "";
                int time = 0;
                double dist = 0;
                int steps = 0;

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);

                    isArrived = object.getBoolean("isArrived");
                    route = object.getString("route");
                    time = object.getInt("time");
                    dist = object.getDouble("dist");
                    steps = object.getInt("steps");

                    count++;

                }

                if(count == 0){


                }else {

                    if(isArrived){

                        Intent sendingIntent = new Intent(MapNavigationActivity.this, MainActivity.class);
                        sendingIntent.putExtra("CallType", 4001);
                        setResult(Activity.RESULT_OK, sendingIntent);
                        isThreadRun = false;
                        finish();
                        overridePendingTransition(0, 0);

                    }else{

                        map_activity_navigation_detail_time.setText(time+"");
                        map_activity_navigation_detail_distance.setText("앞으로 " + (int)dist+"m");

                        route.trim();
                        route.replaceAll(" ","");
                        String[] routeStringSplit = route.split(",");

                        //테스트 용으로 일단 자기위치 넣음.
                        latLngList.add(naviInfo.getStartLocation());

                        for (int i=0;i<routeStringSplit.length;i+=2){

                            latLngList.add(new LatLng(Double.parseDouble(routeStringSplit[i]),
                                    Double.parseDouble(routeStringSplit[i+1])));

                        }

                        updateCamera(gMap, azimuth);

                        PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngList).color(R.color.main_color);
                        polyline = gMap.addPolyline(polylineOptions);
                        stylePolyline(polyline);


                    }

                }


            } catch (Exception e) {
                e.printStackTrace();

            }

            getRouteBackgroundTask.dispose();

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isThreadRun = false;
    }

    @Override
    public void onBackPressed() {

            isThreadRun = false;

    }
}