package com.maru.inunavi.ui.satisfied;

import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.MainActivity.cookieManager;
import static com.maru.inunavi.MainActivity.sessionURL;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.maru.inunavi.IpAddress;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.user.LoginActivity;
import com.maru.inunavi.R;


public class SatisfiedFragment extends Fragment {

    private String url = sessionURL;
    private String userID;
    public View root;
    public static String target;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.satisfied_fragment, container, false);
        LinearLayout frag_satisfied_login_box = root.findViewById(R.id.frag_satisfied_login_box);

        ConstraintLayout constraint_frag_satisfied_main = root.findViewById(R.id.constraint_frag_satisfied_main);
        Button button_frag_satisfied_login = root.findViewById(R.id.button_frag_satisfied_login);
        TextView textView_overview_button = root.findViewById(R.id.textView_overview_button);

        ImageView progressBar_amountOfMovement = root.findViewById(R.id.progressBar_amountOfMovement);
        ImageView progressBar_tightness = root.findViewById(R.id.progressBar_tightness);

        TextView satisfied_amountOfMovement_marker = root.findViewById(R.id.satisfied_amountOfMovement_marker);
        TextView satisfied_tightness_marker = root.findViewById(R.id.satisfied_tightness_marker);
        TextView satisfied_DistanceWeek = root.findViewById(R.id.satisfied_DistanceWeek);

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
                            frag_satisfied_login_box.setVisibility(View.INVISIBLE);
                            constraint_frag_satisfied_main.setVisibility(View.VISIBLE);

                            MainActivity.autoLogin = true;
                            if(MainActivity.autoLogin) {
                                // 자동 로그인 데이터 저장
                                SharedPreferences auto = getContext().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLoginEdit = auto.edit();
                                autoLoginEdit.putString("userId", userID);
                                autoLoginEdit.putBoolean("isAutoLogin", true);
                                autoLoginEdit.commit();

                            }

                            target = IpAddress.isTest ? "http://192.168.0.101/inuNavi/ScheduleList.php?id=\"" + userID +"\"":
                                    "http://" + DemoIP + "/user/select/class?id=" + userID;


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


            frag_satisfied_login_box.setVisibility(View.INVISIBLE);
            constraint_frag_satisfied_main.setVisibility(View.VISIBLE);

            // 정보 초기화

            int amountOfMovementPercent = 30;

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

            int tightnessPercent = 50;

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


            /*satisfied_amountOfMovement_marker;
            satisfied_tightness_marker;
            satisfied_DistanceWeek;*/


        }else{

            //로그인 버튼 리스너
            frag_satisfied_login_box.setVisibility(View.VISIBLE);
            constraint_frag_satisfied_main.setVisibility(View.INVISIBLE);

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

}