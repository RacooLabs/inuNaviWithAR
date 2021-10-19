package com.maru.inunavi.ui.timetable;

import static com.maru.inunavi.MainActivity.sessionURL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maru.inunavi.ui.timetable.search.SearchActivity;
import com.maru.inunavi.user.LoginActivity;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;


public class CalendarFragment extends Fragment {

    private String url = sessionURL;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.timetable_fragment, container, false);
        LinearLayout frag_tita_login_box = root.findViewById(R.id.frag_tita_login_box);
        ConstraintLayout constraint_frag_tita_main = root.findViewById(R.id.constraint_frag_tita_main);

        Button button_frag_tita_login = root.findViewById(R.id.button_frag_tita_login);
        ImageView imageView_frag_tita_setting = root.findViewById(R.id.imageView_frag_tita_setting);
        ImageView imageView_frag_tita_add = root.findViewById(R.id.imageView_frag_tita_add);

        frag_tita_login_box.setVisibility(View.VISIBLE);
        constraint_frag_tita_main.setVisibility(View.INVISIBLE);

        CookieManager cookieManager = ((MainActivity)getActivity()).getCookieManager();


        //설정 콜백
        ActivityResultLauncher<Intent> logoutActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);

                            if(CallType == 1001) {

                                // 로그아웃 요청

                                frag_tita_login_box.setVisibility(View.VISIBLE);
                                constraint_frag_tita_main.setVisibility(View.INVISIBLE);
                                cookieManager.removeAllCookies(null);

                            }


                        }
                    }
                });


        //설정 버튼
        imageView_frag_tita_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), SettingActivity.class);
                logoutActivityResultLauncher.launch(intent);

            }
        });


        //강의 추가 콜백
        ActivityResultLauncher<Intent> searchActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);

                            if(CallType == 2001) {


                            }


                        }
                    }
                });


        //강의 추가 버튼
        imageView_frag_tita_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                searchActivityResultLauncher.launch(intent);

            }
        });



        //로그인 콜백
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 2);
                            String userID = intent.getStringExtra("userID");

                            //로그인 요청, 쿠키 저장

                            if(CallType == 2) {
                                ((BottomNavigationView) getActivity().findViewById(R.id.nav_view)).setSelectedItemId(R.id.navigation_satisfied);
                            }
                            cookieManager.setCookie(url,"cookieKey="+userID);
                            frag_tita_login_box.setVisibility(View.INVISIBLE);
                            constraint_frag_tita_main.setVisibility(View.VISIBLE);
                        }
                    }
                });


        //로그인 버튼
        button_frag_tita_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                someActivityResultLauncher.launch(intent);

            }
        });


        if(cookieManager.getCookie(url)!=null &&
                !cookieManager.getCookie(url).equals("")){

            Log.d("@@@ fragmentcalendar : 50", cookieManager.getCookie(url));
            frag_tita_login_box.setVisibility(View.INVISIBLE);
            constraint_frag_tita_main.setVisibility(View.VISIBLE);
            
            //설정버튼 액티비티 리스너

        }else{

            //Log.d("@@@ fragmentcalendar : 61", cookieManager.getCookie(url).toString());

            //로그인 버튼 리스너

            frag_tita_login_box.setVisibility(View.VISIBLE);
            constraint_frag_tita_main.setVisibility(View.INVISIBLE);

        }

        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}