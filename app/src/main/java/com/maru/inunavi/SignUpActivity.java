package com.maru.inunavi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

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



    }

}
