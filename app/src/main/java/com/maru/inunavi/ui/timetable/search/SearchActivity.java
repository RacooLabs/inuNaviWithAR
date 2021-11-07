package com.maru.inunavi.ui.timetable.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.maru.inunavi.IpAddress;
import com.maru.inunavi.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {


    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_search);

        ImageView tita_search_backButton = findViewById(R.id.tita_search_backButton);
        EditText tita_search_searchbar = findViewById(R.id.tita_search_searchbar);
        TextView tita_search_option_button = findViewById(R.id.tita_search_option_button);

        //돌아가기 버튼
        tita_search_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  { finish(); }
        });

        tita_search_searchbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i == EditorInfo.IME_ACTION_SEARCH){

                    SearchBackgroundTask();

                }

                return false;
            }
        });

        //더 많은 검색 옵션 버튼
        tita_search_option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SearchActivity.this, SearchOptionActivity.class);
                startActivity(intent);

            }
        });

    }


    Disposable backgroundtask;

    public int value;
    boolean isCancel = false;
    private String target = IpAddress.isTest ? "http://192.168.0.106/inuNavi/LectureList.php" :
            "http://219.248.233.170/project1_war_exploded/user/login";


    void SearchBackgroundTask() {
        backgroundtask = Observable.fromCallable(() -> {
            // doInBackground

            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("@@@search activity 106", e.toString());
            }

            return null;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) .subscribe((result) -> {
                    // onPostExecute

            try {

                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                dialog = builder.setMessage(result).setPositiveButton("확인", null).create();
                dialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

            backgroundtask.dispose();


        });


    }




}
