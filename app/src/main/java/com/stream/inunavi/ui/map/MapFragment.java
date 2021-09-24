package com.stream.inunavi.ui.map;


import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.MapView;
import com.stream.inunavi.R;

import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback  {

    private MapView mapView = null;
    

    public MapFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_map, container, false);

        String[] items = { "information", "center", "infor" };


        mapView = (MapView)layout.findViewById(R.id.map);
        mapView.getMapAsync(this);

        Button search_button = layout.findViewById(R.id.search_button);
        Button clear_button = layout.findViewById(R.id.clear_button);

        AutoCompleteTextView autoCompleteTextView_search = layout.findViewById(R.id.autoCompleteTextView_search);


        autoCompleteTextView_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    search_button.setVisibility(View.INVISIBLE);
                    clear_button.setVisibility(View.VISIBLE);
                }else{
                    search_button.setVisibility(View.VISIBLE);
                    clear_button.setVisibility(View.INVISIBLE);
                }
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                autoCompleteTextView_search.requestFocus();
                openKeyboard(layout);

            }
        });

        clear_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                autoCompleteTextView_search.clearFocus();
                hideKeyboard(layout);

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

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MarkerOptions markerOptions = new MarkerOptions();

        LatLng SEOUL = new LatLng(37.376021543103455, 126.63445596491341);

        LatLng SEOUL2 = new LatLng(37.37475843296176, 126.63338849213142);



        markerOptions.position(SEOUL);

        markerOptions.title("정보대");

        markerOptions.snippet("본진");

        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));

        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));



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