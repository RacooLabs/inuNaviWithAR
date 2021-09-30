package com.maru.inunavi;

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

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private String userID;
    private String userPassword;
    private String userEmail;

    private boolean idValidate = false; //사용자 아이디 가능 체크
    private boolean passwordValidate = false; //사용자 아이디 가능 체크
    private boolean passwordCheckValidate = false; //사용자 아이디 가능 체크
    private boolean emailValidate = false; //사용자 아이디 가능 체크

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        TextView sign_up_back_button = findViewById(R.id.textView_sign_up_back);

        sign_up_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToLoginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(backToLoginIntent);
            }
        });


       EditText editText_sign_up_id = findViewById(R.id.editText_sign_up_id);
       EditText editText_sign_up_password = findViewById(R.id.editText_sign_up_password);
       EditText editText_sign_up_password_second = findViewById(R.id.editText_sign_up_password_second);
       EditText editText_sign_up_email = findViewById(R.id.editText_sign_up_email);

       ImageView sign_up_id_done_icon = findViewById(R.id.sign_up_id_done_icon);
       ImageView sign_up_id_not_icon = findViewById(R.id.sign_up_id_not_icon);

       ImageView sign_up_password_done_icon = findViewById(R.id.sign_up_password_done_icon);
       ImageView sign_up_password_not_icon = findViewById(R.id.sign_up_password_not_icon);

       ImageView sign_up_password_second_done_icon = findViewById(R.id.sign_up_password_second_done_icon);
       ImageView sign_up_password_second_not_icon = findViewById(R.id.sign_up_password_second_not_icon);

       ImageView sign_up_email_done_icon = findViewById(R.id.sign_up_email_done_icon);
       ImageView sign_up_email_not_icon = findViewById(R.id.sign_up_email_not_icon);

       TextView textView_id_warning = findViewById(R.id.textView_id_warning);
       TextView textView_password_warning = findViewById(R.id.textView_password_warning);
       TextView textView_password_second_warning = findViewById(R.id.textView_password_second_warning);
       TextView textView_email_warning = findViewById(R.id.textView_email_warning);

       TextView button_sign_up = findViewById(R.id.button_sign_up);

       editText_sign_up_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {

               if(b) {

                   setNormalEditText(editText_sign_up_id, sign_up_id_done_icon, sign_up_id_not_icon, textView_id_warning);
                   idValidate = false;

               }else {

                   String userID = editText_sign_up_id.getText().toString();

                   if (idValidate) {
                       return;
                   }

                   if (userID.equals("")) {
                       setNotEditText(editText_sign_up_id, sign_up_id_done_icon, sign_up_id_not_icon, textView_id_warning, "아이디를 입력하세요.");
                       idValidate = false;

                   } else if (userID.length() > 15) {
                       setNotEditText(editText_sign_up_id, sign_up_id_done_icon, sign_up_id_not_icon, textView_id_warning, "아이디는 14자 이하입니다.");
                       idValidate = false;

                   } else {

                       Response.Listener<String> responseListener = new Response.Listener<String>() {

                           @Override
                           public void onResponse(String response) {

                               try {
                                   Log.d("@@@", response);
                                   JSONObject jsonResponse = new JSONObject(response);

                                   boolean success = jsonResponse.getBoolean("success");




                                   if (success) {
                                       setDoneEditText(editText_sign_up_id, sign_up_id_done_icon, sign_up_id_not_icon, textView_id_warning);
                                       idValidate = true;
                                   }

                               } catch (Exception e) {

                                   Log.d("@@@", "validate error");
                                   e.printStackTrace();


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
                   setNormalEditText(editText_sign_up_password, sign_up_password_done_icon, sign_up_password_not_icon, textView_password_warning);
                   passwordValidate = false;
               }else{

                   String userPassword = editText_sign_up_password.getText().toString();

                   if (userPassword.equals("")) {
                       setNotEditText(editText_sign_up_password, sign_up_password_done_icon, sign_up_password_not_icon, textView_password_warning, "비밀번호를 입력하세요.");
                       passwordValidate = false;

                   }else if (userPassword.length() > 15){
                       setNotEditText(editText_sign_up_password, sign_up_password_done_icon, sign_up_password_not_icon, textView_password_warning, "비밀번호는 14자 이하입니다.");
                       passwordValidate = false;

                   }else {

                       setDoneEditText(editText_sign_up_password, sign_up_password_done_icon, sign_up_password_not_icon, textView_password_warning);
                       passwordValidate = true;

                   }

               }

           }
       });

       editText_sign_up_password_second.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {


               if(b){
                   setNormalEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, sign_up_password_second_not_icon, textView_password_second_warning);
                   passwordCheckValidate = false;
               }else{

                   String userPassword = editText_sign_up_password.getText().toString();
                   String userPasswordCheck = editText_sign_up_password_second.getText().toString();

                   if (userPasswordCheck.equals("")) {
                       setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, sign_up_password_second_not_icon, textView_password_second_warning, "비밀번호를 재확인하세요.");
                       passwordCheckValidate = false;

                   }else if (!userPasswordCheck.equals(userPassword)){

                       setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, sign_up_password_second_not_icon, textView_password_second_warning, "비밀번호가 다릅니다.");
                       passwordCheckValidate = false;

                   }else{

                       setDoneEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, sign_up_password_second_not_icon, textView_password_second_warning);
                       passwordCheckValidate = true;
                   }

               }

           }
       });

       editText_sign_up_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {

               if(b){
                   setNormalEditText(editText_sign_up_email, sign_up_email_done_icon, sign_up_email_not_icon, textView_email_warning);
                   emailValidate = false;
               }else{

                   String userEmail = editText_sign_up_email.getText().toString();
                   Pattern p = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
                   Matcher m = p.matcher(userEmail);
                   Log.d("@@@", userEmail);


                   if (userEmail.equals("")) {
                       setNotEditText(editText_sign_up_email, sign_up_email_done_icon, sign_up_email_not_icon, textView_email_warning, "이메일을 입력하세요.");
                       emailValidate = false;

                   }else if (!m.matches()) {

                       setNotEditText(editText_sign_up_email, sign_up_email_done_icon, sign_up_email_not_icon, textView_email_warning, "이메일 형식을 입력하세요.");
                       emailValidate = false;

                   }else{

                       setDoneEditText(editText_sign_up_email, sign_up_email_done_icon, sign_up_email_not_icon, textView_email_warning);
                       emailValidate = true;

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

               editText_sign_up_id.clearFocus();
               editText_sign_up_password.clearFocus();
               editText_sign_up_password_second.clearFocus();
               editText_sign_up_email.clearFocus();

               if(idValidate && passwordValidate && passwordCheckValidate && emailValidate){

                   Response.Listener<String> responseListener = new Response.Listener<String>() {

                       @Override
                       public void onResponse(String response) {

                           try {
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

                   SignUpRequest signupRequest = new SignUpRequest(userID,userPassword, userEmail, responseListener);
                   RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                   queue.add(signupRequest);


               }else{
                   Toast.makeText(getApplicationContext(), "수정할 항목이 있습니다.", Toast.LENGTH_LONG).show();
               }

           }
       });


    }

    public void setNormalEditText(EditText e, ImageView i_done, ImageView i_not, TextView t){
        e.setBackgroundResource(R.drawable.layout_login_roundedbox);
        i_done.setVisibility(View.INVISIBLE);
        i_not.setVisibility(View.INVISIBLE);
        t.setVisibility(View.GONE);
    }

    public void setDoneEditText(EditText e, ImageView i_done, ImageView i_not, TextView t){
        e.setBackgroundResource(R.drawable.layout_login_roundedbox_done);
        i_done.setVisibility(View.VISIBLE);
        i_not.setVisibility(View.INVISIBLE);
        t.setVisibility(View.GONE);
    }

    public void setNotEditText(EditText e, ImageView i_done, ImageView i_not, TextView t, String msg){
        e.setBackgroundResource(R.drawable.layout_login_roundedbox_not);
        i_done.setVisibility(View.INVISIBLE);
        i_not.setVisibility(View.VISIBLE);
        t.setVisibility(View.VISIBLE);
        t.setText(msg);
    }

    @Override
    protected void onStop(){
       super.onStop();

    }

}
