package com.maru.inunavi.ui.map;


import android.Manifest;
import android.content.Context;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.maru.inunavi.R;

import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private boolean isfinding = false;

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

        View layout = inflater.inflate(R.layout.fragment_map, container, false);

        String[] items = {"information", "center", "infor"};

        mapView = (MapView) layout.findViewById(R.id.map);
        mapView.getMapAsync(this);

        Button search_button = layout.findViewById(R.id.search_button);
        Button clear_button = layout.findViewById(R.id.clear_button);
        Button reset_button = layout.findViewById(R.id.reset_button);

        AutoCompleteTextView autoCompleteTextView_search = layout.findViewById(R.id.autoCompleteTextView_search);


        autoCompleteTextView_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    search_button.setVisibility(View.INVISIBLE);
                    clear_button.setVisibility(View.VISIBLE);
                    reset_button.setVisibility(View.INVISIBLE);

                } else {
                    search_button.setVisibility(View.VISIBLE);
                    clear_button.setVisibility(View.INVISIBLE);
                    reset_button.setVisibility(View.INVISIBLE);

                }
            }
        });


        autoCompleteTextView_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                Log.d("@@@", autoCompleteTextView_search.getText().toString());

                if(actionId == EditorInfo.IME_ACTION_SEARCH){

                    if(autoCompleteTextView_search.getText().toString().equals("information")){

                        if (polyline != null) polyline.remove();
                        latLngList.clear();

                        latLngList.add(new LatLng(37.376001886626796, 126.6344533811601));
                        latLngList.add(new LatLng(37.376086126745015, 126.6343383527122));
                        latLngList.add(new LatLng(37.37574020395405, 126.63402935472476));
                        latLngList.add(new LatLng(37.37530466159084, 126.63364592656517));
                        latLngList.add(new LatLng(37.37612197357511, 126.63360532828945));

                        PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngList).clickable(true);
                        polyline = gMap.addPolyline(polylineOptions);

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

                gMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL2));

                gMap.animateCamera(CameraUpdateFactory.zoomTo(20));


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

                if (polyline != null) polyline.remove();
                latLngList.clear();


            }
        });



        autoCompleteTextView_search.setAdapter(new ArrayAdapter<String>(layout.getContext(),
                android.R.layout.simple_dropdown_item_1line, items));


        return layout;
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
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;

        MarkerOptions markerOptions = new MarkerOptions();

        LatLng SEOUL = new LatLng(37.376021543103455, 126.63445596491341);

        LatLng SEOUL2 = new LatLng(37.37475843296176, 126.63338849213142);


        markerOptions.position(SEOUL);

        markerOptions.title("정보대");

        markerOptions.snippet("본진");

        gMap.addMarker(markerOptions);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));

        gMap.animateCamera(CameraUpdateFactory.zoomTo(13));

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