package com.maru.inunavi.user;

import static com.maru.inunavi.MainActivity.sessionURL;

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

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordActivity extends AppCompatActivity {


    private boolean originPasswordValidate = false;
    private boolean newPasswordValidate = false; //사용자 비밀번호 체크
    private boolean newPasswordCheckValidate = false; //사용자 비밀번호 체크

    private String url = sessionURL;

    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_change_password);

        TextView change_password_back_button = findViewById(R.id.textView_change_password_back);

        change_password_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        if(MainActivity.cookieManager != null) {
            userEmail = MainActivity.cookieManager.getCookie(url).replace("cookieKey=", "");
        }


       EditText editText_change_password_origin = findViewById(R.id.editText_change_password_origin);
       EditText editText_change_password = findViewById(R.id.editText_change_password);
       EditText editText_change_password_second = findViewById(R.id.editText_change_password_second);


       ImageView change_password_origin_done_icon = findViewById(R.id.change_password_origin_done_icon);
       ImageView change_password_done_icon = findViewById(R.id.change_password_done_icon);
       ImageView change_password_second_done_icon = findViewById(R.id.change_password_second_done_icon);


       TextView textView_origin_warning = findViewById(R.id.textView_origin_warning);
       TextView textView_password_warning = findViewById(R.id.textView_password_warning);
       TextView textView_password_second_warning = findViewById(R.id.textView_password_second_warning);

       TextView button_change_password = findViewById(R.id.button_change_password);

       editText_change_password_origin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {

               String userPassword = editText_change_password.getText().toString().trim();

               if(b) {

                   setNormalEditText(editText_change_password_origin, change_password_origin_done_icon, textView_origin_warning);
                   originPasswordValidate = false;

               }else {

                   String originPassword= editText_change_password_origin.getText().toString().trim();

                   if (originPassword.equals("")) {
                       setNotEditText(editText_change_password_origin, change_password_origin_done_icon, textView_origin_warning, "기존 비밀번호를 입력하세요.");
                       originPasswordValidate = false;

                   } else {

                       Response.Listener<String> responseListener = new Response.Listener<String>() {

                           @Override
                           public void onResponse(String response) {

                               try {

                                   Log.d("@@@", "signupactivity_107 : " + response);
                                   JSONObject jsonResponse = new JSONObject(response);
                                   boolean success = jsonResponse.getBoolean("success");

                                   if (success) {
                                       setDoneEditText(editText_change_password_origin, change_password_origin_done_icon, textView_origin_warning);
                                       originPasswordValidate = true;

                                       if(!userPassword.isEmpty() && !userPassword.equals("")){

                                           if(!originPassword.equals(userPassword)){
                                               setDoneEditText(editText_change_password, change_password_done_icon,  textView_password_warning);
                                               newPasswordValidate = true;
                                           }else{
                                               setNotEditText(editText_change_password, change_password_done_icon,  textView_password_warning, "기존과 다른 비밀번호를 입력하세요.");
                                               newPasswordValidate = false;
                                           }

                                       }



                                   }else{
                                       setNotEditText(editText_change_password_origin, change_password_origin_done_icon,  textView_origin_warning, "기존 비밀번호가 틀립니다.");
                                       originPasswordValidate = false;
                                   }

                               } catch (Exception e) {

                                   Log.d("@@@", "validate error");
                                   e.printStackTrace();

                                   setNotEditText(editText_change_password_origin, change_password_origin_done_icon,  textView_origin_warning, "서버 연결 실패");
                                   originPasswordValidate = false;

                               }

                           }

                       };

                       LoginRequest checkOriginPasswordRequest = new LoginRequest(userEmail,originPassword, responseListener);
                       RequestQueue queue = Volley.newRequestQueue(ChangePasswordActivity.this);
                       queue.add(checkOriginPasswordRequest);

                   }
               }

           }
       });

       editText_change_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {

               if(b){
                   setNormalEditText(editText_change_password, change_password_done_icon,  textView_password_warning);
                   newPasswordValidate = false;
               }else{

                   String originPassword = editText_change_password_origin.getText().toString().trim();
                   String userPassword = editText_change_password.getText().toString().trim();
                   String userPasswordCheck = editText_change_password_second.getText().toString().trim();

                   if(userPassword.equals(originPassword)){
                       setNotEditText(editText_change_password, change_password_done_icon, textView_password_warning, "기존과 다른 비밀번호를 입력하세요.");
                       newPasswordValidate = false;

                   }else if (userPassword.contains(" ")){
                       setNotEditText(editText_change_password, change_password_done_icon,  textView_password_warning, "세 비밀번호에 공백이 있습니다.");
                       newPasswordValidate = false;

                   }else if (userPassword.equals("")) {
                       setNotEditText(editText_change_password, change_password_done_icon,  textView_password_warning, "새 비밀번호를 입력하세요.");
                       newPasswordValidate = false;

                   }else if (userPassword.length() > 15 || userPassword.length() < 6){
                       setNotEditText(editText_change_password, change_password_done_icon, textView_password_warning, "비밀번호는 6자 이상 20자 이하입니다.");
                       newPasswordValidate = false;

                   }else {

                       setDoneEditText(editText_change_password, change_password_done_icon,  textView_password_warning);
                       newPasswordValidate = true;

                       if(userPassword.equals(userPasswordCheck)){
                           setDoneEditText(editText_change_password_second, change_password_second_done_icon,  textView_password_second_warning);
                           newPasswordCheckValidate = true;
                       }else{
                           setNotEditText(editText_change_password_second, change_password_second_done_icon,  textView_password_second_warning, "새 비밀번호가 다릅니다.");
                           newPasswordCheckValidate = false;
                       }

                   }

               }

           }
       });

       editText_change_password_second.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {


               if(b){
                   setNormalEditText(editText_change_password_second, change_password_second_done_icon,  textView_password_second_warning);
                   newPasswordCheckValidate = false;
               }else{

                   String userPassword = editText_change_password.getText().toString();
                   String userPasswordCheck = editText_change_password_second.getText().toString();

                   if (userPasswordCheck.equals("")) {
                       setNotEditText(editText_change_password_second, change_password_second_done_icon,  textView_password_second_warning, "새 비밀번호를 재확인하세요.");
                       newPasswordCheckValidate = false;

                   }else if (!userPasswordCheck.equals(userPassword)){

                       setNotEditText(editText_change_password_second, change_password_second_done_icon,  textView_password_second_warning, "새 비밀번호가 다릅니다.");
                       newPasswordCheckValidate = false;

                   }else if (!newPasswordValidate) {

                       setNotEditText(editText_change_password_second, change_password_second_done_icon,  textView_password_second_warning, "");
                       textView_password_second_warning.setVisibility(View.GONE);


                   }else{

                       setDoneEditText(editText_change_password_second, change_password_second_done_icon,  textView_password_second_warning);
                       newPasswordCheckValidate = true;

                   }

               }

           }
       });

        button_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userNewPassword = editText_change_password.getText().toString();

                editText_change_password_origin.clearFocus();
                editText_change_password.clearFocus();
                editText_change_password_second.clearFocus();


                if(originPasswordValidate && newPasswordValidate && newPasswordCheckValidate){

                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {

                                Log.d("@@@", "signupactivity_329 : " + response);

                                JSONObject jsonResponse = new JSONObject(response);


                                Log.d("@@@ changePassword ", response);

                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    Toast.makeText(getApplicationContext(), "비밀번호 변경을 완료하였습니다.", Toast.LENGTH_LONG).show();
                                    finish();
                                    overridePendingTransition(0, 0);

                                }else{
                                    Toast.makeText(getApplicationContext(), "비밀번호 변경에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                }

                            } catch (Exception e) {

                                e.printStackTrace();

                            }


                        }

                    };

                    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(userEmail,userNewPassword,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ChangePasswordActivity.this);
                    queue.add(changePasswordRequest);


                }else{
                    Toast.makeText(getApplicationContext(), "수정할 항목이 있습니다.", Toast.LENGTH_LONG).show();
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
