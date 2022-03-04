package com.maru.inunavi.ui.timetable.search;

import static com.maru.inunavi.ui.timetable.search.SearchActivity.selectionCategoryList;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchCategoryActivity extends AppCompatActivity implements Serializable {

    RecyclerView recyclerView;
    private ArrayList<String> categoryList;

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

        ArrayList<String> categoryList = CalendarFragment.categoryList;

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


        for (int i =0;i<categoryList.size();i++){
            SearchOptionAdapter.Item category_Type = new SearchOptionAdapter.Item(SearchOptionAdapter.MULTI_CHILD, categoryList.get(i));
            data.add(category_Type);
        }

        SearchOptionAdapter adapter =  new SearchOptionAdapter(data, selectionCategoryList);

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

                Intent intent = new Intent(SearchCategoryActivity.this, SearchOptionActivity.class);
                intent.putExtra("CallType", 1001);
                Collections.sort(selectionCategoryList);
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(0, 0);


            }
        });

    }


}
