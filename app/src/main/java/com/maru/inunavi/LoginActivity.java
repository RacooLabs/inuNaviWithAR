package com.maru.inunavi;

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

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        TextView signUpButton = findViewById(R.id.button_moveTo_sign_up);

        EditText editText_id = findViewById(R.id.editText_id);
        EditText editText_password = findViewById(R.id.editText_password);

        TextView textView_login_id_warning = findViewById(R.id.textView_login_id_warning);
        TextView textView_login_password_warning = findViewById(R.id.textView_login_password_warning);

        TextView button_login = findViewById(R.id.button_login);



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = editText_id.getText().toString();
                String userPassword = editText_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.d("@@@",response.toString());
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("CallType", 1);
                                intent.putExtra("userID",userID);
                                setResult(Activity.RESULT_OK, intent);
                                finish();

                            }else{
                                setNotEditText(editText_id, textView_login_id_warning,"");
                                textView_login_id_warning.setVisibility(View.GONE);
                                setNotEditText(editText_password, textView_login_password_warning,"계정을 다시 확인하세요.");

                            }


                        }catch (Exception e){

                            setNotEditText(editText_password, textView_login_password_warning, "서버 연결 실패");

                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);

            }
        });






    }

    public void setNormalEditText(EditText e, TextView t){
        e.setBackgroundResource(R.drawable.layout_login_roundedbox);
        t.setVisibility(View.GONE);
    }

    public void setNotEditText(EditText e, TextView t, String msg){
        e.setBackgroundResource(R.drawable.layout_login_roundedbox_not);
        t.setVisibility(View.VISIBLE);
        t.setText(msg);
    }

}
