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

public class SearchMajorActivity extends AppCompatActivity {

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
        ArrayList<String> majorArray = new ArrayList<String>(Arrays.asList("국어국문학과", "영어영문학과", "독어독문학과", "불어불문학과", "일어일문학과", "중어중국학과", "수학과",
                "물리학과", "화학과", "소비자·아동학과", "패션산업학과", "해양학과", "사회복지학과", "신문방송학과", "문헌정보학과", "창의인재개발학과", "행정학과", "정치외교학과",
                "경제학과", "경제학과(야)", "무역학부", "무역학부(야)", "소비자학과", "기계공학과", "기계공학과(야)", "메카트로닉스공학과", "전기공학과", "전자공학과",
                "전자공학과(야)", "산업경영공학과", "산업경영공학과(야)", "안전공학과", "신소재공학과", "에너지화학공학과", "컴퓨터공학부", "컴퓨터공학부(야)", "정보통신공학과",
                "임베디드시스템공학과", "경영학부", "세무회계학과", "조형예술학부", "한국화전공","서양화전공", "디자인학부", "공연예술학과", "체육학부", "운동건강학부", "국어교육과", "영어교육과",
                "일어교육과", "수학교육과", "체육교육과", "유아교육과", "역사교육과", "윤리교육과", "도시행정학과", "도시건축학부", "건축공학전공", "도시건축학전공", "건설환경공학부",
                "도시공학과", "건설환경공학전공", "환경공학전공", "생명과학부","생명과학전공", "분자의생명전공", "생명공학부", "생명공학전공", "나노바이오전공", "동북아국제통상학부", "한국통상전공", "법학부", "광전자공학전공(연계)",
                "물류학전공(연계)", "인공지능소프트웨어연계전공", "MICE,스포츠및관광연계전공", "창의적디자인연계전공", "뷰티산업연계전공", "인문문화예술기획연계전공", "소셜데이터사이언스연계전공"));


        /*SearchAdapter.Item major = new SearchAdapter.Item(SearchAdapter.HEADER, "전공");
        major.invisibleChildren = new ArrayList<>();*/


        for (int i =0;i<majorArray.size();i++){
            SearchAdapter.Item major = new SearchAdapter.Item(SearchAdapter.DEFAULT_CHILD, majorArray.get(i));
            data.add(major);
        }

        SearchAdapter adapter =  new SearchAdapter(data);

        recyclerView.setAdapter(adapter);


        //어댑터 콜백 리스너
        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(SearchMajorActivity.this, SearchOptionActivity.class);
                intent.putExtra("CallType", 1001);
                intent.putExtra("Major", ((TextView)v).getText());
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

        }) ;



    }

}
