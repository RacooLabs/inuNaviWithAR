package com.maru.inunavi.user;

import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.IpAddress.DemoIP_ClientTest;
import static com.maru.inunavi.MainActivity.sessionURL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import com.maru.inunavi.AppInfo;
import com.maru.inunavi.IpAddress;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.SettingAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpMajorActivity extends AppCompatActivity {


    private String url = sessionURL;

    private RecyclerView majorRecyclerView;
    private SignUpMajorAdapter signUpMajorAdapter;

    public ArrayList<String> majorArray = new ArrayList<>();

    private ImageView sign_up_major_backButton;



    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sign_up_major);

        sign_up_major_backButton = findViewById(R.id.sign_up_major_backButton);


        //돌아가기 버튼
        sign_up_major_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);

            }
        });

        GetTimetableInfoTask();

        /*majorArray.clear();
        majorArray.remove("전체");
        majorArray.add("컴퓨터공학부");
        majorArray.add("정보통신공학과");
        majorArray.add("임베디드");

        majorRecyclerView = findViewById(R.id.sign_up_major_recycler_view);
        majorRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        signUpMajorAdapter =  new SignUpMajorAdapter(majorArray);
        majorRecyclerView.setAdapter(signUpMajorAdapter);


        //어댑터 콜백 리스너
        signUpMajorAdapter.setOnItemClickListener(new SignUpMajorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent = new Intent(SignUpMajorActivity.this, SignUpActivity.class);
                intent.putExtra("CallType", 1);
                intent.putExtra("userMajor", majorArray.get(position));
                setResult(Activity.RESULT_OK, intent);
                finish();
                overridePendingTransition(0, 0);


            }

        }) ;

        */

    }

    Disposable getTimetableInfoTask;

    void GetTimetableInfoTask() {

        getTimetableInfoTask = Observable.fromCallable(() -> {

            // doInBackground

            String target = (IpAddress.isTest ? "http://"+ DemoIP_ClientTest +"/inuNavi/GetTimetableInfo.php" :
                    "http://" + DemoIP + "/getTimeTableInfo");

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

            }

            return null;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorReturn(___ -> "{response : []}").subscribe((result) -> {

            // onPostExecute

            try {



                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");


                String majorArrayString = "";

                int count = 0;

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);


                    majorArrayString = object.getString("majorArrayString");
                    count++;

                }

                if(count == 0){



                }else {


                    majorArray.clear();

                    majorArrayString.trim();
                    majorArrayString.replaceAll(" ", "");


                    majorArray = new ArrayList<String>(Arrays.asList(majorArrayString.split(",")));

                    majorArray.remove("전체");

                    Collections.sort(majorArray);

                    majorRecyclerView = findViewById(R.id.sign_up_major_recycler_view);
                    majorRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

                    signUpMajorAdapter =  new SignUpMajorAdapter(majorArray);
                    majorRecyclerView.setAdapter(signUpMajorAdapter);



                    //어댑터 콜백 리스너
                    signUpMajorAdapter.setOnItemClickListener(new SignUpMajorAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {

                            Intent intent = new Intent(SignUpMajorActivity.this, SignUpActivity.class);
                            intent.putExtra("CallType", 1);
                            intent.putExtra("userMajor", majorArray.get(position));
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                            overridePendingTransition(0, 0);


                        }

                    }) ;

                }

            } catch (Exception e) {

            }

            getTimetableInfoTask.dispose();

        });

    }


}
