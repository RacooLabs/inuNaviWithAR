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

import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.SettingAdapter;

import java.util.ArrayList;

public class MapDetailActivity extends AppCompatActivity {


    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment_detail_activity);


        ImageView map_frag_detail_activity_backButton = findViewById(R.id.map_frag_detail_activity_backButton);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String sort = intent.getStringExtra("sort");
        String time = intent.getStringExtra("time");
        String callNum = intent.getStringExtra("callNum");


        // 레이아웃 바인딩
        TextView map_frag_detail_activity_title = findViewById(R.id.map_frag_detail_activity_title);
        TextView map_frag_detail_activity_sort = findViewById(R.id.map_frag_detail_activity_sort);
        TextView map_frag_detail_activity_time = findViewById(R.id.map_frag_detail_activity_time);
        TextView map_frag_detail_activity_callNum = findViewById(R.id.map_frag_detail_activity_callNum);

        map_frag_detail_activity_title.setText(title);
        map_frag_detail_activity_sort.setText(sort);
        map_frag_detail_activity_time.setText(time);
        map_frag_detail_activity_callNum.setText(callNum);

        //돌아가기 버튼
        map_frag_detail_activity_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}
