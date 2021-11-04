package com.maru.inunavi.ui.timetable.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.maru.inunavi.R;

public class SearchOptionActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_search_option);

        ImageView tita_search_option_backButton = findViewById(R.id.tita_search_option_backButton);

        LinearLayout tita_search_option_major = findViewById(R.id.tita_search_option_major);
        LinearLayout tita_search_option_sort = findViewById(R.id.tita_search_option_sort);
        LinearLayout tita_search_option_grade = findViewById(R.id.tita_search_option_grade);
        LinearLayout tita_search_option_kind = findViewById(R.id.tita_search_option_kind);
        LinearLayout tita_search_option_score = findViewById(R.id.tita_search_option_score);

        TextView tita_search_option_major_text = findViewById(R.id.tita_search_option_major_text);
        TextView tita_search_option_sort_text = findViewById(R.id.tita_search_option_sort_text);
        TextView tita_search_option_major_grade = findViewById(R.id.tita_search_option_major_grade);
        TextView tita_search_option_major_kind = findViewById(R.id.tita_search_option_major_kind);
        TextView tita_search_option_major_score = findViewById(R.id.tita_search_option_major_score);

        //돌아가기 버튼
        tita_search_option_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  { finish(); }
        });

        //전공 콜백 리스너
        ActivityResultLauncher<Intent> majorActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);
                            String major = intent.getStringExtra("Major");

                            tita_search_option_major_text.setText(major);

                        }
                    }
                });


        tita_search_option_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchMajorActivity.class);
                majorActivityResultLauncher.launch(intent);

            }
        });



        //정렬 콜백 리스너
        ActivityResultLauncher<Intent> sortActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);
                            String sortType = intent.getStringExtra("Sort");

                            tita_search_option_sort_text.setText(sortType);

                        }
                    }
                });

        tita_search_option_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchSortActivity.class);
                sortActivityResultLauncher.launch(intent);
            }
        });


        
        

    }


}
