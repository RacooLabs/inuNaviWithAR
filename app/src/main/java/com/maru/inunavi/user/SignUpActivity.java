package com.maru.inunavi.user;

import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.IpAddress.DemoIP_ClientTest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.maru.inunavi.IpAddress;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.search.SearchOptionAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {

    private String userEmail;
    private String userPassword;


    private boolean emailValidate = false; //사용자 이메일 가능 체크
    private boolean passwordValidate = false; //사용자 비밀번호 체크
    private boolean passwordCheckValidate = false; //사용자 비밀번호 체크
    private boolean majorValidate = false; //사용자 전공 체크

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_activity_signup);

        TextView sign_up_back_button = findViewById(R.id.textView_sign_up_back);

        sign_up_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });


        EditText editText_sign_up_email = findViewById(R.id.editText_sign_up_email);
        EditText editText_sign_up_password = findViewById(R.id.editText_sign_up_password);
        EditText editText_sign_up_password_second = findViewById(R.id.editText_sign_up_password_second);
        EditText editText_sign_up_major = findViewById(R.id.editText_sign_up_major);


        ImageView sign_up_email_done_icon = findViewById(R.id.sign_up_email_done_icon);
        ImageView sign_up_password_done_icon = findViewById(R.id.sign_up_password_done_icon);
        ImageView sign_up_password_second_done_icon = findViewById(R.id.sign_up_password_second_done_icon);
        ImageView sign_up_major_arrow = findViewById(R.id.sign_up_major_arrow);


        TextView textView_email_warning = findViewById(R.id.textView_email_warning);
        TextView textView_password_warning = findViewById(R.id.textView_password_warning);
        TextView textView_password_second_warning = findViewById(R.id.textView_password_second_warning);
        TextView textView_major_warning = findViewById(R.id.textView_major_warning);


        TextView button_sign_up = findViewById(R.id.button_sign_up);

        editText_sign_up_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b) {
                    setNormalEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning);
                    emailValidate = false;
                } else {

                    String userEmail = editText_sign_up_email.getText().toString().trim();
                    Pattern p = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
                    Matcher m = p.matcher(userEmail);
                    Log.d("@@@", userEmail);


                    if (userEmail.equals("")) {
                        setNotEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning, "이메일을 입력하세요.");
                        emailValidate = false;

                    } else if (!m.matches()) {

                        setNotEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning, "이메일 형식을 입력하세요.");
                        emailValidate = false;

                    } else {

                        Response.Listener<String> responseListener = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                try {

                                    Log.d("@@@", "signupactivity_107 : " + response);
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");


                                    if (!success) {
                                        setDoneEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning);
                                        emailValidate = true;
                                    } else {
                                        setNotEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning, "이메일이 이미 존재합니다.");
                                        emailValidate = false;
                                    }

                                } catch (Exception e) {

                                    Log.d("@@@", "validate error");
                                    e.printStackTrace();

                                    setNotEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning, "서버 연결 실패");
                                    emailValidate = false;


                                }


                            }

                        };

                        ValidateRequest validateRequest = new ValidateRequest(userEmail, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                        queue.add(validateRequest);

                    }

                }


            }
        });


        editText_sign_up_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b) {
                    setNormalEditText(editText_sign_up_password, sign_up_password_done_icon, textView_password_warning);
                    passwordValidate = false;
                } else {

                    String userEmail = editText_sign_up_email.getText().toString().trim();
                    String userPassword = editText_sign_up_password.getText().toString().trim();
                    String userPasswordCheck = editText_sign_up_password_second.getText().toString().trim();

                    if (userPassword.contains(" ")) {
                        setNotEditText(editText_sign_up_password, sign_up_password_done_icon, textView_password_warning, "비밀번호에 공백이 있습니다.");
                        passwordValidate = false;

                    } else if (userPassword.equals("")) {
                        setNotEditText(editText_sign_up_password, sign_up_password_done_icon, textView_password_warning, "비밀번호를 입력하세요.");
                        passwordValidate = false;

                    } else if (userPassword.length() > 15 || userPassword.length() < 6) {
                        setNotEditText(editText_sign_up_password, sign_up_password_done_icon, textView_password_warning, "비밀번호는 6자 이상 20자 이하입니다.");
                        passwordValidate = false;

                    } else {

                        setDoneEditText(editText_sign_up_password, sign_up_password_done_icon, textView_password_warning);
                        passwordValidate = true;

                        if (userPassword.equals(userPasswordCheck)) {
                            setDoneEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, textView_password_second_warning);
                            passwordCheckValidate = true;
                        } else {
                            setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, textView_password_second_warning, "비밀번호가 다릅니다.");
                            passwordCheckValidate = false;
                        }

                    }

                }

            }
        });

        editText_sign_up_password_second.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {


                if (b) {
                    setNormalEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, textView_password_second_warning);
                    passwordCheckValidate = false;
                } else {

                    String userPassword = editText_sign_up_password.getText().toString();
                    String userPasswordCheck = editText_sign_up_password_second.getText().toString();

                    if (userPasswordCheck.equals("")) {
                        setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, textView_password_second_warning, "비밀번호를 재확인하세요.");
                        passwordCheckValidate = false;

                    } else if (!userPasswordCheck.equals(userPassword)) {

                        setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, textView_password_second_warning, "비밀번호가 다릅니다.");
                        passwordCheckValidate = false;

                    } else if (!passwordValidate) {

                        setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, textView_password_second_warning, "");
                        textView_password_second_warning.setVisibility(View.GONE);


                    } else {

                        setDoneEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, textView_password_second_warning);
                        passwordCheckValidate = true;

                    }

                }

            }
        });

        //전공 정보 추가

        //더 많은 옵션 콜백 리스너
        ActivityResultLauncher<Intent> signUpMajorResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent intent = result.getData();
                            int CallType = intent.getIntExtra("CallType", 0);

                            if(CallType == 1){
                                String major = intent.getStringExtra("userMajor");
                                editText_sign_up_major.setText(major);
                            }

                        }
                    }
                });

        editText_sign_up_major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText_sign_up_email.clearFocus();
                editText_sign_up_password.clearFocus();
                editText_sign_up_password_second.clearFocus();

                Intent intent = new Intent(SignUpActivity.this, SignUpMajorActivity.class);
                signUpMajorResultLauncher.launch(intent);

            }
        });




        editText_sign_up_major.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!charSequence.toString().equals("") || !charSequence.toString().isEmpty()) {
                    majorValidate = true;
                    editText_sign_up_major.setBackgroundResource(R.drawable.layout_login_round_box_done);
                    sign_up_major_arrow.setVisibility(View.VISIBLE);
                    textView_major_warning.setVisibility(View.INVISIBLE);

                } else {
                    majorValidate = false;
                    editText_sign_up_major.setBackgroundResource(R.drawable.layout_login_signup_round_box_not);
                    sign_up_major_arrow.setVisibility(View.VISIBLE);
                    textView_major_warning.setVisibility(View.VISIBLE);
                    textView_major_warning.setText("전공을 선택하세요.");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }

        });


        button_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = editText_sign_up_email.getText().toString().trim();
                String userPassword = editText_sign_up_password.getText().toString();
                String userPasswordCheck = editText_sign_up_password_second.getText().toString();
                String userMajor = editText_sign_up_major.getText().toString();

                editText_sign_up_email.clearFocus();
                editText_sign_up_password.clearFocus();
                editText_sign_up_password_second.clearFocus();
                editText_sign_up_email.clearFocus();


                Pattern p = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
                Matcher m = p.matcher(userEmail);
                Log.d("@@@", userEmail);


                if (userPassword.equals("")) {

                    setNotEditText(editText_sign_up_password, sign_up_password_done_icon, textView_password_warning, "비밀번호를 입력하세요.");
                    passwordValidate = false;

                }

                if (userPasswordCheck.equals("")) {

                    setNotEditText(editText_sign_up_password_second, sign_up_password_second_done_icon, textView_password_second_warning, "비밀번호를 재확인하세요.");
                    passwordCheckValidate = false;

                }

                if (!majorValidate) {

                    editText_sign_up_major.setBackgroundResource(R.drawable.layout_login_signup_round_box_not);
                    sign_up_major_arrow.setVisibility(View.VISIBLE);
                    textView_major_warning.setVisibility(View.VISIBLE);
                    textView_major_warning.setText("전공을 선택하세요.");
                    majorValidate = false;

                }

                if (userEmail.equals("")) {
                    setNotEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning, "이메일을 입력하세요.");
                    emailValidate = false;

                } else if (!m.matches()) {

                    setNotEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning, "이메일 형식을 입력하세요.");
                    emailValidate = false;


                } else {

                    Response.Listener<String> responseEmailListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            try {

                                Log.d("@@@", "signupactivity_107 : " + response);
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");


                                if (!success) {
                                    setDoneEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning);
                                    emailValidate = true;

                                    if (emailValidate && passwordValidate && passwordCheckValidate && majorValidate) {

                                        Response.Listener<String> responseListener = new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {

                                                try {

                                                    Log.d("@@@", "signupactivity_329 : " + response);

                                                    JSONObject jsonResponse = new JSONObject(response);

                                                    boolean success = jsonResponse.getBoolean("success");

                                                    if (success) {
                                                        Toast.makeText(getApplicationContext(), "회원 등록을 완료하였습니다.", Toast.LENGTH_SHORT).show();

                                                        finish();
                                                        overridePendingTransition(0, 0);

                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                                    }

                                                } catch (Exception e) {

                                                    e.printStackTrace();

                                                }


                                            }

                                        };

                                        SignUpRequest signupRequest = new SignUpRequest(userEmail, userPassword, userMajor, responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                                        queue.add(signupRequest);


                                    } else {
                                        Toast.makeText(getApplicationContext(), "수정할 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                                    }

                                } else {

                                    setNotEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning, "이메일이 이미 존재합니다.");
                                    emailValidate = false;
                                }


                            } catch (Exception e) {

                                Log.d("@@@", "validate error");
                                e.printStackTrace();

                                setNotEditText(editText_sign_up_email, sign_up_email_done_icon, textView_email_warning, "서버 연결 실패");
                                emailValidate = false;


                            }


                        }

                    };

                    ValidateRequest validateEmailRequest = new ValidateRequest(userEmail, responseEmailListener);
                    RequestQueue queueEmail = Volley.newRequestQueue(SignUpActivity.this);
                    queueEmail.add(validateEmailRequest);


                }
            }
        });


    }

    public void setNormalEditText(EditText e, ImageView i_done, TextView t) {
        e.setBackgroundResource(R.drawable.layout_login_round_box);
        i_done.setVisibility(View.INVISIBLE);
        t.setVisibility(View.GONE);
    }

    public void setDoneEditText(EditText e, ImageView i_done, TextView t) {
        e.setBackgroundResource(R.drawable.layout_login_round_box_done);
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
    protected void onStop() {
        super.onStop();

    }


}
