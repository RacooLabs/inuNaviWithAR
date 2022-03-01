package com.maru.inunavi;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        /*if (!checkLocationServicesStatus()) {


        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //기기의 sdk버전 이 M 즉, 마시멜로보다 높습니까? 마시멜로는 6.0
            //퍼미션 상태 확인


            if(!checkLocationServicesStatus()) {

                showDialogForLocationServiceSetting();

            }


            if (!hasPermissions(PERMISSIONS)) {
                //퍼미션 허가 안되어있다면 사용자에게 요청

                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);

            }



            if(checkLocationServicesStatus() && hasPermissions(PERMISSIONS)){

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                        overridePendingTransition(0, 0);
                    }
                }, 500);

            }

        }

    }


    // 여기서부터는 퍼미션 관련 코드입니다.
    static final int PERMISSIONS_REQUEST_CODE = 1000;
    String[] PERMISSIONS  = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};

    private boolean hasPermissions(String[] permissions) {

        int result;

        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (String perms : permissions){

            result = ContextCompat.checkSelfPermission(this, perms);

            if (result == PackageManager.PERMISSION_DENIED){
                //허가 안된 퍼미션 발견
                return false;
            }

        }

        //모든 퍼미션이 허가되었음
        return true;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){

            case PERMISSIONS_REQUEST_CODE:

                if (grantResults.length > 0) {
                    boolean locationPermissionAccepted = grantResults[0]
                            == PackageManager.PERMISSION_GRANTED;

                    if (!locationPermissionAccepted)
                        showDialogForPermission("위치 정보 권한이 필요합니다.");
                    else
                    {
                        if(!checkLocationServicesStatus()) {

                            showDialogForLocationServiceSetting();

                        }else{

                            Intent mainIntent = new Intent(LaunchActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                            overridePendingTransition(0, 0);

                        }
                    }
                }
                break;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder( LaunchActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id){
                finish();
                overridePendingTransition(0, 0);
            }
        });

        builder.create().show();
    }



    public boolean checkLocationServicesStatus() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 서비스를 켜세요.");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {


                final int GPS_ENABLE_REQUEST_CODE = 2001;

                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);

            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                finish();
                overridePendingTransition(0, 0);
            }
        });

        builder.create().show();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(new Intent(this, LaunchActivity.class));
    }
}
