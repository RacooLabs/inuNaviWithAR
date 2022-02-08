package com.maru.inunavi.ui.satisfied;

import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.MainActivity.cookieManager;
import static com.maru.inunavi.MainActivity.sessionURL;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.maru.inunavi.IpAddress;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.ui.map.NaviInfo;
import com.maru.inunavi.user.LoginActivity;
import com.maru.inunavi.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SatisfiedFragment extends Fragment {

    private String url = sessionURL;
    private String userID;
    public View root;
    public static String target;

    private LinearLayout frag_satisfied_login_box;
    private ConstraintLayout constraint_frag_satisfied_main;
    private ConstraintLayout satisfied_blur_window;

    ImageView progressBar_amountOfMovement;
    ImageView progressBar_tightness;

    TextView satisfied_amountOfMovement_marker;
    TextView satisfied_tightness_marker;
    TextView satisfied_DistanceWeek;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.satisfied_fragment, container, false);
        frag_satisfied_login_box = root.findViewById(R.id.frag_satisfied_login_box);

        constraint_frag_satisfied_main = root.findViewById(R.id.constraint_frag_satisfied_main);
        satisfied_blur_window = root.findViewById(R.id.satisfied_blur_window);

        Button button_frag_satisfied_login = root.findViewById(R.id.button_frag_satisfied_login);
        TextView textView_overview_button = root.findViewById(R.id.textView_overview_button);

        progressBar_amountOfMovement = root.findViewById(R.id.progressBar_amountOfMovement);
        progressBar_tightness = root.findViewById(R.id.progressBar_tightness);

        satisfied_amountOfMovement_marker = root.findViewById(R.id.satisfied_amountOfMovement_marker);
        satisfied_tightness_marker = root.findViewById(R.id.satisfied_tightness_marker);
        satisfied_DistanceWeek = root.findViewById(R.id.satisfied_DistanceWeek);

        ActivityResultLauncher<Intent> loginActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 2);
                            String userID = intent.getStringExtra("userID");

                            //로그인 요청, 쿠키 저장

                            cookieManager.setCookie(url,"cookieKey="+userID);
                            frag_satisfied_login_box.setVisibility(View.GONE);
                            constraint_frag_satisfied_main.setVisibility(View.VISIBLE);
                            satisfied_blur_window.setVisibility(View.GONE);

                            MainActivity.autoLogin = true;
                            if(MainActivity.autoLogin) {
                                // 자동 로그인 데이터 저장
                                SharedPreferences auto = getContext().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLoginEdit = auto.edit();
                                autoLoginEdit.putString("userId", userID);
                                autoLoginEdit.putBoolean("isAutoLogin", true);
                                autoLoginEdit.commit();

                            }

                            GetAnalysisResultBackgroundTask();

                        }
                    }
                });


        button_frag_satisfied_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                loginActivityResultLauncher.launch(intent);

            }
        });

        if(cookieManager.getCookie(url) != null && !cookieManager.getCookie(url).equals("")){

            userID = MainActivity.cookieManager.getCookie(url).replace("cookieKey=", "");

            target = IpAddress.isTest ? "http://192.168.0.101/inuNavi/ScheduleList.php?id=\"" + userID +"\"":
                    "http://" + DemoIP + "/user/select/class?id=" + userID;


            frag_satisfied_login_box.setVisibility(View.GONE);
            constraint_frag_satisfied_main.setVisibility(View.VISIBLE);
            satisfied_blur_window.setVisibility(View.GONE);

            GetAnalysisResultBackgroundTask();

        }else{

            //로그인 버튼 리스너
            frag_satisfied_login_box.setVisibility(View.VISIBLE);
            constraint_frag_satisfied_main.setVisibility(View.INVISIBLE);
            satisfied_blur_window.setVisibility(View.GONE);

        }


        textView_overview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapOverviewActivity.class);
                startActivity(intent);
            }
        });



        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public int DpToPixel(int dp) {

        try{
            Resources r = getContext().getResources();

            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp,
                    r.getDisplayMetrics()
            );

            return px;

        }catch (Exception e){

        }

        return 0;

    }

    // 학기 경로 분석 결과를 가져오는 서버 통신 코드
    Disposable getAnalysisResultTask;

    void GetAnalysisResultBackgroundTask() {

        getAnalysisResultTask = Observable.fromCallable(() -> {

            // doInBackground


            String target = (IpAddress.isTest ? "http://192.168.0.101/inuNavi/GetAnalysisResult.php" :
                    "http://" + DemoIP + "/selectLecture")+ "?userID=\"" + MainActivity.cookieManager.getCookie(url).replace("cookieKey=", "") + "\"";


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
                Log.d("@@@satisfied Fragment 274", e.toString());
            }

            return null;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorReturn(___ -> "{response : []}").subscribe((result) -> {

            // onPostExecute

            try {

                Log.d("@@@satisfied fragment 285", result);

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");

                int distancePercentage = 0;
                int tightnessPercentage = 0;
                int totalDistance = 0;

                int count = 0;

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);

                    distancePercentage = object.getInt("distancePercentage");
                    tightnessPercentage = object.getInt("tightnessPercentage");
                    totalDistance = object.getInt("totalDistance");

                    count++;

                }

                if(count == 0){

                    frag_satisfied_login_box.setVisibility(View.GONE);
                    constraint_frag_satisfied_main.setVisibility(View.GONE);
                    satisfied_blur_window.setVisibility(View.VISIBLE);

                }else {

                    // 정보 초기화

                    int amountOfMovementPercent = distancePercentage;

                    progressBar_amountOfMovement.post(new Runnable() {
                        @Override
                        public void run() {
                            int progressBar_amountOfMovement_width = progressBar_amountOfMovement.getWidth() - DpToPixel(24);
                            ConstraintLayout.LayoutParams satisfied_amountOfMovement_marker_params = (ConstraintLayout.LayoutParams)satisfied_amountOfMovement_marker.getLayoutParams();

                            int leftMargin = (int)(progressBar_amountOfMovement_width*amountOfMovementPercent * 0.01 - DpToPixel(7));

                            satisfied_amountOfMovement_marker_params.setMargins(leftMargin, 0,0,0);
                            satisfied_amountOfMovement_marker.setLayoutParams(satisfied_amountOfMovement_marker_params);

                        }
                    });


                    View satisfied_tightness_marker_parent = (View)satisfied_tightness_marker.getParent();

                    int tightnessPercent = tightnessPercentage;

                    satisfied_tightness_marker_parent.post(new Runnable() {
                        @Override
                        public void run() {

                            int progressBar_tightness_width = satisfied_tightness_marker_parent.getWidth() - DpToPixel(24) ;
                            ConstraintLayout.LayoutParams satisfied_tightness_marker_params = (ConstraintLayout.LayoutParams)satisfied_tightness_marker.getLayoutParams();

                            int leftMargin = (int)(progressBar_tightness_width*tightnessPercent * 0.01 - DpToPixel(7));

                            satisfied_tightness_marker_params.setMargins(leftMargin, 0,0,0);
                            satisfied_tightness_marker.setLayoutParams(satisfied_tightness_marker_params);

                        }
                    });

                    int totalDistanceInput = totalDistance;

                    satisfied_DistanceWeek.post(new Runnable() {
                        @Override
                        public void run() {

                            satisfied_DistanceWeek.setText(totalDistanceInput+"m");

                        }
                    });


                }

            } catch (Exception e) {
                e.printStackTrace();

            }

            getAnalysisResultTask.dispose();

        });

    }

}