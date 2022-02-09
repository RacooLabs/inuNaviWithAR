package com.maru.inunavi.ui.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.user.LoginActivity;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SettingAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_setting);

        recyclerView = (RecyclerView)findViewById(R.id.setting_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)) ;

        ImageView tita_setting_backButton = findViewById(R.id.tita_setting_backButton);



        //돌아가기 버튼
        tita_setting_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        ArrayList<String> list = new ArrayList<>();
        list.add("로그아웃");
        list.add("비밀번호 수정");
        list.add("회원 탈퇴");
        list.add("앱 정보");
        list.add("오류 신고");
        adapter = new SettingAdapter(list);
        recyclerView.setAdapter(adapter);


        //어댑터 콜백 리스너
        adapter.setOnItemClickListener(new SettingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                if(((TextView)v).getText().equals("로그아웃")){

                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    intent.putExtra("CallType", 1001);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);

                }else if(((TextView)v).getText().equals("비밀번호 수정")){

                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    intent.putExtra("CallType", 1002);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);


                }else if(((TextView)v).getText().equals("회원 탈퇴")){

                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    intent.putExtra("CallType", 1003);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);


                }else if(((TextView)v).getText().equals("앱 정보")){

                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    intent.putExtra("CallType", 1004);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);


                }else if(((TextView)v).getText().equals("오류 신고")){

                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    intent.putExtra("CallType", 1005);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    overridePendingTransition(0, 0);

                }else{


                }

            }

        }) ;

    }


}
