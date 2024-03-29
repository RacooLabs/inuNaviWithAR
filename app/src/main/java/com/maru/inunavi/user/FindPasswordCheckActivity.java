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
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.search.SearchActivity;
import com.maru.inunavi.ui.timetable.search.SearchCategoryActivity;

import org.json.JSONObject;

public class FindPasswordCheckActivity extends AppCompatActivity {



    private String url = sessionURL;

    private boolean verifyCodeIsPassed = false;

    private String userEmail = "";
    private String verifyCode = "";

    private TextView find_password_check_timer;
    private PasswordTimer passwordTimer;



    @Override
    public void onBackPressed() {

        Intent intent = new Intent(FindPasswordCheckActivity.this, FindPasswordActivity.class);
        intent.putExtra("CallType", -2);
        setResult(Activity.RESULT_OK, intent);
        finish();
        overridePendingTransition(0, 0);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_find_password_check);


        if(getIntent() != null){
            userEmail = getIntent().getStringExtra("userEmail");
            verifyCode = getIntent().getStringExtra("verifyCode");
        }


        TextView textView_find_password_check_back = findViewById(R.id.textView_find_password_check_back);

        EditText editText_find_password_check_verifyCode = findViewById(R.id.editText_find_password_check_verifyCode);

        ImageView find_password_check_done_icon = findViewById(R.id.find_password_check_done_icon);

        TextView textView_warning = findViewById(R.id.textView_warning);

        TextView button_find_password_check = findViewById(R.id.button_find_password_check);

        //타이머 설정
        find_password_check_timer = findViewById(R.id.find_password_check_timer);
        passwordTimer = new PasswordTimer(180000, 1000, find_password_check_timer,
                editText_find_password_check_verifyCode, find_password_check_done_icon, textView_warning, "입력 시간이 초과되었습니다.\n이전으로 돌아가서 다시 요청해주세요.",
                button_find_password_check);

        passwordTimer.start();


        textView_find_password_check_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FindPasswordCheckActivity.this, FindPasswordActivity.class);
                intent.putExtra("CallType", -2);
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(0, 0);

            }
        });



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

                                    Intent returnIntent = new Intent(FindPasswordCheckActivity.this, FindPasswordActivity.class);
                                    returnIntent.putExtra("CallType", -1);
                                    setResult(Activity.RESULT_OK, returnIntent);
                                    finish();
                                    overridePendingTransition(0, 0);

                                    break;

                            }

                        }

                    }
                });



        button_find_password_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String verifyCodeInput = editText_find_password_check_verifyCode.getText().toString().trim();

                if (verifyCodeInput.equals("")) {
                    setNotEditText(editText_find_password_check_verifyCode, find_password_check_done_icon, textView_warning, "인증코드를 입력하세요.");
                    find_password_check_timer.setTextColor(getResources().getColor(R.color.not_color, null));
                    verifyCodeIsPassed = false;

                }else{

                    if(verifyCodeInput.equals(verifyCode)){

                        setDoneEditText(editText_find_password_check_verifyCode, find_password_check_done_icon, textView_warning);
                        find_password_check_timer.setTextColor(getResources().getColor(R.color.done_color, null));
                        verifyCodeIsPassed = true;

                    }else{

                        setNotEditText(editText_find_password_check_verifyCode, find_password_check_done_icon, textView_warning, "인증코드가 일치하지 않습니다.");
                        find_password_check_timer.setTextColor(getResources().getColor(R.color.not_color, null));
                        verifyCodeIsPassed = false;

                    }

                    editText_find_password_check_verifyCode.clearFocus();

                    if(verifyCodeIsPassed){

                        Intent intent = new Intent(FindPasswordCheckActivity.this, FindPasswordChangeActivity.class);
                        intent.putExtra("userEmail", userEmail);
                        backToMainLoginResultLauncher.launch(intent);

                    }

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
        i_done.setVisibility(View.INVISIBLE);
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
