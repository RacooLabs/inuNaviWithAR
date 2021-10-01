package com.maru.inunavi;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest {

    final static private String URL = "http://219.248.233.170/project1_war_exploded/user/check/id?id=";
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
