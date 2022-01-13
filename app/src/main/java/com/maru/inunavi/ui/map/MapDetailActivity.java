package com.maru.inunavi.ui.map;

import static com.maru.inunavi.ui.map.MapFragment.placeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.SettingAdapter;

import java.util.ArrayList;

public class MapDetailActivity extends AppCompatActivity {


    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment_detail_activity);


        ImageView map_frag_detail_activity_backButton = findViewById(R.id.map_frag_detail_activity_backButton);

        int placeID = getIntent().getIntExtra("placeID", 0);

        //돌아가기 버튼
        map_frag_detail_activity_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


}
