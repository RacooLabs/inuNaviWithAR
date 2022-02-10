package com.maru.inunavi.ui.timetable;

import static com.maru.inunavi.MainActivity.cookieManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.AppInfo;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.search.Schedule;
import com.maru.inunavi.user.ChangePasswordActivity;
import com.maru.inunavi.user.LoginActivity;
import com.maru.inunavi.user.QuitActivity;

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

        //회원 탈퇴 콜백
        ActivityResultLauncher<Intent> quitActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);

                            if(CallType == 1) {

                                Intent sendIntent = new Intent(SettingActivity.this, MainActivity.class);
                                sendIntent.putExtra("CallType", 1001);
                                setResult(Activity.RESULT_OK, sendIntent);
                                finish();
                                overridePendingTransition(0, 0);

                            }

                        }
                    }
                });


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

                    // 비밀 번호 변경 요청

                    Intent startIntent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                    startActivity(startIntent);


                }else if(((TextView)v).getText().equals("회원 탈퇴")){

                    Intent quitIntent = new Intent(SettingActivity.this, QuitActivity.class);
                    quitActivityResultLauncher.launch(quitIntent);


                }else if(((TextView)v).getText().equals("앱 정보")){

                    Intent intent = new Intent(SettingActivity.this, AppInfo.class);
                    startActivity(intent);

                }else if(((TextView)v).getText().equals("오류 신고")){

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("plain/text");
                    String[] address = {"racoo340@gmail.com"};
                    email.putExtra(Intent.EXTRA_EMAIL, address);
                    email.putExtra(Intent.EXTRA_SUBJECT, " inunani 오류 신고");
                    email.putExtra(Intent.EXTRA_TEXT, "오류 내용을 적어주세요\n------------------------------------\n");
                    startActivity(email);

                }else{


                }

            }

        }) ;

    }


}
