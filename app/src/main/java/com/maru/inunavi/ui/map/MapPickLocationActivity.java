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
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.map.markerinfo.FloatingMarkerTitlesOverlay;
import com.maru.inunavi.ui.map.markerinfo.MarkerInfo;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;


public class MapPickLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView = null;
    private GoogleMap gMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_activity_pick_location);

        mapView = findViewById(R.id.map_pick_location);


        if (mapView != null) {
            mapView.onCreate(savedInstanceState);

        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.gMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            System.exit(0);
        }

        /*gMap.setMyLocationEnabled(true);

        gMap.getUiSettings().setMyLocationButtonEnabled(true);

        gMap.getUiSettings().setMapToolbarEnabled(false);

        View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        View compassButton = ((View) mapView.findViewWithTag("GoogleMapCompass"));
        RelativeLayout.LayoutParams rlpLocation = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        RelativeLayout.LayoutParams rlpCompass = (RelativeLayout.LayoutParams) compassButton.getLayoutParams();

        // position on right bottom
        rlpLocation.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlpLocation.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlpLocation.setMargins(0, 200, 180, 0);

        rlpCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlpCompass.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlpCompass.setMargins(180, 200, 0, 0);*/


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





}