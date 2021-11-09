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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchCSEActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchAdapter adapter;

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
            }
        });

        recyclerView = findViewById(R.id.single_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<SearchAdapter.Item> data = new ArrayList<>();

        //전공
        ArrayList<String> CSEArray = new ArrayList<String>(Arrays.asList("대학영어2", "Academic English", "컴퓨팅적사고와SW", "글쓰기이론과실제",
                "대학영어회화2","기타" ));

        for (int i =0;i<CSEArray.size();i++){
            SearchAdapter.Item major = new SearchAdapter.Item(SearchAdapter.DEFAULT_CHILD, CSEArray.get(i));
            data.add(major);
        }

        SearchAdapter adapter =  new SearchAdapter(data);

        recyclerView.setAdapter(adapter);


        //어댑터 콜백 리스너
        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(SearchCSEActivity.this, SearchOptionActivity.class);
                intent.putExtra("CallType", 1001);
                intent.putExtra("CSE", ((TextView)v).getText());
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

        }) ;

    }

}
