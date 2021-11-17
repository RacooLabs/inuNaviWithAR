package com.maru.inunavi.user;

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
import com.maru.inunavi.R;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private String userID;
    private String userPassword;
    private String userEmail;

    private boolean idValidate = false; //사용자 아이디 가능 체크
    private boolean passwordValidate = false; //사용자 비밀번호 체크
    private boolean passwordCheckValidate = false; //사용자 비밀번호 체크
    private boolean emailValidate = false; //사용자 이메일 가능 체크
    private boolean nameValidate = false; //사용자 이름 가능 체크

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_activity_signup);

        TextView sign_up_back_button = findViewById(R.id.textView_sign_up_back);

        sign_up_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


       EditText editText_sign_up_id = findViewById(R.id.editText_sign_up_id);
       EditText editText_sign_up_password = findViewById(R.id.editText_sign_up_password);
       EditText editText_sign_up_password_second = findViewById(R.id.editText_sign_up_password_second);
       EditText editText_sign_up_email = findViewById(R.id.editText_sign_up_email);
       EditText editText_sign_up_name = findViewById(R.id.editText_sign_up_name);


       ImageView sign_up_id_done_icon = findViewById(R.id.sign_up_id_done_icon);
       ImageView sign_up_password_done_icon = findViewById(R.id.sign_up_password_done_icon);
       ImageView sign_up_password_second_done_icon = findViewById(R.id.sign_up_password_second_done_icon);
       ImageView sign_up_email_done_icon = findViewById(R.id.sign_up_email_done_icon);
       ImageView sign_up_name_done_icon = findViewById(R.id.sign_up_name_done_icon);

       TextView textView_id_warning = findViewById(R.id.textView_id_warning);
       TextView textView_password_warning = findViewById(R.id.textView_password_warning);
       TextView textView_password_second_warning = findViewById(R.id.textView_password_second_warning);
       TextView textView_email_warning = findViewById(R.id.textView_email_warning);
       TextView textView_name_warning = findViewById(R.id.textView_name_warning);

       TextView button_sign_up = findViewById(R.id.button_sign_up);

       editText_sign_up_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {

               if(b) {

                   setNormalEditText(editText_sign_up_id, sign_up_id_done_icon, textView_id_warning);
                   idValidate = false;

               }else {

                   String userID = editText_sign_up_id.getText().toString();

                   if (userID.contains(" ")){
                       setNotEditText(editText_sign_up_id, sign_up_id_done_icon, textView_id_warning, "아이디에 공백이 있습니다.");
                       idValidate = false;

                   } else if (userID.equals("")) {
                       setNotEditText(editText_sign_up_id, sign_up_id_done_icon, textView_id_warning, "아이디를 입력하세요.");
                       idValidate = false;

                   } else if (userID.length() > 15 || userID.length() < 6) {
                       setNotEditText(editText_sign_up_id, sign_up_id_done_icon, textView_id_warning, "아이디는 6자 이상 14자 이하입니다.");
                       idValidate = false;

                   } else {

                       Response.Listener<String> responseListener = new Response.Listener<String>() {

                           @Override
                           public void onResponse(String response) {

                               try {

                                   Log.d("@@@", "signupactivity_107 : " + response);
                                   JSONObject jsonResponse = new JSONObject(response);
                                   boolean success = jsonResponse.getBoolean("success");


                                   if (success) {
                                       setDoneEditText(editText_sign_up_id, sign_up_id_done_icon, textView_id_warning);
                                       idValidate = true;
                                   }else{
                                       setNotEditText(editText_sign_up_id, sign_up_id_done_icon,  textView_id_warning, "아이디가 이미 존재합니다.");
                                       idValidate = false;
                                   }

                               } catch (Exception e) {

                                   Log.d("@@@", "validate error");
                                   e.printStackTrace();

                                   setNotEditText(editText_sign_up_id, sign_up_id_done_icon,  textView_id_warning, "서버 연결 실패");
                                   idValidate = false;


                               }


                           }

                       };

                       ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                       RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                       queue.add(validateRequest);

                   }
               }

           }
       });

       editText_sign_up_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {

               if(b){
                   setNormalEditText(editText_sign_up_password, sign_up_password_done_icon,  textView_password_warning);
                   passwordValidate = false;
               }else{

                   String userId = editText_sign_up_id.getText().toString();
                   String userPassword = editText_sign_up_password.getText().toString();
                   String userPasswordCheck = editText_sign_up_password_second.getText().toString();

                   if(userPassword.contains(userId)){
                       setNotEditText(editText_sign_up_password, sign_up_password_done_icon, textView_password_warning, "비밀번호에 아이디가 포함되어있습니다.");
                       passwordValidate = false;

                   }else if (userPassword.contains(" ")){
                       setNotEditText(editText_sign_up_password, sign_up_password_done_icon,  textView_password_warning, "비밀번호에 공백이 있습니다.");
                       passwordValidate = false;

                   }else if (userPassword.equals("")) {
                       setNotEditText(editText_sign_up_password, sign_up_password_done_icon,  textView_password_warning, "비밀번호를 입력하세요.");
                       passwordValidate = false;

                   }else if (userPassword.length() > 15 || userPassword.length() < 6){
                       setNotEditText(editText_sign_up_password, sign_up_password_done_icon, textView_password_warning, "비밀번호는 6자 이상 20자 이하입니다.");
                       passwordValidate = false;

                   }else {

                       setDoneEditText(editText_sign_up_password, sign_up_password_done_icon,  textView_password_warning);
                       passwordValidate = true;

                       if(userPassword.equals(userPasswordCheck)){
                           setDoneEditText(editText_sign_up_password_second, sign_up_password_second_done_icon,  textView_password_second_warning);
                           passwordCheckValidate = true;
                       }else{
                           setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon,  textView_password_second_warning, "비밀번호가 다릅니다.");
                           passwordCheckValidate = false;
                       }

                   }

               }

           }
       });

       editText_sign_up_password_second.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {


               if(b){
                   setNormalEditText(editText_sign_up_password_second, sign_up_password_second_done_icon,  textView_password_second_warning);
                   passwordCheckValidate = false;
               }else{

                   String userPassword = editText_sign_up_password.getText().toString();
                   String userPasswordCheck = editText_sign_up_password_second.getText().toString();

                   if (userPasswordCheck.equals("")) {
                       setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon,  textView_password_second_warning, "비밀번호를 재확인하세요.");
                       passwordCheckValidate = false;

                   }else if (!userPasswordCheck.equals(userPassword)){

                       setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon,  textView_password_second_warning, "비밀번호가 다릅니다.");
                       passwordCheckValidate = false;

                   }else if (!passwordValidate) {

                       setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon,  textView_password_second_warning, "");
                       textView_password_second_warning.setVisibility(View.GONE);


                   }else{

                       setDoneEditText(editText_sign_up_password_second, sign_up_password_second_done_icon,  textView_password_second_warning);
                       passwordCheckValidate = true;

                   }

               }

           }
       });

       editText_sign_up_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {

               if(b){
                   setNormalEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning);
                   emailValidate = false;
               }else{

                   String userEmail = editText_sign_up_email.getText().toString();
                   Pattern p = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
                   Matcher m = p.matcher(userEmail);
                   Log.d("@@@", userEmail);


                   if (userEmail.equals("")) {
                       setNotEditText(editText_sign_up_email, sign_up_email_done_icon,  textView_email_warning, "이메일을 입력하세요.");
                       emailValidate = false;

                   }else if (!m.matches()) {

                       setNotEditText(editText_sign_up_email, sign_up_email_done_icon,  textView_email_warning, "이메일 형식을 입력하세요.");
                       emailValidate = false;

                   }else{

                       setDoneEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning);
                       emailValidate = true;

                   }

               }


           }
       });

       editText_sign_up_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {

               if(b){
                   setNormalEditText(editText_sign_up_name, sign_up_name_done_icon, textView_name_warning);
                   nameValidate = false;
               }else{

                   String userName = editText_sign_up_name.getText().toString();

                   if (userName.equals("")) {
                       setNotEditText(editText_sign_up_name, sign_up_name_done_icon, textView_name_warning, "이름을 입력하세요.");
                       nameValidate = false;

                   }else if (userName.length() > 15){
                       setNotEditText(editText_sign_up_name, sign_up_name_done_icon, textView_name_warning, "이름은 14자 이하입니다.");
                       nameValidate = false;

                   }else{

                       setDoneEditText(editText_sign_up_name, sign_up_name_done_icon, textView_name_warning);
                       nameValidate = true;

                   }

               }
               
           }
       });

       button_sign_up.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String userID = editText_sign_up_id.getText().toString();
               String userPassword = editText_sign_up_password.getText().toString();
               String userEmail = editText_sign_up_email.getText().toString();
               String userName = editText_sign_up_name.getText().toString();


               editText_sign_up_id.clearFocus();
               editText_sign_up_password.clearFocus();
               editText_sign_up_password_second.clearFocus();
               editText_sign_up_email.clearFocus();
               editText_sign_up_name.clearFocus();

               if(idValidate && passwordValidate && passwordCheckValidate && emailValidate && nameValidate){

                   Response.Listener<String> responseListener = new Response.Listener<String>() {

                       @Override
                       public void onResponse(String response) {

                           try {

                               Log.d("@@@", "signupactivity_329 : " + response);

                               JSONObject jsonResponse = new JSONObject(response);

                               boolean success = jsonResponse.getBoolean("success");

                               if (success) {
                                   Toast.makeText(getApplicationContext(), "회원 등록을 완료하였습니다.", Toast.LENGTH_LONG).show();
                                   finish();

                               }else{
                                   Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_LONG).show();
                               }

                           } catch (Exception e) {

                               e.printStackTrace();

                           }


                       }

                   };

                   SignUpRequest signupRequest = new SignUpRequest(userID,userPassword, userEmail, userName,responseListener);
                   RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                   queue.add(signupRequest);


               }else{
                   Toast.makeText(getApplicationContext(), "수정할 항목이 있습니다.", Toast.LENGTH_LONG).show();
               }

           }
       });


    }

    public void setNormalEditText(EditText e, ImageView i_done, TextView t){
        e.setBackgroundResource(R.drawable.layout_login_roundedbox);
        i_done.setVisibility(View.INVISIBLE);
        t.setVisibility(View.GONE);
    }

    public void setDoneEditText(EditText e, ImageView i_done, TextView t){
        e.setBackgroundResource(R.drawable.layout_login_roundedbox_done);
        i_done.setVisibility(View.VISIBLE);
        t.setVisibility(View.GONE);
    }

    public void setNotEditText(EditText e, ImageView i_done, TextView t, String msg){
        e.setBackgroundResource(R.drawable.layout_login_roundedbox_not);
        i_done.setVisibility(View.INVISIBLE);
        t.setVisibility(View.VISIBLE);
        t.setText(msg);
    }

    @Override
    protected void onStop(){
       super.onStop();

    }

}
