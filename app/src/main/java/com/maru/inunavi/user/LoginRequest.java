package com.maru.inunavi.user;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = "http://192.168.55.162/inuNavi/UserLogin.php";
    //final static private String URL = "http://219.248.233.170/project1_war_exploded/user/login";

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
