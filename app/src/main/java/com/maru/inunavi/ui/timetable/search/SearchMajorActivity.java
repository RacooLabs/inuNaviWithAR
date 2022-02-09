package com.maru.inunavi.ui.timetable.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.CalendarFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchMajorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchOptionAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_search_option_single);

        ImageView tita_search_option_single_backButton = findViewById(R.id.tita_search_option_single_backButton);


        //Topbar
        Intent intent = getIntent();
        String topBarTitle = intent.getStringExtra("topBarTitle");
        TextView tita_search_option_topBar_textView = findViewById(R.id.tita_search_option_topBar_textView);
        tita_search_option_topBar_textView.setText(topBarTitle);

        //돌아가기 버튼
        tita_search_option_single_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        recyclerView = findViewById(R.id.single_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<SearchOptionAdapter.Item> data = new ArrayList<>();

        //전공
        ArrayList<String> majorArray = CalendarFragment.majorArray;


        /*SearchAdapter.Item major = new SearchAdapter.Item(SearchAdapter.HEADER, "전공");
        major.invisibleChildren = new ArrayList<>();*/


        for (int i =0;i<majorArray.size();i++){
            SearchOptionAdapter.Item major = new SearchOptionAdapter.Item(SearchOptionAdapter.DEFAULT_CHILD, majorArray.get(i));
            data.add(major);
        }

        SearchOptionAdapter adapter =  new SearchOptionAdapter(data);

        recyclerView.setAdapter(adapter);


        //어댑터 콜백 리스너
        adapter.setOnItemClickListener(new SearchOptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(SearchMajorActivity.this, SearchOptionActivity.class);
                intent.putExtra("CallType", 1001);
                intent.putExtra("Major", ((TextView)v).getText());
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(0, 0);

            }

        }) ;



    }

}
