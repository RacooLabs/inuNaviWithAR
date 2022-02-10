package com.maru.inunavi.user;

import static com.maru.inunavi.MainActivity.sessionURL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.SettingActivity;

import org.json.JSONObject;

public class QuitActivity extends AppCompatActivity {


    private boolean userPasswordValidate = false;

    private String url = sessionURL;

    private String userEmail = MainActivity.cookieManager.getCookie(url).replace("cookieKey=", "");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_quit);

        TextView change_password_back_button = findViewById(R.id.textView_quit_back);

        change_password_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

       EditText editText_quit = findViewById(R.id.editText_quit);
       
       ImageView quit_done_icon = findViewById(R.id.quit_done_icon);

       TextView textView_warning = findViewById(R.id.textView_warning);

       TextView button_quit = findViewById(R.id.button_quit);


        button_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userPassword= editText_quit.getText().toString().trim();

                if (userPassword.equals("")) {
                    setNotEditText(editText_quit, quit_done_icon, textView_warning, "비밀번호를 입력하세요.");
                    userPasswordValidate = false;

                }else{

                    Response.Listener<String> responsePasswordListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {

                                Log.d("@@@", "signupactivity_107 : " + response);
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    setDoneEditText(editText_quit, quit_done_icon, textView_warning);
                                    userPasswordValidate = true;

                                }else{
                                    setNotEditText(editText_quit, quit_done_icon,  textView_warning, "비밀번호가 틀립니다.");
                                    userPasswordValidate = false;
                                }

                                editText_quit.clearFocus();

                                if(userPasswordValidate) {

                                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {

                                            try {

                                                Log.d("@@@", "signupactivity_329 : " + response);

                                                JSONObject jsonResponse = new JSONObject(response);


                                                Log.d("@@@ changePassword ", response);

                                                boolean success = jsonResponse.getBoolean("success");

                                                if (success) {
                                                    Toast.makeText(getApplicationContext(), "회원 탈퇴를 완료하였습니다.", Toast.LENGTH_LONG).show();

                                                    Intent intent = new Intent(QuitActivity.this, SettingActivity.class);
                                                    intent.putExtra("CallType", 1);
                                                    setResult(Activity.RESULT_OK, intent);
                                                    finish();
                                                    overridePendingTransition(0, 0);

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "회원탈퇴에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                                }

                                            } catch (Exception e) {

                                                e.printStackTrace();

                                            }


                                        }

                                    };

                                    QuitRequest quitRequest = new QuitRequest(userEmail, userPassword, responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(QuitActivity.this);
                                    queue.add(quitRequest);
                                }


                            } catch (Exception e) {

                                Log.d("@@@", "validate error");
                                e.printStackTrace();

                                setNotEditText(editText_quit, quit_done_icon,  textView_warning, "서버 연결 실패");
                                userPasswordValidate = false;

                            }

                        }

                    };

                    LoginRequest checkOriginPasswordRequest = new LoginRequest(userEmail,userPassword, responsePasswordListener);
                    RequestQueue queuePassword = Volley.newRequestQueue(QuitActivity.this);
                    queuePassword.add(checkOriginPasswordRequest);

                }


            }
        });


    }


    public void setNormalEditText(EditText e, ImageView i_done, TextView t){
        e.setBackgroundResource(R.drawable.layout_login_round_box);
        i_done.setVisibility(View.INVISIBLE);
        t.setVisibility(View.GONE);
    }

    public void setDoneEditText(EditText e, ImageView i_done, TextView t){
        e.setBackgroundResource(R.drawable.layout_login_round_box_done);
        i_done.setVisibility(View.VISIBLE);
        t.setVisibility(View.GONE);
    }

    public void setNotEditText(EditText e, ImageView i_done, TextView t, String msg){
        e.setBackgroundResource(R.drawable.layout_login_signup_round_box_not);
        i_done.setVisibility(View.INVISIBLE);
        t.setVisibility(View.VISIBLE);
        t.setText(msg);
    }

    @Override
    protected void onStop(){
       super.onStop();

    }

}
