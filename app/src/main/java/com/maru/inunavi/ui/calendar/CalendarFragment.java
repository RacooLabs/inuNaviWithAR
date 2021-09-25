package com.maru.inunavi.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.maru.inunavi.R;


public class CalendarFragment extends Fragment {

    //private MapViewModel mapViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}