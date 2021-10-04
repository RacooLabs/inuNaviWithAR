package com.maru.inunavi.ui.timetable;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;

public class SearchActivity extends AppCompatActivity {


    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_activity_search);

        ImageView tita_search_backButton = findViewById(R.id.tita_search_backButton);

        //돌아가기 버튼
        tita_search_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  { finish(); }
        });
        

    }


}
