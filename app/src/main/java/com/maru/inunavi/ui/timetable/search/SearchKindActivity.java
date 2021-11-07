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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SearchKindActivity extends AppCompatActivity implements Serializable {

    RecyclerView recyclerView;
    SearchAdapter adapter;
    private ArrayList<String> kindList;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_search_option_multi);

        ImageView tita_search_option_multi_backButton = findViewById(R.id.tita_search_option_multi_backButton);
        TextView tita_search_option_multi_allCheck = findViewById(R.id.tita_search_option_multi_allCheck);
        TextView tita_search_option_multi_allCancel = findViewById(R.id.tita_search_option_multi_allCancel);
        TextView tita_search_option_multi_submit = findViewById(R.id.tita_search_option_multi_submit);


        //Topbar
        Intent intent = getIntent();
        String topBarTitle = intent.getStringExtra("topBarTitle");
        TextView tita_search_option_multi_topBar_textView = findViewById(R.id.tita_search_option_multi_topBar_textView);
        tita_search_option_multi_topBar_textView.setText(topBarTitle);

        kindList = (ArrayList<String>)getIntent().getSerializableExtra("Kind");

        //돌아가기 버튼
        tita_search_option_multi_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recyclerView = findViewById(R.id.multi_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<SearchAdapter.Item> data = new ArrayList<>();

        ArrayList<String> kindTypeList = new ArrayList<String>(Arrays.asList("전공선택", "전공기초", "전공필수", "교양필수","기초과학","교양선택", "교직", "일반선택",
                "군사학"));

        for (int i =0;i<kindTypeList.size();i++){
            SearchAdapter.Item kind_Type = new SearchAdapter.Item(SearchAdapter.MULTI_CHILD, kindTypeList.get(i));
            data.add(kind_Type);
        }

        SearchAdapter adapter =  new SearchAdapter(data, kindList);

        recyclerView.setAdapter(adapter);


        //어댑터 콜백 리스너
        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(SearchKindActivity.this, SearchActivity.class);
                intent.putExtra("CallType", 1001);
                intent.putExtra("Kind", ((TextView)v).getText());
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

        });

        tita_search_option_multi_allCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.doAllCheck();

            }
        });

        tita_search_option_multi_allCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.doAllCancel();

            }
        });


        //확인 버튼

        tita_search_option_multi_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SearchKindActivity.this, SearchOptionActivity.class);
                intent.putExtra("CallType", 1001);
                Collections.sort(kindList);
                intent.putExtra("Kind", kindList);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });

    }


}
