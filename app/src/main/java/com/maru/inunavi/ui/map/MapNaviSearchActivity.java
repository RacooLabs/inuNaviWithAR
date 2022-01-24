package com.maru.inunavi.ui.map;

import static com.maru.inunavi.ui.map.MapFragmentState.DETAIL_MODE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.map.MapDetailActivityAdapter;
import com.maru.inunavi.ui.map.Place;
import com.maru.inunavi.ui.timetable.search.SearchCSEActivity;
import com.maru.inunavi.ui.timetable.search.SearchOptionActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MapNaviSearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MapNaviSearchActivityAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment_navi_search);

        Intent intent = getIntent();
        String searchSort = getIntent().getStringExtra("SearchSort");

        // 레이아웃 바인딩
        ImageView map_frag_navi_search_back = findViewById(R.id.map_frag_navi_search_back);
        ConstraintLayout map_frag_navi_search_myLocation_wrapper = findViewById(R.id.map_frag_navi_search_myLocation_wrapper);
        ConstraintLayout map_frag_navi_search_pickInMap_wrapper = findViewById(R.id.map_frag_navi_search_pickInMap_wrapper);
        EditText map_frag_navi_search_searchBar = findViewById(R.id.map_frag_navi_search_searchBar);

        
        //돌아가기 버튼
        map_frag_navi_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        if(searchSort != null || !searchSort.equals("")){
            map_frag_navi_search_searchBar.setHint(searchSort);
        }


        recyclerView = findViewById(R.id.map_frag_navi_search_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)) ;


        ArrayList<Place> searchPlaceList = new ArrayList<Place>(Arrays.asList(
            new Place("INFORMATION","정보기술대학", "부속건물", 320, new LatLng(37.37428569643498, 126.63386849546436), "9:00 ~ 18:00", "0328321234"),
                new Place(  "ENGINEERING","공과·도시과학대학", "부속건물", 400 , new LatLng(37.37351897032315, 126.63275998245754), "9:00 ~ 18:00", "0328321234"),
                new Place("LABS", "공동실험 실습관", "부속건물", 500 , new LatLng(37.37269933308723, 126.63335830802647), "9:00 ~ 18:00", "0328321234"),
                new Place("INFORMATION","정보기술대학", "부속건물", 320, new LatLng(37.37428569643498, 126.63386849546436), "9:00 ~ 18:00", "0328321234"),
                new Place(  "ENGINEERING","공과·도시과학대학", "부속건물", 400 , new LatLng(37.37351897032315, 126.63275998245754), "9:00 ~ 18:00", "0328321234"),
                new Place("LABS", "공동실험 실습관", "부속건물", 500 , new LatLng(37.37269933308723, 126.63335830802647), "9:00 ~ 18:00", "0328321234")
        ));

        adapter = new MapNaviSearchActivityAdapter(searchPlaceList);
        recyclerView.setAdapter(adapter);


        //내 위치로 출발, 목적지 설정할 때
        map_frag_navi_search_myLocation_wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(searchSort.equals("출발지 검색")){
                    Intent intent = new Intent(MapNaviSearchActivity.this, MainActivity.class);
                    intent.putExtra("CallType", 2001);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);

                }else if(searchSort.equals("도착지 검색")){

                    Intent intent = new Intent(MapNaviSearchActivity.this, MainActivity.class);
                    intent.putExtra("CallType", 2002);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);

                }
            }
        });

        
        //지도에서 출발, 목적지 직접 고를때

        ActivityResultLauncher<Intent> pickLocationResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);
                            double latitude = intent.getDoubleExtra("latitude", 0);
                            double longitude = intent.getDoubleExtra("longitude", 0);

                            if(CallType == 1) {

                                if(searchSort.equals("출발지 검색")){
                                    Intent sendingIntent = new Intent(MapNaviSearchActivity.this, MainActivity.class);
                                    sendingIntent.putExtra("CallType", 3001);
                                    sendingIntent.putExtra("latitude",latitude);
                                    sendingIntent.putExtra("longitude",longitude);
                                    setResult(Activity.RESULT_OK, sendingIntent);
                                    finish();
                                    overridePendingTransition(0, 0);



                                }else if(searchSort.equals("도착지 검색")){


                                    Intent sendingIntent = new Intent(MapNaviSearchActivity.this, MainActivity.class);
                                    sendingIntent.putExtra("CallType", 3002);
                                    sendingIntent.putExtra("latitude",latitude);
                                    sendingIntent.putExtra("longitude",longitude);
                                    setResult(Activity.RESULT_OK, sendingIntent);
                                    finish();
                                    overridePendingTransition(0, 0);

                                }


                            }

                        }

                    }
                });

        map_frag_navi_search_pickInMap_wrapper.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MapNaviSearchActivity.this, MapPickLocationActivity.class);
                pickLocationResultLauncher.launch(intent);

            }

        });


        //리사이클러 뷰 클릭했을 때
        adapter.setOnItemClickListener(new MapNaviSearchActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                if(searchSort.equals("출발지 검색")){
                    Intent intent = new Intent(MapNaviSearchActivity.this, MainActivity.class);
                    intent.putExtra("CallType", 1001);
                    intent.putExtra("startPlaceCode", searchPlaceList.get(position).getPlaceCode());
                    intent.putExtra("startPlaceTitle", searchPlaceList.get(position).getTitle());
                    intent.putExtra("startLatitude", searchPlaceList.get(position).getLocation().latitude);
                    intent.putExtra("startLongitude", searchPlaceList.get(position).getLocation().longitude);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);

                }else if(searchSort.equals("도착지 검색")){

                    Intent intent = new Intent(MapNaviSearchActivity.this, MainActivity.class);
                    intent.putExtra("CallType", 1002);
                    intent.putExtra("endPlaceCode", searchPlaceList.get(position).getPlaceCode());
                    intent.putExtra("endPlaceTitle", searchPlaceList.get(position).getTitle());
                    intent.putExtra("endLatitude", searchPlaceList.get(position).getLocation().latitude);
                    intent.putExtra("endLongitude", searchPlaceList.get(position).getLocation().longitude);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);

                }
            }

        });

    }


}
