package com.maru.inunavi.ui.timetable.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    ImageView tita_search_backButton;
    EditText tita_search_searchbar;
    TextView tita_search_option_button;
    RadioGroup tita_search_radioGroup;

    private String main_keyword = "";
    private String keyword_option = "과목명";
    private String target;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_search);

        ImageView tita_search_backButton = findViewById(R.id.tita_search_backButton);
        EditText tita_search_searchbar = findViewById(R.id.tita_search_searchbar);
        TextView tita_search_option_button = findViewById(R.id.tita_search_option_button);
        RadioGroup tita_search_radioGroup = findViewById(R.id.tita_search_radioGroup);


        //돌아가기 버튼
        tita_search_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  { finish(); }
        });

        tita_search_searchbar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i == EditorInfo.IME_ACTION_SEARCH){

                    main_keyword = tita_search_searchbar.getText().toString();

                    target = (IpAddress.isTest ? "http://192.168.0.106/inuNavi/LectureList.php" :
                            "http://219.248.233.170/project1_war_exploded/user/login")+ "?main_keyword=\"" + main_keyword + "\"&keyword_option=\"" + keyword_option
                            + "\"&major_option=\"전체\"" + "&sort_option=\"기본\"" + "&grade_option=\"전체\"" + "&kind_option=\"전체\"" + "&score_option=\"전체\"";

                    SearchBackgroundTask();

                }

                return false;
            }
        });

        
        //더 많은 옵션 콜백 리스너
        ActivityResultLauncher<Intent> searchOptionActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent intent = result.getData();
                            int CallType = intent.getIntExtra("CallType", 0);



                            String major = intent.getStringExtra("Major");

                            target = (IpAddress.isTest ? "http://192.168.0.106/inuNavi/LectureList.php" :
                                    "http://219.248.233.170/project1_war_exploded/user/login")+ "?main_keyword=\"" + main_keyword + "\"&keyword_option=\"" + keyword_option
                                    + "\"&major_option=\"전체\"" + "&sort_option=\"기본\"" + "&grade_option=\"전체\"" + "&kind_option=\"전체\"" + "&score_option=\"전체\"";

                            SearchBackgroundTask();

                        }
                    }
                });


        
        //더 많은 검색 옵션 버튼
        tita_search_option_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SearchActivity.this, SearchOptionActivity.class);
                searchOptionActivityResultLauncher.launch(intent);

            }
        });


        //키워드 옵션
        tita_search_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.tita_search_radioButton1:
                        keyword_option = "과목명";
                        break;

                    case R.id.tita_search_radioButton2:
                        keyword_option = "교수명";
                        break;

                    case R.id.tita_search_radioButton3:
                        keyword_option = "과목코드";
                        
                }
            }
        });



    }


    Disposable backgroundtask;

    public int value;
    boolean isCancel = false;


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
