package com.maru.inunavi.ui.timetable.search;

import static com.maru.inunavi.ui.timetable.search.SearchActivity.selectionGradeList;

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

public class SearchGradeActivity extends AppCompatActivity implements Serializable {

    RecyclerView recyclerView;
    SearchOptionAdapter adapter;
    private ArrayList<String> gradeList;

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

        gradeList = (ArrayList<String>)getIntent().getSerializableExtra("Grade");

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



        for (int i =0;i<gradeList.size();i++){
            SearchOptionAdapter.Item grade_Type = new SearchOptionAdapter.Item(SearchOptionAdapter.MULTI_CHILD, gradeList.get(i));
            data.add(grade_Type);
        }

        SearchOptionAdapter adapter =  new SearchOptionAdapter(data, selectionGradeList);

        recyclerView.setAdapter(adapter);



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

                Intent intent = new Intent(SearchGradeActivity.this, SearchOptionActivity.class);
                intent.putExtra("CallType", 1001);
                Collections.sort(selectionGradeList);
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(0, 0);


            }
        });

    }


}
