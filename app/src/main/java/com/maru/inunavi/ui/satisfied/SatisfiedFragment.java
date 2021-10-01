package com.maru.inunavi.ui.satisfied;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.maru.inunavi.LoginActivity;
import com.maru.inunavi.R;


public class SatisfiedFragment extends Fragment {


    

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_satisfied, container, false);

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}