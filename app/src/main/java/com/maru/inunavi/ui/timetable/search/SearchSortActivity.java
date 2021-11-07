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

public class SearchSortActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_search_option_single);

        //Topbar
        Intent intent = getIntent();
        String topBarTitle = intent.getStringExtra("topBarTitle");
        TextView tita_search_option_topBar_textView = findViewById(R.id.tita_search_option_topBar_textView);
        tita_search_option_topBar_textView.setText(topBarTitle);

        ImageView tita_search_option_single_backButton = findViewById(R.id.tita_search_option_single_backButton);

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



        ArrayList<String> sortTypeList = new ArrayList<String>(Arrays.asList("기본", "과목코드", "과목명" ));

        for (int i =0;i<sortTypeList.size();i++){
            SearchAdapter.Item sort_Type = new SearchAdapter.Item(SearchAdapter.DEFAULT_CHILD, sortTypeList.get(i));
            data.add(sort_Type);
        }

        SearchAdapter adapter =  new SearchAdapter(data);

        recyclerView.setAdapter(adapter);


        //어댑터 콜백 리스너
        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(SearchSortActivity.this, SearchOptionActivity.class);
                intent.putExtra("CallType", 1001);
                intent.putExtra("Sort", ((TextView)v).getText());
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

        }) ;



    }

}
