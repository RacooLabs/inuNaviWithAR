package com.maru.inunavi.ui.satisfied;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maru.inunavi.user.LoginActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.recommend.RecommendFragment;


public class SatisfiedFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        SatisfiedFragment satisfiedFragment= new SatisfiedFragment();
        RecommendFragment recommendFragment = new RecommendFragment();

        View root = inflater.inflate(R.layout.satisfied_fragment, container, false);
        Button button_frag_satisfied_login = root.findViewById(R.id.button_frag_sati_login);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);

                            if(CallType == 0){

                            }else if(CallType == 1){

                                ((BottomNavigationView)getActivity().findViewById(R.id.nav_view)).setSelectedItemId(R.id.navigation_satisfied);

                            }else if(CallType == 2){

                            }

                        }
                    }
                });


        button_frag_satisfied_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                someActivityResultLauncher.launch(intent);

            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}