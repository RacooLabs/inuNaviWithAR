package com.maru.inunavi.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.map.MapDetailActivityAdapter;
import com.maru.inunavi.ui.map.Place;

import java.util.ArrayList;
import java.util.Arrays;

public class MapNaviSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MapNaviSearchActivityAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment_navi_search);




        // 레이아웃 바인딩
        ImageView map_frag_navi_search_back = findViewById(R.id.map_frag_navi_search_back);
        ConstraintLayout map_frag_navi_search_myLocation_wrapper = findViewById(R.id.map_frag_navi_search_myLocation_wrapper);
        ConstraintLayout map_frag_navi_search_pickInMap_wrapper = findViewById(R.id.map_frag_navi_search_pickInMap_wrapper);


        
        //돌아가기 버튼
        map_frag_navi_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        recyclerView = findViewById(R.id.map_frag_navi_search_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)) ;


        ArrayList<Place> nearPlaceList = new ArrayList<Place>(Arrays.asList(
            new Place(1,"정보기술대학", "부속건물", 320, new LatLng(37.37428569643498, 126.63386849546436), "9:00 ~ 18:00", "032-832-1234"),
            new Place( 2,"공과·도시과학대학", "부속건물", 400 , new LatLng(37.37351897032315, 126.63275998245754), "9:00 ~ 18:00", "032-832-1234"),
            new Place(3, "공동실험 실습관", "부속건물", 500 , new LatLng(37.37269933308723, 126.63335830802647), "9:00 ~ 18:00", "032-832-1234"),
            new Place(1,"정보기술대학", "부속건물", 320 , new LatLng(37.37428569643498, 126.63386849546436), "9:00 ~ 18:00", "032-832-1234"),
            new Place( 2,"공과·도시과학대학", "부속건물", 400 , new LatLng(37.37351897032315, 126.63275998245754), "9:00 ~ 18:00", "032-832-1234"),
            new Place(3,"공동실험 실습관", "부속건물", 500 , new LatLng(37.37269933308723, 126.63335830802647), "9:00 ~ 18:00", "032-832-1234")

        ));

        adapter = new MapNaviSearchActivityAdapter(nearPlaceList);
        recyclerView.setAdapter(adapter);

    }


}
