package com.maru.inunavi.ui.map;


import android.Manifest;
import android.content.Context;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
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
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapFragment extends Fragment implements OnMapReadyCallback {


    private MapView mapView = null;
    private GoogleMap gMap;
    private Polyline polyline = null;
    private MapFragment mapFragment= null;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private Location currentLocation;

    private List<LatLng> latLngList = new ArrayList<>();

    RecyclerView recyclerView;
    MapSearchAdapter adapter;


    public MapFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.map_fragment, container, false);


        mapView = (MapView) layout.findViewById(R.id.map);
        mapView.getMapAsync(this);
        mapFragment = this;

        ImageView now_navi_button = layout.findViewById(R.id.now_navi_button);

        ImageView map_frag_back = layout.findViewById(R.id.map_frag_back);
        ImageView map_frag_cancel = layout.findViewById(R.id.map_frag_cancel);
        ImageView map_frag_search_icon = layout.findViewById(R.id.map_frag_search_icon);
        Spinner map_frag_sliding_spinner = layout.findViewById(R.id.map_frag_sliding_spinner);

        EditText editText_search = layout.findViewById(R.id.editText_search);
        SlidingUpPanelLayout mapSlidingLayout = layout.findViewById(R.id.map_sliding_layout);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fetchLocation();

        //리사이클러뷰 설정
        recyclerView = (RecyclerView)layout.findViewById(R.id.map_frag_recyclerview_sliding);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)) ;
        ArrayList<Place> placeList = new ArrayList<>();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //스피너 설정
        String[] items = getResources().getStringArray(R.array.map_frag_sliding_spinner_list);
        map_frag_sliding_spinner.setAdapter(new ArrayAdapter(getContext(), R.layout.map_fragment_custom_spinner_item, items));


        placeList.add(new Place("정보기술대학", 7, 320, new LatLng(37.37428569643498, 126.63386849546436)));
        placeList.add(new Place( "공과·도시과학대학", 8, 400 , new LatLng(37.37351897032315, 126.63275998245754)));
        placeList.add(new Place("공동실험 실습관", 9, 500 , new LatLng(37.37269933308723, 126.63335830802647)));
        placeList.add(new Place("정보기술대학", 7, 320 , new LatLng(37.37428569643498, 126.63386849546436)));
        placeList.add(new Place( "공과·도시과학대학", 8, 400 , new LatLng(37.37351897032315, 126.63275998245754)));
        placeList.add(new Place("공동실험 실습관", 9, 500 , new LatLng(37.37269933308723, 126.63335830802647)));

        adapter = new MapSearchAdapter(placeList);
        recyclerView.setAdapter(adapter);

        //검색 결과 창 숨김.
        mapSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);


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

                    mapSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

                    gMap.clear();

                    if(placeList!=null && !placeList.isEmpty()){

                        for(int i=0; i<placeList.size(); i++){

                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(placeList.get(i).getLocation());
                            markerOptions.title(placeList.get(i).getTitle());

                            Marker marker = gMap.addMarker(markerOptions);
                            marker.setTag(i + "번째 마커");

                            //markerOptions.icon()

                        }


                    }

                }

                return false;
            }
        });



        //검색창 뒤로 가기 누를때
        map_frag_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                editText_search.clearFocus();
                editText_search.setText("");

                map_frag_back.setVisibility(View.INVISIBLE);
                map_frag_cancel.setVisibility(View.INVISIBLE);
                map_frag_search_icon.setVisibility(View.VISIBLE);

                hideKeyboard(layout);
                mapSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);

                gMap.clear();


            }
        });

        //검색창 클리어
        map_frag_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText_search.setText("");


            }
        });


        now_navi_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fetchLocation();

                gMap.clear();

                latLngList.clear();

                if(currentLocation != null){
                    latLngList.add(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                }

                latLngList.add(new LatLng(37.37635285186897, 126.63429909872082));
                latLngList.add(new LatLng(37.3762381423054, 126.6344930682583));
                latLngList.add(new LatLng(37.375298951155386, 126.63361343896047));
                latLngList.add(new LatLng(37.374571249163154, 126.63295935563643));
                latLngList.add(new LatLng(37.374427858775405, 126.63319843436867));

                PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngList).clickable(true);
                polyline = gMap.addPolyline(polylineOptions);

                MarkerOptions markerOptions = new MarkerOptions();

                LatLng dest = new LatLng(37.374427858775405, 126.63319843436867);

                markerOptions.position(dest);

                markerOptions.title("정보대");

                markerOptions.snippet("본진");

                gMap.addMarker(markerOptions);

                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom( dest , 17));

            }
        });

        return layout;

    }

    private void fetchLocation() {

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
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 17));

                }
            }
        });

    }


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
            fetchLocation();
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

                Toast.makeText(getActivity(), marker.getTag() + "" + marker.getTitle(), Toast.LENGTH_SHORT).show();


                return false;
            }
        });



    }

    public void hideKeyboard(View layout){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(layout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void openKeyboard(View layout){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(layout.findViewById(R.id.editText_search), 0);
    }

}