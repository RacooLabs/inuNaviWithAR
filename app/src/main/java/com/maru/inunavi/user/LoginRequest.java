package com.maru.inunavi.user;

import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.IpAddress.DemoIP_ClientTest;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.maru.inunavi.IpAddress;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = IpAddress.isTest ? "http://"+ DemoIP_ClientTest +"/inuNavi/UserLogin.php" :
            "http://" + DemoIP + "/user/login";

    private Map<String, String> parameters;

    public LoginRequest(String userEmail, String userPassword, Response.Listener<String> listener){


        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("@@@", String.valueOf(error));
            }
        });

        parameters = new HashMap<>();
        parameters.put("email", userEmail);
        parameters.put("password", userPassword);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
