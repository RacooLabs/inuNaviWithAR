package com.maru.inunavi.ui.map;


import android.Manifest;
import android.content.Context;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
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
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
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

        String[] items = {"인문대학", "center", "infor"};

        mapView = (MapView) layout.findViewById(R.id.map);
        mapView.getMapAsync(this);
        mapFragment = this;

        TextView search_button = layout.findViewById(R.id.search_button);
        TextView clear_button = layout.findViewById(R.id.clear_button);
        TextView reset_button = layout.findViewById(R.id.reset_button);

        TextView now_navi_button = layout.findViewById(R.id.now_navi_button);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fetchLocation();

        AutoCompleteTextView autoCompleteTextView_search = layout.findViewById(R.id.autoCompleteTextView_search);


        autoCompleteTextView_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    search_button.setVisibility(View.INVISIBLE);
                    clear_button.setVisibility(View.VISIBLE);

                } else {
                    search_button.setVisibility(View.VISIBLE);
                    clear_button.setVisibility(View.INVISIBLE);

                }

                reset_button.setVisibility(View.INVISIBLE);
            }
        });


        autoCompleteTextView_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                Log.d("@@@", autoCompleteTextView_search.getText().toString());

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (autoCompleteTextView_search.getText().toString().equals("인문대학")) {

                        if (polyline != null) polyline.remove();

                        latLngList.clear();
                        gMap.clear();

                        latLngList.add(new LatLng(37.3747872226735, 126.63342072263077));
                        latLngList.add(new LatLng(37.375203052050516, 126.63380415078625));
                        latLngList.add(new LatLng(37.375773021330396, 126.63272604103146));
                        latLngList.add(new LatLng(37.37541813530646, 126.63237418931232));
                        latLngList.add(new LatLng(37.37556152380112, 126.63214864333851));

                        PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngList).clickable(true);
                        polyline = gMap.addPolyline(polylineOptions);

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(37.37556152380112, 126.63214864333851));
                        markerOptions.title("인문대학");

                        gMap.addMarker(markerOptions);

                        search_button.setVisibility(View.INVISIBLE);
                        clear_button.setVisibility(View.INVISIBLE);
                        reset_button.setVisibility(View.VISIBLE);

                    }

                    hideKeyboard(layout);

                }

                return false;
            }
        });


        search_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                autoCompleteTextView_search.requestFocus();
                openKeyboard(layout);

                MarkerOptions markerOptions = new MarkerOptions();

                LatLng SEOUL2 = new LatLng(37.37475843296176, 126.63338849213142);


                markerOptions.position(SEOUL2);

                markerOptions.title("정보대");

                markerOptions.snippet("본진");

                gMap.addMarker(markerOptions);

                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom( SEOUL2, 17));


            }
        });

        clear_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                autoCompleteTextView_search.clearFocus();
                hideKeyboard(layout);

            }
        });

        reset_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                search_button.setVisibility(View.VISIBLE);
                clear_button.setVisibility(View.INVISIBLE);
                reset_button.setVisibility(View.INVISIBLE);
                autoCompleteTextView_search.clearFocus();


                if (polyline != null) polyline.remove();
                latLngList.clear();


            }
        });


        autoCompleteTextView_search.setAdapter(new ArrayAdapter<String>(layout.getContext(),
                android.R.layout.simple_dropdown_item_1line, items));


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

        //MarkerOptions markerOptions = new MarkerOptions();


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            System.exit(0);
        }
        gMap.setMyLocationEnabled(true);

        gMap.getUiSettings().setMyLocationButtonEnabled(true);

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



    }

    public void hideKeyboard(View layout){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(layout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void openKeyboard(View layout){
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(layout.findViewById(R.id.autoCompleteTextView_search), 0);
    }

}