package com.maru.inunavi.user;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.maru.inunavi.IpAddress;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = IpAddress.isTest ? "http://192.168.0.101/inuNavi/UserLogin.php" :
            "http://219.248.233.170/project1_war_exploded/user/login";

    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener){

        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("@@@", String.valueOf(error));
            }
        });

        parameters = new HashMap<>();
        parameters.put("id", userID);
        parameters.put("password", userPassword);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
