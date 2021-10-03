package com.maru.inunavi.ui.timetable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.user.LoginActivity;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SettingAdapter adapter;


    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_setting);

        recyclerView = (RecyclerView)findViewById(R.id.recyceler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)) ;

        ArrayList<String> list = new ArrayList<>();
        list.add("로그아웃");
        adapter = new SettingAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SettingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Log.d("@@@ settingactivity42", "error");

                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                intent.putExtra("CallType", 1001);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

        }) ;

    }


}
