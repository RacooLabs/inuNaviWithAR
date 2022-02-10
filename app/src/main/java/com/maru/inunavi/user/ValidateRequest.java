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

public class ValidateRequest extends StringRequest {

    final static private String URL = IpAddress.isTest ? "http://"+ DemoIP_ClientTest +"/inuNavi/UserValidate.php?email=" :
            "http://" + DemoIP + "/user/check/id?id=";

    private Map<String, String> parameters;

    public ValidateRequest(String userEmail, Response.Listener<String> listener){

        super(Method.GET, URL  + userEmail, listener, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("@@@2", String.valueOf(error));
            }
        });

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
