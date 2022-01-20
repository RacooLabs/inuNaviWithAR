package com.maru.inunavi.ui.map;

import static com.maru.inunavi.ui.map.MapFragment.placeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.recommend.RecommendAdapter;
import com.maru.inunavi.ui.timetable.SettingAdapter;
import com.maru.inunavi.ui.timetable.search.Lecture;

import java.util.ArrayList;
import java.util.Arrays;

public class MapDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MapDetailActivityAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment_detail_activity);


        ImageView map_frag_detail_activity_backButton = findViewById(R.id.map_frag_detail_activity_backButton);

        Intent intent = getIntent();

        int placeID = intent.getIntExtra("placeID",0);

        // 레이아웃 바인딩
        TextView map_frag_detail_activity_title = findViewById(R.id.map_frag_detail_activity_title);
        TextView map_frag_detail_activity_sort = findViewById(R.id.map_frag_detail_activity_sort);
        TextView map_frag_detail_activity_time = findViewById(R.id.map_frag_detail_activity_time);
        TextView map_frag_detail_activity_callNum = findViewById(R.id.map_frag_detail_activity_callNum);

        
        //돌아가기 버튼
        map_frag_detail_activity_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        
        // 장소 정보 출력
        map_frag_detail_activity_title.setText(placeList.get(placeID).getTitle());
        map_frag_detail_activity_sort.setText(placeList.get(placeID).getSort());
        map_frag_detail_activity_time.setText(placeList.get(placeID).getTime());
        map_frag_detail_activity_callNum.setText(placeList.get(placeID).getCallNum());

        recyclerView = findViewById(R.id.map_frag_detail_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)) ;


        ArrayList<Place> nearPlaceList = new ArrayList<Place>(Arrays.asList(
            new Place(1,"정보기술대학", "부속건물", 320, new LatLng(37.37428569643498, 126.63386849546436), "9:00 ~ 18:00", "032-832-1234"),
            new Place( 2,"공과·도시과학대학", "부속건물", 400 , new LatLng(37.37351897032315, 126.63275998245754), "9:00 ~ 18:00", "032-832-1234"),
            new Place(3, "공동실험 실습관", "부속건물", 500 , new LatLng(37.37269933308723, 126.63335830802647), "9:00 ~ 18:00", "032-832-1234"),
            new Place(1,"정보기술대학", "부속건물", 320 , new LatLng(37.37428569643498, 126.63386849546436), "9:00 ~ 18:00", "032-832-1234"),
            new Place( 2,"공과·도시과학대학", "부속건물", 400 , new LatLng(37.37351897032315, 126.63275998245754), "9:00 ~ 18:00", "032-832-1234"),
            new Place(3,"공동실험 실습관", "부속건물", 500 , new LatLng(37.37269933308723, 126.63335830802647), "9:00 ~ 18:00", "032-832-1234")

        ));


        adapter = new MapDetailActivityAdapter(nearPlaceList);
        recyclerView.setAdapter(adapter);

    }


}
