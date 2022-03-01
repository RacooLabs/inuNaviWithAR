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

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindPasswordChangeActivity extends AppCompatActivity {



    private boolean newPasswordValidate = false; //사용자 비밀번호 체크
    private boolean newPasswordCheckValidate = false; //사용자 비밀번호 체크

    private String url = sessionURL;

    private String userEmail = "";

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(FindPasswordChangeActivity.this, FindPasswordCheckActivity.class);
        intent.putExtra("CallType", -1);
        setResult(Activity.RESULT_OK, intent);
        finish();
        overridePendingTransition(0, 0);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_find_password_change);

        TextView find_password_change_back_button = findViewById(R.id.textView_find_password_change_back);

        if(getIntent() != null){
            userEmail = getIntent().getStringExtra("userEmail");
        }

        find_password_change_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FindPasswordChangeActivity.this, FindPasswordCheckActivity.class);
                intent.putExtra("CallType", -1);
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(0, 0);

            }

        });


        EditText editText_find_password_change = findViewById(R.id.editText_find_password_change);
        EditText editText_find_password_change_second = findViewById(R.id.editText_find_password_change_second);


        ImageView find_password_change_done_icon = findViewById(R.id.find_password_change_done_icon);
        ImageView find_password_change_second_done_icon = findViewById(R.id.find_password_change_second_done_icon);


        TextView textView_password_warning = findViewById(R.id.textView_password_warning);
        TextView textView_password_second_warning = findViewById(R.id.textView_password_second_warning);

        TextView button_find_password_change = findViewById(R.id.button_find_password_change);


        editText_find_password_change.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(b){
                    setNormalEditText(editText_find_password_change, find_password_change_done_icon,  textView_password_warning);
                    newPasswordValidate = false;
                }else{

                    String userPassword = editText_find_password_change.getText().toString().trim();
                    String userPasswordCheck = editText_find_password_change_second.getText().toString().trim();

                    if (userPassword.contains(" ")){
                        setNotEditText(editText_find_password_change, find_password_change_done_icon,  textView_password_warning, "세 비밀번호에 공백이 있습니다.");
                        newPasswordValidate = false;

                    }else if (userPassword.equals("")) {
                        setNotEditText(editText_find_password_change, find_password_change_done_icon,  textView_password_warning, "새 비밀번호를 입력하세요.");
                        newPasswordValidate = false;

                    }else if (userPassword.length() > 15 || userPassword.length() < 6){
                        setNotEditText(editText_find_password_change, find_password_change_done_icon, textView_password_warning, "비밀번호는 6자 이상 20자 이하입니다.");
                        newPasswordValidate = false;


                    }else {

                        setDoneEditText(editText_find_password_change, find_password_change_done_icon,  textView_password_warning);
                        newPasswordValidate = true;

                        if(userPassword.equals(userPasswordCheck)){
                            setDoneEditText(editText_find_password_change_second, find_password_change_second_done_icon,  textView_password_second_warning);
                            newPasswordCheckValidate = true;
                        }else{
                            setNotEditText(editText_find_password_change_second, find_password_change_second_done_icon,  textView_password_second_warning, "새 비밀번호가 다릅니다.");
                            newPasswordCheckValidate = false;
                        }

                    }

                }

            }
        });


        editText_find_password_change_second.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {


                if(b){
                    setNormalEditText(editText_find_password_change_second, find_password_change_second_done_icon,  textView_password_second_warning);
                    newPasswordCheckValidate = false;
                }else{

                    String userPassword = editText_find_password_change.getText().toString();
                    String userPasswordCheck = editText_find_password_change_second.getText().toString();

                    if (userPasswordCheck.equals("")) {
                        setNotEditText(editText_find_password_change_second, find_password_change_second_done_icon,  textView_password_second_warning, "새 비밀번호를 재확인하세요.");
                        newPasswordCheckValidate = false;

                    }else if (!userPasswordCheck.equals(userPassword)){

                        setNotEditText(editText_find_password_change_second, find_password_change_second_done_icon,  textView_password_second_warning, "새 비밀번호가 다릅니다.");
                        newPasswordCheckValidate = false;

                    }else if (!newPasswordValidate) {

                        setNotEditText(editText_find_password_change_second, find_password_change_second_done_icon,  textView_password_second_warning, "");
                        textView_password_second_warning.setVisibility(View.GONE);

                    }else{

                        setDoneEditText(editText_find_password_change_second, find_password_change_second_done_icon,  textView_password_second_warning);
                        newPasswordCheckValidate = true;

                    }

                }

            }
        });

        button_find_password_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userNewPassword = editText_find_password_change.getText().toString();

                editText_find_password_change.clearFocus();
                editText_find_password_change_second.clearFocus();

                if(newPasswordValidate && newPasswordCheckValidate) {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {



                                JSONObject jsonResponse = new JSONObject(response);




                                boolean success = jsonResponse.getBoolean("success");

                                if (success) {
                                    Toast.makeText(getApplicationContext(), "비밀번호 변경을 완료하였습니다.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(FindPasswordChangeActivity.this, FindPasswordCheckActivity.class);
                                    intent.putExtra("CallType", -1);
                                    setResult(Activity.RESULT_OK, intent);
                                    finish();
                                    overridePendingTransition(0, 0);

                                } else {
                                    Toast.makeText(getApplicationContext(), "비밀번호 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {


                            }


                        }

                    };

                    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(userEmail, userNewPassword, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(FindPasswordChangeActivity.this);
                    queue.add(changePasswordRequest);

                }else{

                    Toast.makeText(getApplicationContext(), "수정할 항목이 있습니다.", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }



    public void setNormalEditText(EditText e, ImageView i_done, TextView t) {
        e.setBackgroundResource(R.drawable.layout_login_signup_round_box);
        i_done.setVisibility(View.INVISIBLE);
        t.setVisibility(View.GONE);
    }

    public void setDoneEditText(EditText e, ImageView i_done, TextView t) {
        e.setBackgroundResource(R.drawable.layout_login_signup_round_box_done);
        i_done.setVisibility(View.VISIBLE);
        t.setVisibility(View.GONE);
    }

    public void setNotEditText(EditText e, ImageView i_done, TextView t, String msg) {
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
