package com.maru.inunavi.ui.timetable;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;

public class SettingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SettingAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_setting);

        recyclerView = (RecyclerView)findViewById(R.id.recyceler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)) ;
        adapter = new SettingAdapter();

        adapter.setArrayData("로그아웃");
        recyclerView.setAdapter(adapter);

    }


}
