package com.maru.inunavi.ui.satisfied;

import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.MainActivity.cookieManager;
import static com.maru.inunavi.MainActivity.sessionURL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maru.inunavi.IpAddress;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.user.LoginActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.recommend.RecommendFragment;


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


        }else{

            //로그인 버튼 리스너
            frag_satisfied_login_box.setVisibility(View.VISIBLE);
            constraint_frag_satisfied_main.setVisibility(View.INVISIBLE);

        }



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}