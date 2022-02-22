package com.maru.inunavi.ui.timetable;

import static com.maru.inunavi.MainActivity.cookieManager;
import static com.maru.inunavi.MainActivity.sessionURL;

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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.maru.inunavi.AppInfo;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.search.Schedule;
import com.maru.inunavi.user.ChangePasswordActivity;
import com.maru.inunavi.user.LoginActivity;
import com.maru.inunavi.user.QuitActivity;
import com.maru.inunavi.user.SignUpActivity;
import com.maru.inunavi.user.SignUpMajorActivity;
import com.maru.inunavi.user.SignUpRequest;

import org.json.JSONObject;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SettingAdapter adapter;

    private String url = sessionURL;
    private String userEmail = MainActivity.cookieManager.getCookie(url).replace("cookieKey=", "");

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_setting);

        recyclerView = (RecyclerView)findViewById(R.id.setting_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)) ;

        ImageView tita_setting_backButton = findViewById(R.id.tita_setting_backButton);
        TextView setting_user_email = findViewById(R.id.setting_user_email);
        TextView setting_user_major = findViewById(R.id.setting_user_major);

        // 이메일 적기
        setting_user_email.setText(userEmail);
        setting_user_major.setText(MainActivity.userMajor);

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
        list.add("전공 변경");
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

        //전공 변경 콜백
        ActivityResultLauncher<Intent> modifyMajorActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);

                            if(CallType == 1) {

                                String userMajor = intent.getStringExtra("userMajor");

                                Response.Listener<String> modifyMajorResponseListener = new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {

                                        Log.d("@@@ SettingActivity.java 138", response);

                                        try {

                                            JSONObject jsonResponse = new JSONObject(response);

                                            boolean success = jsonResponse.getBoolean("success");


                                            if (success) {

                                                Toast.makeText(getApplicationContext(), "전공을 변경하였습니다.", Toast.LENGTH_SHORT).show();
                                                MainActivity.userMajor = userMajor;
                                                setting_user_major.setText(MainActivity.userMajor);

                                                MainActivity.autoLogin = true;

                                                if(MainActivity.autoLogin) {

                                                    // 자동 로그인 데이터 저장
                                                    SharedPreferences auto = getApplicationContext().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                                                    SharedPreferences.Editor autoLoginEdit = auto.edit();
                                                    autoLoginEdit.putString("userEmail", userEmail);
                                                    autoLoginEdit.putString("userMajor", MainActivity.userMajor);
                                                    autoLoginEdit.commit();

                                                }



                                            } else {
                                                Toast.makeText(getApplicationContext(), "전공 변경을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (Exception e) {



                                        }


                                    }

                                };

                                ModifyMajorRequest modifyMajorRequest = new ModifyMajorRequest(userEmail ,userMajor, modifyMajorResponseListener);
                                RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                                queue.add(modifyMajorRequest);

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

                }else if(((TextView)v).getText().equals("비밀번호 수정")) {

                    // 비밀 번호 변경 요청

                    Intent startIntent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                    startActivity(startIntent);

                }else if(((TextView)v).getText().equals("전공 변경")){

                    // 전공 변경 요청

                    Intent startIntent = new Intent(SettingActivity.this, SignUpMajorActivity.class);
                    modifyMajorActivityResultLauncher.launch(startIntent);


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
