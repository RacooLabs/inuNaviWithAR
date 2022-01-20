package com.maru.inunavi.ui.map;


import static com.maru.inunavi.ui.map.MapFragmentState.DEFAULT_MODE;
import static com.maru.inunavi.ui.map.MapFragmentState.DETAIL_MODE;
import static com.maru.inunavi.ui.map.MapFragmentState.DIRECTION_MODE;
import static com.maru.inunavi.ui.map.MapFragmentState.SEARCH_MODE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;

import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maru.inunavi.ui.map.markerinfo.FloatingMarkerTitlesOverlay;
import com.maru.inunavi.ui.map.markerinfo.MarkerInfo;
import com.maru.inunavi.ui.timetable.SettingActivity;
import com.maru.inunavi.ui.timetable.SettingAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback {


    private MapView mapView = null;
    private GoogleMap gMap;
    private Polyline polyline = null;
    private MapFragment mapFragment= null;
    private GPSTracker gpsTracker;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    //마커 타이틀 오버레이
    private FloatingMarkerTitlesOverlay floatingMarkersOverlay;


    //선택된 마커

    private Marker pointedMarker = null;

    private List<LatLng> latLngList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MapSearchAdapter adapter;

    private MapFragmentState mapFragmentState = DEFAULT_MODE;

    // 검색했을 때 검새결과를 받을 리스트
    public static ArrayList<Place> placeList;

    //detail 박스에 포커싱된 장소
    private Place detailFocusedPlace;

    //레이아웃 변수
    private View layout;

    private ConstraintLayout autoCompleteTextView_search_wrapper;
    private SlidingUpPanelLayout mapSlidingLayout;
    private ConstraintLayout map_frag_detail_box_wrapper;
    private ConstraintLayout navi_button_wrapper;
    private ConstraintLayout AR_button_wrapper;

    //검색창을 구성하는 레이아웃
    private ImageView map_frag_back;
    private ImageView map_frag_cancel;
    private ImageView map_frag_search_icon;
    private EditText editText_search;

    // 슬라이딩 패널에 들어가는 spinner
    private Spinner map_frag_sliding_spinner;

    // 추가 기능 버튼
    private ImageView navi_button;
    private ImageView AR_button;


    // 디테일 박스

    private ConstraintLayout map_frag_detail_box;

    // 디테일 박스 구성 레이아웃
    private TextView map_frag_detail_title;
    private TextView map_frag_detail_sort;
    private TextView map_frag_detail_distance;

    //네비게이션 검색 박스
    private ConstraintLayout map_frag_navi_searchWrapper;
    private ImageView map_frag_navi_back;
    private EditText map_frag_navi_searchBar_Start;
    private EditText map_frag_navi_searchBar_End;
    private ImageView map_frag_navi_change;
    private TextView map_frag_navi_searchButton_now;

    //네비게이션 출발 목적지 저장 변수
    private int startPlaceID = 0;
    private int endPlaceID = 0;
    private LatLng startLocation = null;
    private LatLng endLocation = null;


    public MapFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //뒤로 가기 버튼 리스너
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                switch (mapFragmentState){

                    case DEFAULT_MODE:

                        if(editText_search.isFocused()){
                            editText_search.clearFocus();
                            editText_search.setText("");

                            map_frag_back.setVisibility(View.INVISIBLE);
                            map_frag_cancel.setVisibility(View.INVISIBLE);
                            map_frag_search_icon.setVisibility(View.VISIBLE);

                            hideKeyboard(layout);
                            gMap.clear();
                            floatingMarkersOverlay.clearMarkers();

                        }else{

                            System.exit(0);

                        }

                        break;


                    case SEARCH_MODE :
                    case DIRECTION_MODE:

                        setMapFragmentMode(DEFAULT_MODE,autoCompleteTextView_search_wrapper,
                                mapSlidingLayout, map_frag_detail_box_wrapper,map_frag_navi_searchWrapper,
                                navi_button_wrapper, AR_button_wrapper);

                        map_frag_back.setVisibility(View.INVISIBLE);
                        map_frag_cancel.setVisibility(View.INVISIBLE);
                        map_frag_search_icon.setVisibility(View.VISIBLE);


                        hideKeyboard(layout);
                        gMap.clear();
                        floatingMarkersOverlay.clearMarkers();

                        break;

                    case DETAIL_MODE :
                        setMapFragmentMode(SEARCH_MODE,autoCompleteTextView_search_wrapper,
                                mapSlidingLayout, map_frag_detail_box_wrapper, map_frag_navi_searchWrapper,
                                navi_button_wrapper, AR_button_wrapper);

                        if (pointedMarker != null) {
                            pointedMarker.remove();
                            pointedMarker = null;
                        }

                        break;


                }

            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.map_fragment, container, false);


        mapView = (MapView) layout.findViewById(R.id.map);
        mapView.getMapAsync(this);
        mapFragment = this;
        gpsTracker = new GPSTracker(getActivity());

        //map_fragment를 기본적으로 구성하는 레이아웃들
        autoCompleteTextView_search_wrapper = layout.findViewById(R.id.autoCompleteTextView_search_wrapper);
        mapSlidingLayout = layout.findViewById(R.id.map_sliding_layout);
        map_frag_detail_box_wrapper = layout.findViewById(R.id.map_frag_detail_box_wrapper);
        navi_button_wrapper = layout.findViewById(R.id.navi_button_wrapper);
        AR_button_wrapper = layout.findViewById(R.id.AR_button_wrapper);

        //검색창을 구성하는 레이아웃
        map_frag_back = layout.findViewById(R.id.map_frag_back);
        map_frag_cancel = layout.findViewById(R.id.map_frag_cancel);
        map_frag_search_icon = layout.findViewById(R.id.map_frag_search_icon);
        editText_search = layout.findViewById(R.id.editText_search);


        // 슬라이딩 패널에 들어가는 spinner
        map_frag_sliding_spinner = layout.findViewById(R.id.map_frag_sliding_spinner);

        // 추가 기능 버튼
        navi_button = layout.findViewById(R.id.navi_button);
        AR_button = layout.findViewById(R.id.AR_button);

        // 디테일 박스

        map_frag_detail_box = layout.findViewById(R.id.map_frag_detail_box);

        // 디테일 박스 구성 레이아웃
        map_frag_detail_title = layout.findViewById(R.id.map_frag_detail_title);
        map_frag_detail_sort = layout.findViewById(R.id.map_frag_detail_sort);
        map_frag_detail_distance = layout.findViewById(R.id.map_frag_detail_distance);

        //네이게이션 검색 박스 레이아웃
        map_frag_navi_searchWrapper = layout.findViewById(R.id.map_frag_navi_searchWrapper);
        map_frag_navi_back = layout.findViewById(R.id.map_frag_navi_back);
        map_frag_navi_searchBar_Start = layout.findViewById(R.id.map_frag_navi_searchBar_Start);
        map_frag_navi_searchBar_End = layout.findViewById(R.id.map_frag_navi_searchBar_End);
        map_frag_navi_change = layout.findViewById(R.id.map_frag_navi_change);
        map_frag_navi_searchButton_now = layout.findViewById(R.id.map_frag_navi_searchButton_now);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        //fetchLocation();


        //리사이클러뷰 설정
        recyclerView = (RecyclerView)layout.findViewById(R.id.map_frag_recyclerview_sliding);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)) ;
        placeList = new ArrayList<>();



        //선 넣는 코드
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getContext()).getOrientation());
        //recyclerView.addItemDecoration(dividerItemDecoration);

        placeList.add(new Place(1,"정보기술대학", "부속건물", 320, new LatLng(37.37428569643498, 126.63386849546436), "9:00 ~ 18:00", "032-832-1234"));
        placeList.add(new Place( 2,"공과·도시과학대학", "부속건물", 400 , new LatLng(37.37351897032315, 126.63275998245754), "9:00 ~ 18:00", "032-832-1234"));
        placeList.add(new Place(3, "공동실험 실습관", "부속건물", 500 , new LatLng(37.37269933308723, 126.63335830802647), "9:00 ~ 18:00", "032-832-1234"));
        placeList.add(new Place(1,"정보기술대학", "부속건물", 320 , new LatLng(37.37428569643498, 126.63386849546436), "9:00 ~ 18:00", "032-832-1234"));
        placeList.add(new Place( 2,"공과·도시과학대학", "부속건물", 400 , new LatLng(37.37351897032315, 126.63275998245754), "9:00 ~ 18:00", "032-832-1234"));
        placeList.add(new Place(3,"공동실험 실습관", "부속건물", 500 , new LatLng(37.37269933308723, 126.63335830802647), "9:00 ~ 18:00", "032-832-1234"));

        adapter = new MapSearchAdapter(placeList);
        recyclerView.setAdapter(adapter);

        // 검색 모드 슬라이딩 어댑터에 클릭 리스너 설정. 디테일 박스 모드로 넘어감.
        adapter.setOnItemClickListener(new MapSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                setMapFragmentMode(DETAIL_MODE, autoCompleteTextView_search_wrapper, mapSlidingLayout, map_frag_detail_box_wrapper,
                        map_frag_navi_searchWrapper, navi_button_wrapper, AR_button_wrapper);
                map_frag_detail_title.setText(placeList.get(position).getTitle());
                map_frag_detail_sort.setText(placeList.get(position).getSort());
                map_frag_detail_distance.setText((int)(placeList.get(position).getDistance()) + "m");

                detailFocusedPlace= placeList.get(position);

                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom( placeList.get(position).getLocation() , 17));

                if (pointedMarker != null) {
                    pointedMarker.remove();
                    pointedMarker = null;
                }

                pointedMarker = gMap.addMarker(new MarkerOptions().position(placeList.get(position).getLocation()));
                pointedMarker.setTag("pointedMarker");

            }

        });

        //스피너 설정
        String[] items = getResources().getStringArray(R.array.map_frag_sliding_spinner_list);
        map_frag_sliding_spinner.setAdapter(new ArrayAdapter(getContext(), R.layout.map_fragment_custom_spinner_item, items));

        //초기 모드 설정
        setMapFragmentMode(DEFAULT_MODE,autoCompleteTextView_search_wrapper, mapSlidingLayout, map_frag_detail_box_wrapper,
                map_frag_navi_searchWrapper, navi_button_wrapper, AR_button_wrapper);


        //검색창의 포커스 여부 설정
        editText_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    map_frag_back.setVisibility(View.VISIBLE);
                    map_frag_cancel.setVisibility(View.VISIBLE);
                    map_frag_search_icon.setVisibility(View.INVISIBLE);

                }
            }
        });


        //검색창 검색 버튼 누를때
        editText_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    map_frag_back.setVisibility(View.VISIBLE);
                    map_frag_cancel.setVisibility(View.INVISIBLE);

                    hideKeyboard(layout);

                    setMapFragmentMode(SEARCH_MODE,autoCompleteTextView_search_wrapper, mapSlidingLayout, map_frag_detail_box_wrapper,
                            map_frag_navi_searchWrapper, navi_button_wrapper, AR_button_wrapper);

                    gMap.clear();
                    floatingMarkersOverlay.clearMarkers();

                    if(placeList!=null && !placeList.isEmpty()){

                        for(int i=0; i<placeList.size(); i++){

                           /* MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(placeList.get(i).getLocation());
                            markerOptions.title(placeList.get(i).getTitle());


                            Marker marker = gMap.addMarker(markerOptions);
                            marker.setTag(i); // 마커를 눌렀을 때 정보를 표시하기 위한 태그 정보*/


                            int color = Color.parseColor("#02468E");
                            final float main_color_hsv = 211;
                            MarkerInfo mi = new MarkerInfo(placeList.get(i).getLocation(), placeList.get(i).getTitle(), color);

                            Marker marker = gMap.addMarker(new MarkerOptions().position(mi.getCoordinates()).icon(BitmapDescriptorFactory.defaultMarker(main_color_hsv)));

                            marker.setTag(i);

                            //Adding the marker to track by the overlay
                            //To remove that marker, you will need to call floatingMarkersOverlay.removeMarker(id)


                            floatingMarkersOverlay.addMarker(i, mi);

                        }

                    }

                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(placeList.get(0).getLocation().latitude - 0.0008,
                            placeList.get(0).getLocation().longitude) , 17));

                    //gMap.animateCamera(CameraUpdateFactory.newLatLngZoom( placeList.get(0).getLocation(), 17));




                }

                return false;
            }
        });


        //검색창 뒤로 가기 누를때
        map_frag_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                switch (mapFragmentState){

                    case DEFAULT_MODE:

                        if(editText_search.isFocused()){
                            editText_search.clearFocus();
                            editText_search.setText("");

                            map_frag_back.setVisibility(View.INVISIBLE);
                            map_frag_cancel.setVisibility(View.INVISIBLE);
                            map_frag_search_icon.setVisibility(View.VISIBLE);

                            hideKeyboard(layout);
                            gMap.clear();
                            floatingMarkersOverlay.clearMarkers();
                        }else{
                            System.exit(0);
                        }

                        break;

                    case DIRECTION_MODE:
                    case SEARCH_MODE :

                        setMapFragmentMode(DEFAULT_MODE,autoCompleteTextView_search_wrapper,
                                mapSlidingLayout, map_frag_detail_box_wrapper, map_frag_navi_searchWrapper,
                                navi_button_wrapper, AR_button_wrapper);

                        map_frag_back.setVisibility(View.INVISIBLE);
                        map_frag_cancel.setVisibility(View.INVISIBLE);
                        map_frag_search_icon.setVisibility(View.VISIBLE);

                        hideKeyboard(layout);
                        gMap.clear();
                        floatingMarkersOverlay.clearMarkers();

                        break;


                    case DETAIL_MODE :

                        setMapFragmentMode(SEARCH_MODE,autoCompleteTextView_search_wrapper,
                                mapSlidingLayout, map_frag_detail_box_wrapper, map_frag_navi_searchWrapper,
                                navi_button_wrapper, AR_button_wrapper);

                        if (pointedMarker != null) {
                            pointedMarker.remove();
                            pointedMarker = null;
                        }

                        break;

                }

            }
        });

        //검색창 클리어
        map_frag_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText_search.setText("");


            }
        });

        // 디테일 박스 리스너 설정

        ActivityResultLauncher<Intent> detailActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);


                            if(CallType == 2001) {

                            }

                        }

                    }
                });

        map_frag_detail_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MapDetailActivity.class);

                intent.putExtra("placeID", detailFocusedPlace.getPlaceID());

                detailActivityResultLauncher.launch(intent);

            }

        });


        //네비게이션 버튼 누를때
        navi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setMapFragmentMode(DIRECTION_MODE,autoCompleteTextView_search_wrapper, mapSlidingLayout, map_frag_detail_box_wrapper,
                        map_frag_navi_searchWrapper, navi_button_wrapper, AR_button_wrapper);

                gMap.clear();
                floatingMarkersOverlay.clearMarkers();

            }
        });

        //네비게이션 검색 박스 뒤로가기 버튼 누를때
        map_frag_navi_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setMapFragmentMode(DEFAULT_MODE,autoCompleteTextView_search_wrapper,
                        mapSlidingLayout, map_frag_detail_box_wrapper, map_frag_navi_searchWrapper,
                        navi_button_wrapper, AR_button_wrapper);


                map_frag_navi_searchBar_Start.setText("");
                map_frag_navi_searchBar_End.setText("");

                map_frag_back.setVisibility(View.INVISIBLE);
                map_frag_cancel.setVisibility(View.INVISIBLE);
                map_frag_search_icon.setVisibility(View.VISIBLE);

                hideKeyboard(layout);
                gMap.clear();
                floatingMarkersOverlay.clearMarkers();

            }
        });


        //네비게이션 검색 출발지 검색창 설정
        ActivityResultLauncher<Intent> mapNaviSearchActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {


                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);

                            switch (CallType){
                                case 1001:

                                    startPlaceID = intent.getIntExtra("startPlaceID", 0);
                                    String startPlaceTitle = intent.getStringExtra("startPlaceTitle");
                                    map_frag_navi_searchBar_Start.setText(startPlaceTitle);
                                    break;

                                case 1002:

                                    endPlaceID = intent.getIntExtra("endPlaceID", 0);
                                    String endPlaceTitle = intent.getStringExtra("endPlaceTitle");
                                    map_frag_navi_searchBar_End.setText(endPlaceTitle);
                                    break;

                                case 2001:

                                    map_frag_navi_searchBar_Start.setText("내 위치");

                                    startLocation = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());

                                    break;

                                case 2002:

                                    map_frag_navi_searchBar_End.setText("내 위치");

                                    endLocation = new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude());

                                    break;

                            }

                        }

                    }
                });

        map_frag_navi_searchBar_Start.setClickable(true);
        map_frag_navi_searchBar_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MapNaviSearchActivity.class);
                intent.putExtra("SearchSort", "출발지 검색");
                mapNaviSearchActivityResultLauncher.launch(intent);

            }
        });

        //네비게이션 검색 도착지 검색창 누를때
        map_frag_navi_searchBar_End.setClickable(true);
        map_frag_navi_searchBar_End.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapNaviSearchActivity.class);
                intent.putExtra("SearchSort", "도착지 검색");
                mapNaviSearchActivityResultLauncher.launch(intent);
            }
        });

        //네비게이션 출발지 목적지 변경 누를때
        map_frag_navi_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int tmpPlaceID = 0;

                tmpPlaceID = startPlaceID;
                startPlaceID = endPlaceID;
                endPlaceID = tmpPlaceID;

                String tmpPlaceTitle = map_frag_navi_searchBar_Start.getText().toString();
                map_frag_navi_searchBar_Start.setText(map_frag_navi_searchBar_End.getText().toString());
                map_frag_navi_searchBar_End.setText(tmpPlaceTitle);

            }
        });

        return layout;

    }


    //구형 위치 가져오기 메소드
  /*  public void fetchLocation() {

        if (ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }


        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getActivity(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    assert mapView != null;
                    mapView.getMapAsync(mapFragment);

                    Log.d("@@@MapFragment282" , currentLocation.getLatitude() + " " + currentLocation.getLongitude());

                    if(gMap != null){
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 17));
                    }

                }

            }

        });


    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);

        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            System.exit(0);
        }

        gMap.setMyLocationEnabled(true);

        gMap.getUiSettings().setMyLocationButtonEnabled(true);

        gMap.getUiSettings().setMapToolbarEnabled(false);

        // 마커 타이틀 오버레이
        floatingMarkersOverlay = layout.findViewById(R.id.map_floating_markers_overlay);
        floatingMarkersOverlay.setSource(googleMap);


        View locationButton = ((View) getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        View compassButton = ((View) getView().findViewWithTag("GoogleMapCompass"));
        RelativeLayout.LayoutParams rlpLocation = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        RelativeLayout.LayoutParams rlpCompass = (RelativeLayout.LayoutParams) compassButton.getLayoutParams();

        // position on right bottom
        rlpLocation.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlpLocation.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlpLocation.setMargins(0, 200, 180, 0);

        rlpCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlpCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlpCompass.setMargins(180, 200, 0, 0);

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                if(!marker.getTag().equals("pointedMarker")){

                    setMapFragmentMode(DETAIL_MODE, autoCompleteTextView_search_wrapper, mapSlidingLayout, map_frag_detail_box_wrapper,
                            map_frag_navi_searchWrapper, navi_button_wrapper, AR_button_wrapper);
                    map_frag_detail_title.setText(placeList.get((Integer.parseInt(marker.getTag().toString()))).getTitle());
                    map_frag_detail_sort.setText(placeList.get((Integer.parseInt(marker.getTag().toString()))).getSort());
                    map_frag_detail_distance.setText((int)(placeList.get((Integer.parseInt(marker.getTag().toString()))).getDistance()) + "m");

                    detailFocusedPlace = placeList.get(Integer.parseInt(marker.getTag().toString()));

                    if (pointedMarker != null) {
                        pointedMarker.remove();
                        pointedMarker = null;
                    }

                    pointedMarker = gMap.addMarker(new MarkerOptions().position(placeList.get((Integer.parseInt(marker.getTag().toString()))).getLocation()));
                    pointedMarker.setTag("pointedMarker");

                }

                return false;

            }
        });

        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(37.37532099190484, 126.63285407077159) , 17));

    }


    public void hideKeyboard(View layout){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(layout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void openKeyboard(View layout){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(layout.findViewById(R.id.editText_search), 0);
    }



    // custom methods

    // default mode(only search bar), search mode( search bar, slidingPanel), detail mode ( search bar, detail box),
    // direction mode ( direction bar , direction information box)


    public void setMapFragmentMode(MapFragmentState state, ConstraintLayout searchBar, SlidingUpPanelLayout slidingPanel, ConstraintLayout detailBox
                            ,ConstraintLayout map_frag_navi_searchWrapper ,ConstraintLayout naviButton, ConstraintLayout arButton){

        switch (state){

            case DEFAULT_MODE:

                mapFragmentState = DEFAULT_MODE;

                editText_search.clearFocus();
                editText_search.setText("");

                searchBar.setVisibility(View.VISIBLE);
                slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                detailBox.setVisibility(View.GONE);
                map_frag_navi_searchWrapper.setVisibility(View.GONE);

                naviButton.setVisibility(View.VISIBLE);
                arButton.setVisibility(View.VISIBLE);

                break;

            case SEARCH_MODE:

                mapFragmentState = SEARCH_MODE;
                searchBar.setVisibility(View.VISIBLE);
                slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                detailBox.setVisibility(View.GONE);
                map_frag_navi_searchWrapper.setVisibility(View.GONE);

                naviButton.setVisibility(View.VISIBLE);
                arButton.setVisibility(View.VISIBLE);

                break;

            case DETAIL_MODE:

                mapFragmentState = DETAIL_MODE;
                searchBar.setVisibility(View.VISIBLE);
                slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                detailBox.setVisibility(View.VISIBLE);
                map_frag_navi_searchWrapper.setVisibility(View.GONE);

                naviButton.setVisibility(View.VISIBLE);
                arButton.setVisibility(View.VISIBLE);

                break;

            case DIRECTION_MODE:

                map_frag_navi_searchBar_Start.setText("");
                map_frag_navi_searchBar_End.setText("");
                startPlaceID = 0;
                endPlaceID = 0;
                startLocation = null;
                endLocation = null;

                mapFragmentState = DIRECTION_MODE;
                searchBar.setVisibility(View.GONE);
                slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
                detailBox.setVisibility(View.GONE);
                map_frag_navi_searchWrapper.setVisibility(View.VISIBLE);

                naviButton.setVisibility(View.INVISIBLE);
                arButton.setVisibility(View.VISIBLE);
                break;


            default :

                searchBar.setVisibility(View.VISIBLE);
                slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                detailBox.setVisibility(View.INVISIBLE);

                naviButton.setVisibility(View.VISIBLE);
                arButton.setVisibility(View.VISIBLE);

                break;


        }

    }



}