package com.maru.inunavi.ui.timetable.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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

public class SearchOptionActivity extends AppCompatActivity implements Serializable {

    ArrayList<String> gradeList = new ArrayList<String>(Arrays.asList("1학년", "2학년", "3학년", "4학년" ));

    ArrayList<String> categoryList = new ArrayList<String>(Arrays.asList("전공선택", "전공기초", "전공필수", "교양필수","기초과학","교양선택", "교직", "일반선택",
            "군사학"));

    ArrayList<String> scoreList = new ArrayList<String>(Arrays.asList("1학점", "2학점", "3학점", "4학점"));

    String main_keyword = "";
    String keyword_option = "전체";

    String major_option = "전체";
    String cse_option = "전체";
    String sort_option =  "기본";
    String grade_option = "전체";
    String category_option = "전체";
    String score_option = "전체";

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_search_option);

        ImageView tita_search_option_backButton = findViewById(R.id.tita_search_option_backButton);

        LinearLayout tita_search_option_major = findViewById(R.id.tita_search_option_major);
        LinearLayout tita_search_option_cse = findViewById(R.id.tita_search_option_cse);
        LinearLayout tita_search_option_sort = findViewById(R.id.tita_search_option_sort);
        LinearLayout tita_search_option_grade_layout = findViewById(R.id.tita_search_option_grade_layout);
        LinearLayout tita_search_option_category_layout = findViewById(R.id.tita_search_option_category_layout);
        LinearLayout tita_search_option_score_layout = findViewById(R.id.tita_search_option_score_layout);

        EditText tita_search_option_searchbar = findViewById(R.id.tita_search_option_searchbar);
        RadioGroup tita_search_option_radioGroup = findViewById(R.id.tita_search_option_radioGroup);

        TextView tita_search_option_major_text = findViewById(R.id.tita_search_option_major_text);
        TextView tita_search_option_cse_text = findViewById(R.id.tita_search_option_cse_text);
        TextView tita_search_option_sort_text = findViewById(R.id.tita_search_option_sort_text);
        TextView tita_search_option_grade_text = findViewById(R.id.tita_search_option_grade_text);
        TextView tita_search_option_category_text = findViewById(R.id.tita_search_option_category_text);
        TextView tita_search_option_score_text = findViewById(R.id.tita_search_option_score_text);

        TextView tita_search_option_ok = findViewById(R.id.tita_search_option_ok);



        //돌아가기 버튼
        tita_search_option_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  { finish();
                overridePendingTransition(0, 0);}
        });

        //검색 조건
        tita_search_option_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){

                    case R.id.tita_search_option_radio1:
                        keyword_option = "전체";
                        break;

                    case R.id.tita_search_option_radio2:
                        keyword_option = "과목명";
                        break;

                    case R.id.tita_search_option_radio3:
                        keyword_option = "교수명";
                        break;

                    case R.id.tita_search_option_radio4:
                        keyword_option = "과목코드";
                        break;

                }
            }
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
                intent.putExtra("topBarTitle", "전공");
                majorActivityResultLauncher.launch(intent);

            }
        });

        //교양필수 콜백 리스너
        ActivityResultLauncher<Intent> CSEActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);
                            String CSE = intent.getStringExtra("CSE");

                            tita_search_option_cse_text.setText(CSE);

                        }
                    }
                });


        tita_search_option_cse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchCSEActivity.class);
                intent.putExtra("topBarTitle", "교양필수");
                CSEActivityResultLauncher.launch(intent);

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

                            if(gradeList.size() == 4 || gradeList.size() == 0){

                                tita_search_option_grade_text.setText("전체");

                            }else if(gradeList.size() > 0){

                                StringBuilder sb = new StringBuilder();

                                int i;

                                for(i=0;i<gradeList.size()-1;i++){
                                    sb.append(gradeList.get(i));
                                    sb.append(", ");
                                }

                                sb.append(gradeList.get(i));

                                tita_search_option_grade_text.setText(sb);

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
        ActivityResultLauncher<Intent> categoryActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent intent = result.getData();
                            int CallType = intent.getIntExtra("CallType", 0);
                            categoryList = (ArrayList<String>)intent.getSerializableExtra("Category");

                            if(categoryList.size() == 9 || categoryList.size() == 0){

                                tita_search_option_category_text.setText("전체");

                            }else if(categoryList.size() > 0){

                                StringBuilder sb = new StringBuilder();

                                int i;

                                for(i=0;i<categoryList.size()-1;i++){
                                    sb.append(categoryList.get(i));
                                    sb.append(", ");
                                }

                                sb.append(categoryList.get(i));

                                tita_search_option_category_text.setText(sb);

                            }


                        }
                    }
                });

        tita_search_option_category_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchOptionActivity.this, SearchCategoryActivity.class);
                intent.putExtra("topBarTitle", "구분");
                intent.putExtra("Category", categoryList);
                categoryActivityResultLauncher.launch(intent);
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


                            if(scoreList.size() == 4 || scoreList.size() == 0){
                                tita_search_option_score_text.setText("전체");

                            }else if(scoreList.size() > 0){

                                StringBuilder sb = new StringBuilder();

                                int i;

                                for(i=0;i<scoreList.size()-1;i++){
                                    sb.append(scoreList.get(i));
                                    sb.append(", ");
                                }

                                sb.append(scoreList.get(i));

                                tita_search_option_score_text.setText(sb);

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

        //확인버튼
        tita_search_option_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SearchOptionActivity.this, SearchActivity.class);
                intent.putExtra("CallType", 0);


                if(tita_search_option_searchbar.getText() == null){
                    main_keyword = "";
                }else{
                    main_keyword = tita_search_option_searchbar.getText().toString().trim();
                }

                major_option = tita_search_option_major_text.getText().toString();
                cse_option = tita_search_option_cse_text.getText().toString();
                sort_option =  tita_search_option_sort_text.getText().toString();
                grade_option = tita_search_option_grade_text.getText().toString();
                category_option = tita_search_option_category_text.getText().toString();
                score_option = tita_search_option_score_text.getText().toString();

                intent.putExtra("main_keyword", main_keyword);
                intent.putExtra("keyword_option",keyword_option);
                intent.putExtra("major_option",  major_option);
                intent.putExtra("cse_option",  cse_option);
                intent.putExtra("sort_option", sort_option);
                intent.putExtra("grade_option",grade_option);
                intent.putExtra("category_option", category_option);
                intent.putExtra("score_option", score_option) ;

                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(0, 0);

            }

        });


    }


}
