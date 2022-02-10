package com.maru.inunavi;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class AppInfo extends AppCompatActivity {


    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.appinfo);
        String version = "";

        PackageInfo packageInfo = null;
        try {
            packageInfo = getBaseContext().getPackageManager().getPackageInfo(getBaseContext().getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView textView_version= findViewById(R.id.version);
        ImageView appInfo_backButton = findViewById(R.id.appInfo_backButton);


        appInfo_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);

            }
        });

        textView_version.setText(version + "v\n\nTeam 마루 \n\n racoo340@gmail.com");


    }


}
