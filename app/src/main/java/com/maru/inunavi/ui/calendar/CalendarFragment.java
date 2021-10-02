package com.maru.inunavi.ui.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maru.inunavi.LoginActivity;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;


public class CalendarFragment extends Fragment {

    //private MapViewModel mapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        LinearLayout frag_cal_login_box = root.findViewById(R.id.frag_cal_login_box);
        ConstraintLayout constraint_frag_cal_main = root.findViewById(R.id.constraint_frag_cal_main);

        Button button_frag_cal_login = root.findViewById(R.id.button_frag_cal_login);

        frag_cal_login_box.setVisibility(View.VISIBLE);
        constraint_frag_cal_main.setVisibility(View.INVISIBLE);

        CookieManager cookieManager = ((MainActivity)getActivity()).getCookieManager();

        //cookieManager.removeAllCookies(null);

        if(cookieManager.getCookie("http://192.168.55.162/inuNavi/")!=null &&
                !cookieManager.getCookie("http://192.168.55.162/inuNavi/").equals("")){

            Log.d("@@@ fragmentcalendar : 50", cookieManager.getCookie("http://192.168.55.162/inuNavi/"));
            frag_cal_login_box.setVisibility(View.INVISIBLE);
            constraint_frag_cal_main.setVisibility(View.VISIBLE);

        }else{

            frag_cal_login_box.setVisibility(View.VISIBLE);
            constraint_frag_cal_main.setVisibility(View.INVISIBLE);

            ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent intent = result.getData();

                                int CallType = intent.getIntExtra("CallType", 2);
                                String userID = intent.getStringExtra("userID");

                                if(CallType == 2) {
                                    ((BottomNavigationView) getActivity().findViewById(R.id.nav_view)).setSelectedItemId(R.id.navigation_satisfied);
                                }
                                cookieManager.setCookie("http://192.168.55.162/inuNavi/","cookieKey="+userID);
                                frag_cal_login_box.setVisibility(View.INVISIBLE);
                                constraint_frag_cal_main.setVisibility(View.VISIBLE);
                            }
                        }
                    });


            button_frag_cal_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    someActivityResultLauncher.launch(intent);

                }
            });

        }




        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}