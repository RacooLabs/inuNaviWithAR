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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.SettingActivity;

import org.json.JSONObject;

public class FindPasswordActivity extends AppCompatActivity {


    private boolean userEmailIsExist = false;

    private String url = sessionURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_find_password);

        TextView textView_find_password_back = findViewById(R.id.textView_find_password_back);

        textView_find_password_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

       EditText editText_find_password_email = findViewById(R.id.editText_find_password_email);
       
       ImageView find_password_done_icon = findViewById(R.id.find_password_done_icon);

       TextView textView_warning = findViewById(R.id.textView_warning);

       TextView button_find_password = findViewById(R.id.button_find_password);


        ActivityResultLauncher<Intent> backToMainLoginResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);

                            switch (CallType){

                                case -1:

                                    finish();
                                    break;

                            }

                        }

                    }
                });


        button_find_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = editText_find_password_email.getText().toString().trim();

                if (userEmail.equals("")) {
                    setNotEditText(editText_find_password_email, find_password_done_icon, textView_warning, "이메일을 입력하세요.");
                    userEmailIsExist = false;

                }else{

                    Response.Listener<String> responseValidateListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {

                                Log.d("@@@", "signupactivity_107 : " + response);
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if (!success) {
                                    setDoneEditText(editText_find_password_email, find_password_done_icon, textView_warning);
                                    userEmailIsExist = true;

                                }else{
                                    setNotEditText(editText_find_password_email, find_password_done_icon,  textView_warning, "이메일이 존재하지 않습니다.");
                                    userEmailIsExist = false;
                                }

                                editText_find_password_email.clearFocus();

                                if(userEmailIsExist) {

                                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {

                                            try {

                                                Log.d("@@@", "findpasswordactivity104 : " + response);

                                                JSONObject jsonResponse = new JSONObject(response);
                                                Log.d("@@@ findpasswordactivity104 ", response);

                                                boolean success = jsonResponse.getBoolean("success");
                                                String verifyCode = jsonResponse.getString("verifyCode");



                                                if (success) {

                                                    Toast.makeText(getApplicationContext(), "이메일에 인증코드를 전송했습니다." , Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(FindPasswordActivity.this, FindPasswordCheckActivity.class);
                                                    intent.putExtra("userEmail", userEmail);
                                                    intent.putExtra("verifyCode", verifyCode);
                                                    backToMainLoginResultLauncher.launch(intent);


                                                } else {
                                                    Toast.makeText(getApplicationContext(), "이메일 인증 요청에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                                }

                                            } catch (Exception e) {

                                                e.printStackTrace();

                                            }


                                        }

                                    };

                                    FindPasswordRequest findPasswordRequest = new FindPasswordRequest(userEmail, responseListener);
                                    RequestQueue validateEmailQueue = Volley.newRequestQueue(FindPasswordActivity.this);
                                    validateEmailQueue.add(findPasswordRequest);

                                }


                            } catch (Exception e) {

                                Log.d("@@@", "validate error");
                                e.printStackTrace();

                                setNotEditText(editText_find_password_email, find_password_done_icon,  textView_warning, "서버 연결 실패");
                                userEmailIsExist = false;

                            }

                        }

                    };

                    ValidateRequest validateRequest = new ValidateRequest(userEmail, responseValidateListener);
                    RequestQueue queueValidate = Volley.newRequestQueue(FindPasswordActivity.this);
                    queueValidate.add(validateRequest);

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
