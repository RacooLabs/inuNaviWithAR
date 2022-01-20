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

public class SearchScoreActivity extends AppCompatActivity implements Serializable {

    RecyclerView recyclerView;
    SearchOptionAdapter adapter;
    private ArrayList<String> scoreList;

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

        scoreList = (ArrayList<String>)getIntent().getSerializableExtra("Score");

        //돌아가기 버튼
        tita_search_option_multi_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });


        recyclerView = findViewById(R.id.multi_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<SearchOptionAdapter.Item> data = new ArrayList<>();

        ArrayList<String> scoreTypeList = new ArrayList<String>(Arrays.asList("1학점","2학점","3학점","4학점"));

        for (int i =0;i<scoreTypeList.size();i++){
            SearchOptionAdapter.Item score_Type = new SearchOptionAdapter.Item(SearchOptionAdapter.MULTI_CHILD, scoreTypeList.get(i));
            data.add(score_Type);
        }

        SearchOptionAdapter adapter =  new SearchOptionAdapter(data, scoreList);

        recyclerView.setAdapter(adapter);


        //어댑터 콜백 리스너
        adapter.setOnItemClickListener(new SearchOptionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(SearchScoreActivity.this, SearchActivity.class);
                intent.putExtra("CallType", 1001);
                intent.putExtra("Score", ((TextView)v).getText());
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(0, 0);

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

                Intent intent = new Intent(SearchScoreActivity.this, SearchOptionActivity.class);
                intent.putExtra("CallType", 1001);
                Collections.sort(scoreList);
                intent.putExtra("Score", scoreList);
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(0, 0);

            }
        });

    }


}
