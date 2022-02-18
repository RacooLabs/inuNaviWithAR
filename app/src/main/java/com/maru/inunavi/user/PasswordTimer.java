package com.maru.inunavi.user;

import android.media.Image;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.maru.inunavi.R;

import java.text.DecimalFormat;

public class PasswordTimer extends CountDownTimer {

    TextView textView;
    EditText editText_find_password_check_verifyCode;
    ImageView find_password_check_done_icon;
    TextView textView_warning;
    String msg;
    TextView button_find_password_check;


    public PasswordTimer(long millisInFuture, long countDownInterval, TextView textView
    , EditText editText_find_password_check_verifyCode, ImageView find_password_check_done_icon, TextView textView_warning, String msg,
                         TextView button_find_password_check)
    {
        super(millisInFuture, countDownInterval);
        this.textView = textView;

        this.editText_find_password_check_verifyCode = editText_find_password_check_verifyCode;
        this.find_password_check_done_icon = find_password_check_done_icon;
        this.textView_warning = textView_warning;
        this.msg = msg;
        this.button_find_password_check = button_find_password_check;


    }

    @Override
    public void onTick(long millisUntilFinished) {

        DecimalFormat df = new DecimalFormat("00");
        int minute = (int)(millisUntilFinished/1000/60);
        int second = (int)(millisUntilFinished/1000)%60;

        textView.setText(minute+":"+df.format(second));

    }

    @Override
    public void onFinish() {

        textView.setText("0:00");
        setNotEditText(editText_find_password_check_verifyCode, find_password_check_done_icon, textView_warning, msg);
        button_find_password_check.setEnabled(false);
        editText_find_password_check_verifyCode.setFocusable(false);

    }

    public void setNotEditText(EditText e, ImageView i_done, TextView t, String msg){
        e.setBackgroundResource(R.drawable.layout_login_signup_round_box_not);
        i_done.setVisibility(View.INVISIBLE);
        t.setVisibility(View.VISIBLE);
        t.setText(msg);
    }

}
