package com.maru.inunavi.user;

import static com.maru.inunavi.IpAddress.DemoIP;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.maru.inunavi.IpAddress;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {

    final static private String URL = IpAddress.isTest ? "http://192.168.0.101/inuNavi/UserValidate.php?id=" :
            "http://" + DemoIP + "/user/check/id?id=";

    private Map<String, String> parameters;

    public ValidateRequest(String userID, Response.Listener<String> listener){

        super(Method.GET, URL  + userID, listener, new Response.ErrorListener() {

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
