package com.maru.inunavi.ui.satisfied;


import static java.lang.Thread.sleep;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.maru.inunavi.R;

import java.util.ArrayList;
import java.util.List;


public class MapOverviewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private SupportMapFragment mapFragment;
    private Polyline polyline = null;

    private Marker startMarker = null; // 출발 마커
    private Marker endMarker = null; // 도착 마커
    private final int markerSize = 48;

    private ImageView map_overview_back;

    private TextView map_activity_overview_date;
    private TextView map_activity_overview_start_lecture_title;
    private TextView map_activity_overview_end_lecture_title;
    private TextView map_activity_overview_time_distance;


    private TextView map_activity_overview_button_next;
    private TextView map_activity_overview_button_back;


    private List<OverviewInfo> overviewInfoList= new ArrayList<>();

    private int position = 0;
    private int beforePosition = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_activity_overview);

        map_overview_back = findViewById(R.id.map_overview_back);

        map_activity_overview_date = findViewById(R.id.map_activity_overview_date);
        map_activity_overview_start_lecture_title = findViewById(R.id.map_activity_overview_start_lecture_title);
        map_activity_overview_end_lecture_title = findViewById(R.id.map_activity_overview_end_lecture_title);
        map_activity_overview_time_distance = findViewById(R.id.map_activity_overview_time_distance);

        map_activity_overview_button_next = findViewById(R.id.map_activity_overview_button_next);
        map_activity_overview_button_back = findViewById(R.id.map_activity_overview_button_back);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_overview);

        mapFragment.getMapAsync(this);

        map_overview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



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


        //--------------------------맵 초기화 완료---------------------------------------------

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(37.37532099190484, 126.63285407077159) , 17));

        gMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                overviewInfoList.clear();

                overviewInfoList.add(new OverviewInfo("너구리의 이해", "라쿤의 이해", "화요일 09:30 AM", 12, 132,"37.3747872226735, 126.63342072263077, 37.375203052050516, 126.63380415078625, 37.375773021330396, 126.63272604103146, 37.37541813530646," +
                        "126.63237418931232, 37.37556152380112, 126.63214864333851"));

                overviewInfoList.add(new OverviewInfo("라쿤의 이해", "고양이의 이해", "화요일 12:30 PM",20, 274, "37.37556152380112, 126.63214864333851, 37.37539651900144, " +
                        "126.63232797956309,37.37581772204735, 126.63274975057166,37.37691642355557, 126.63394063341136,37.37629986221577, 126.6349736340463,37.37621382999831, 126.63485860562228,37.376151098111194, 126.63503904240133,37.376194657526, 126.63553868017112,37.37625380470092, 126.63559281120482"));

                if(gMap != null)
                    gMap.setPadding(0,DpToPixel(70), 0, DpToPixel(140));


                showOverviewDirection(overviewInfoList, position, gMap);
                beforePosition = position;


                map_activity_overview_button_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        position++;

                        if(position>=overviewInfoList.size()){
                            position = overviewInfoList.size()-1;
                        }

                        if (beforePosition != position) {
                            showOverviewDirection(overviewInfoList, position, gMap);
                        }

                        beforePosition = position;

                    }
                });

                map_activity_overview_button_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        position--;
                        if(position<0){
                            position = 0;
                        }

                        if (beforePosition != position) {
                            showOverviewDirection(overviewInfoList, position, gMap);
                        }

                        beforePosition = position;

                    }
                });


            }
        });



    }

    public void showOverviewDirection(List<OverviewInfo> overviewInfoList, int i, GoogleMap gMap) {

        gMap.clear();

        if(overviewInfoList.get(i).getDirectionList() != null && !overviewInfoList.get(i).getDirectionList().isEmpty()){
            setStartMarker(overviewInfoList.get(i).getDirectionList().get(0));
            setEndMarker(overviewInfoList.get(i).getDirectionList().get(overviewInfoList.get(i).getDirectionList().size()-1));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();


            for (LatLng latLng : overviewInfoList.get(i).getDirectionList()){
                builder.include(latLng);
            }

            LatLngBounds bounds = builder.build();

            gMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, DpToPixel(48)));

        }


        map_activity_overview_date.setText(overviewInfoList.get(i).getEndLectureTime());
        map_activity_overview_start_lecture_title.setText(overviewInfoList.get(i).getStartLectureName());
        map_activity_overview_end_lecture_title.setText(overviewInfoList.get(i).getEndLectureName());
        map_activity_overview_time_distance.setText(overviewInfoList.get(i).getTotalTime()+ "분 | " + overviewInfoList.get(i).getDistance() + "m");


        PolylineOptions polylineOptions = new PolylineOptions().addAll(overviewInfoList.get(i).getDirectionList()).color(R.color.main_color);
        polyline = gMap.addPolyline(polylineOptions);
        stylePolyline(polyline);


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


    private static final int COLOR_BLACK_ARGB = 0xff02468E;
    private static final int POLYLINE_STROKE_WIDTH_PX = 14;


    private void stylePolyline(Polyline polyline) {

        polyline.setStartCap(new RoundCap());
        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);


    }

    public void setStartMarker(LatLng startLocation){

        if (startMarker != null) {
            startMarker.remove();
            startMarker = null;
        }

        if (gMap != null) {

            startMarker = gMap.addMarker(new MarkerOptions().position(startLocation).icon(bitmapDescriptorFromVector(this, R.drawable.ic_inumarker_start)));
            startMarker.setTag("startMarker");
        }

    }

    public void setEndMarker(LatLng endLocation){

        if (endMarker != null) {
            endMarker.remove();
            endMarker = null;
        }

        if (gMap != null) {

            endMarker = gMap.addMarker(new MarkerOptions().position(endLocation).icon(bitmapDescriptorFromVector(this, R.drawable.ic_inumarker_end)));
            endMarker.setTag("endMarker");

        }

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, DpToPixel(markerSize), DpToPixel(markerSize));
        Bitmap bitmap = Bitmap.createBitmap(DpToPixel(markerSize), DpToPixel(markerSize), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }




}
