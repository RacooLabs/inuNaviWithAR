package com.maru.inunavi.ui.satisfied;


import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.IpAddress.DemoIP_ClientTest;
import static com.maru.inunavi.MainActivity.sessionURL;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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


public class MapOverviewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private SupportMapFragment mapFragment;
    private Polyline polyline = null;
    private String url = sessionURL;

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

        setContentView(R.layout.satisfied_overview);

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

        GetAnalysisResultBackgroundTask();

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


        map_activity_overview_date.setText(overviewInfoList.get(i).getEndLectureTime() + " 수업 시작");
        map_activity_overview_start_lecture_title.setText(overviewInfoList.get(i).getStartLectureName());
        map_activity_overview_end_lecture_title.setText(overviewInfoList.get(i).getEndLectureName());
        map_activity_overview_time_distance.setText(overviewInfoList.get(i).getTotalTime()+ "분 | " + overviewInfoList.get(i).getDistance() + "m");


        PolylineOptions polylineOptions = new PolylineOptions().addAll(overviewInfoList.get(i).getDirectionList()).color(R.color.main_color);
        polyline = gMap.addPolyline(polylineOptions);
        stylePolyline(polyline);


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


    // 학기 경로 분석 결과를 가져오는 서버 통신 코드
    Disposable getOverviewRouteTask;

    void GetAnalysisResultBackgroundTask() {

        getOverviewRouteTask = Observable.fromCallable(() -> {

            // doInBackground

            String target = (IpAddress.isTest ? "http://"+ DemoIP_ClientTest +"/inuNavi/GetOverviewRoot.php" :
                    "http://" + DemoIP + "/selectLecture")+ "?userID=\"" + MainActivity.cookieManager.getCookie(url).replace("cookieKey=", "") + "\"";


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
                Log.d("@@@MapOverviewActivity 310", e.toString());
            }

            return null;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorReturn(___ -> "{response : []}").subscribe((result) -> {

            // onPostExecute

            try {

                Log.d("@@@MapOverviewActivity 321", result);

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");

                String startLectureName = "";
                String endLectureName = "";
                String endLectureTime = "";
                int totalTime = 0;
                int distance = 0;
                String directionString = "";

                int count = 0;

                overviewInfoList.clear();

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);

                    startLectureName = object.getString("startLectureName");
                    endLectureName = object.getString("endLectureName");
                    endLectureTime = object.getString("endLectureTime");
                    totalTime = object.getInt("totalTime");
                    distance = object.getInt("distance");
                    directionString = object.getString("directionString");

                    count++;

                    overviewInfoList.add(new OverviewInfo(startLectureName,endLectureName,endLectureTime, totalTime, distance,directionString));

                }

                if(count == 0){

                    AlertDialog.Builder msgBuilder = new AlertDialog.Builder(this)
                            .setTitle("알림")
                            .setMessage("경로 정보가 없습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    finish();

                                }
                            });

                    AlertDialog msgDlg = msgBuilder.create(); msgDlg.show();


                }else {


                    gMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                        @Override
                        public void onMapLoaded() {

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

            } catch (Exception e) {
                e.printStackTrace();

            }

            getOverviewRouteTask.dispose();

        });

    }




}
