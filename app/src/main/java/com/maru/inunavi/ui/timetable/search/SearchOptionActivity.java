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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchOptionActivity extends AppCompatActivity implements Serializable {

    ArrayList<String> gradeList = new ArrayList<String>(Arrays.asList("1학년", "2학년", "3학년", "4학년" ));

    ArrayList<String> kindList = new ArrayList<String>(Arrays.asList("전공선택", "전공기초", "전공필수", "교양필수","기초과학","교양선택", "교직", "일반선택",
            "군사학"));

    ArrayList<String> scoreList = new ArrayList<String>(Arrays.asList("1학점", "2학점", "3학점", "4학점"));

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_search_option);

        ImageView tita_search_option_backButton = findViewById(R.id.tita_search_option_backButton);

        LinearLayout tita_search_option_major = findViewById(R.id.tita_search_option_major);
        LinearLayout tita_search_option_sort = findViewById(R.id.tita_search_option_sort);
        LinearLayout tita_search_option_grade_layout = findViewById(R.id.tita_search_option_grade_layout);
        LinearLayout tita_search_option_kind_layout = findViewById(R.id.tita_search_option_kind_layout);
        LinearLayout tita_search_option_score_layout = findViewById(R.id.tita_search_option_score_layout);

        TextView tita_search_option_major_text = findViewById(R.id.tita_search_option_major_text);
        TextView tita_search_option_sort_text = findViewById(R.id.tita_search_option_sort_text);
        TextView tita_search_option_grade_text = findViewById(R.id.tita_search_option_grade_text);
        TextView tita_search_option_kind_text = findViewById(R.id.tita_search_option_kind_text);
        TextView tita_search_option_score_text = findViewById(R.id.tita_search_option_score_text);

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
                intent.putExtra("topBarTitle", "전공/영역");
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
                intent.putExtra("topBarTitle", "정렬");
                sortActivityResultLauncher.launch(intent);
            }
        });

        //학년 콜백 리스너
        ActivityResultLauncher<Intent> gradeActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent intent = result.getData();
                            int CallType = intent.getIntExtra("CallType", 0);
                            gradeList = (ArrayList<String>)intent.getSerializableExtra("Grade");

                            if(gradeList.size() > 0){

                                StringBuilder sb = new StringBuilder();

                                int i;

                                for(i=0;i<gradeList.size()-1;i++){
                                    sb.append(gradeList.get(i));
                                    sb.append(", ");
                                }

                                sb.append(gradeList.get(i));

                                tita_search_option_grade_text.setText(sb);

                            }else{

                                tita_search_option_grade_text.setText("전체");

                            }

                        }
                    }
                });

        tita_search_option_grade_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchGradeActivity.class);
                intent.putExtra("topBarTitle", "학년");
                intent.putExtra("Grade", gradeList);
                gradeActivityResultLauncher.launch(intent);
            }
        });

        //구분 콜백 리스너
        ActivityResultLauncher<Intent> kindActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent intent = result.getData();
                            int CallType = intent.getIntExtra("CallType", 0);
                            kindList = (ArrayList<String>)intent.getSerializableExtra("Kind");

                            if(kindList.size() > 0){

                                StringBuilder sb = new StringBuilder();

                                int i;

                                for(i=0;i<kindList.size()-1;i++){
                                    sb.append(kindList.get(i));
                                    sb.append(", ");
                                }

                                sb.append(kindList.get(i));

                                tita_search_option_kind_text.setText(sb);

                            }else{

                                tita_search_option_kind_text.setText("전체");

                            }

                        }
                    }
                });

        tita_search_option_kind_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchKindActivity.class);
                intent.putExtra("topBarTitle", "구분");
                intent.putExtra("Kind", kindList);
                kindActivityResultLauncher.launch(intent);
            }
        });


        //학점 콜백 리스너
        ActivityResultLauncher<Intent> scoreActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent intent = result.getData();
                            int CallType = intent.getIntExtra("CallType", 0);
                            scoreList = (ArrayList<String>)intent.getSerializableExtra("Score");

                            if(scoreList.size() > 0){

                                StringBuilder sb = new StringBuilder();

                                int i;

                                for(i=0;i<scoreList.size()-1;i++){
                                    sb.append(scoreList.get(i));
                                    sb.append(", ");
                                }

                                sb.append(scoreList.get(i));

                                tita_search_option_score_text.setText(sb);

                            }else{

                                tita_search_option_score_text.setText("전체");

                            }

                        }
                    }
                });

        tita_search_option_score_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchScoreActivity.class);
                intent.putExtra("topBarTitle", "학점");
                intent.putExtra("Score", scoreList);
                scoreActivityResultLauncher.launch(intent);
            }
        });

    }


}
