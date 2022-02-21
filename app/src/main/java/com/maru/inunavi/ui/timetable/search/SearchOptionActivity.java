package com.maru.inunavi.ui.timetable.search;


import static com.maru.inunavi.ui.timetable.search.SearchActivity.categoryList;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.category_option;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.cse_option;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.gradeList;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.grade_option;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.main_keyword_option;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.major_option;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.radioList;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.scoreList;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.score_option;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.selectionCategoryList;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.selectionGradeList;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.selectionScoreList;
import static com.maru.inunavi.ui.timetable.search.SearchActivity.sort_option;

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
import com.maru.inunavi.ui.timetable.CalendarFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchOptionActivity extends AppCompatActivity implements Serializable {


    String keyword_option = "전체";


    @Override
    public void onBackPressed() {

        selectionCategoryList.clear();
        selectionGradeList.clear();
        selectionScoreList.clear();

        selectionCategoryList.addAll(categoryList);
        selectionGradeList.addAll(gradeList);
        selectionScoreList.addAll(scoreList);

        main_keyword_option = "";
        SearchActivity.radioList= new boolean[]{true, false, false, false};
        major_option = "전체";
        cse_option = "전체";
        sort_option =  "기본";
        grade_option = "전체";
        category_option = "전체";
        score_option = "전체";

        finish();
        overridePendingTransition(0, 0);


    }

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

        tita_search_option_searchbar.setText(main_keyword_option);
        tita_search_option_major_text.setText(major_option);
        tita_search_option_cse_text.setText(cse_option);
        tita_search_option_sort_text.setText(sort_option);
        tita_search_option_grade_text.setText(grade_option);
        tita_search_option_category_text.setText(category_option);
        tita_search_option_score_text.setText(score_option);


        //돌아가기 버튼
        tita_search_option_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {

                selectionCategoryList.clear();
                selectionGradeList.clear();
                selectionScoreList.clear();

                selectionCategoryList.addAll(categoryList);
                selectionGradeList.addAll(gradeList);
                selectionScoreList.addAll(scoreList);

                main_keyword_option = "";
                SearchActivity.radioList= new boolean[]{true, false, false, false};
                major_option = "전체";
                cse_option = "전체";
                sort_option =  "기본";
                grade_option = "전체";
                category_option = "전체";
                score_option = "전체";

                finish();
                overridePendingTransition(0, 0);

            }
        });

        // 라디오 버튼 세팅

        if(radioList[0]){

            tita_search_option_radioGroup.check(R.id.tita_search_option_radio1);

        }else if(radioList[1]){

            tita_search_option_radioGroup.check(R.id.tita_search_option_radio2);

        }else if(radioList[2]){

            tita_search_option_radioGroup.check(R.id.tita_search_option_radio3);

        }else if(radioList[3]) {

            tita_search_option_radioGroup.check(R.id.tita_search_option_radio4);

        }


        //검색 조건
        tita_search_option_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){

                    case R.id.tita_search_option_radio1:
                        keyword_option = "전체";

                        radioList[0] = true;
                        radioList[1] = false;
                        radioList[2] = false;
                        radioList[3] = false;

                        break;

                    case R.id.tita_search_option_radio2:
                        keyword_option = "과목명";

                        radioList[0] = false;
                        radioList[1] = true;
                        radioList[2] = false;
                        radioList[3] = false;

                        break;

                    case R.id.tita_search_option_radio3:
                        keyword_option = "교수명";

                        radioList[0] = false;
                        radioList[1] = false;
                        radioList[2] = true;
                        radioList[3] = false;

                        break;

                    case R.id.tita_search_option_radio4:
                        keyword_option = "과목코드";

                        radioList[0] = false;
                        radioList[1] = false;
                        radioList[2] = false;
                        radioList[3] = true;

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
                            major_option = major;


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
                            cse_option = CSE;

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
                            sort_option = sortType;

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


                            if(selectionGradeList.size() == gradeList.size() || selectionGradeList.size() == 0){

                                tita_search_option_grade_text.setText("전체");
                                grade_option = "전체";

                            }else if(selectionGradeList.size() > 0){

                                StringBuilder sb = new StringBuilder();

                                int i;

                                for(i=0;i<selectionGradeList.size()-1;i++){
                                    sb.append(selectionGradeList.get(i));
                                    sb.append(", ");
                                }

                                sb.append(selectionGradeList.get(i));

                                tita_search_option_grade_text.setText(sb);
                                grade_option = sb.toString();

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


                            if(selectionCategoryList.size() == categoryList.size() || selectionCategoryList.size() == 0){

                                tita_search_option_category_text.setText("전체");
                                category_option = "전체";

                            }else if(selectionCategoryList.size() > 0){

                                StringBuilder sb = new StringBuilder();

                                int i;

                                for(i=0;i<selectionCategoryList.size()-1;i++){
                                    sb.append(selectionCategoryList.get(i));
                                    sb.append(", ");
                                }

                                sb.append(selectionCategoryList.get(i));

                                tita_search_option_category_text.setText(sb);
                                category_option = sb.toString();

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


                            if(selectionScoreList.size() == scoreList.size() || selectionScoreList.size() == 0){
                                tita_search_option_score_text.setText("전체");
                                score_option = "전체";

                            }else if(selectionScoreList.size() > 0){

                                StringBuilder sb = new StringBuilder();

                                int i;

                                for(i=0;i<selectionScoreList.size()-1;i++){
                                    sb.append(selectionScoreList.get(i));
                                    sb.append(", ");
                                }

                                sb.append(selectionScoreList.get(i));

                                tita_search_option_score_text.setText(sb);
                                score_option = sb.toString();

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
                    main_keyword_option = "";
                }else{
                    main_keyword_option = tita_search_option_searchbar.getText().toString().trim();
                }

                major_option = tita_search_option_major_text.getText().toString();
                cse_option = tita_search_option_cse_text.getText().toString();
                sort_option =  tita_search_option_sort_text.getText().toString();
                grade_option = tita_search_option_grade_text.getText().toString();
                category_option = tita_search_option_category_text.getText().toString();
                score_option = tita_search_option_score_text.getText().toString();

                intent.putExtra("main_keyword_option", main_keyword_option);
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
