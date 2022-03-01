package com.maru.inunavi.ui.map;

import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.IpAddress.DemoIP_ClientTest;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.maru.inunavi.IpAddress;

import java.util.HashMap;
import java.util.Map;

public class NextPlaceRequest extends StringRequest {

    final static private String URL = (IpAddress.isTest ? "http://"+ DemoIP_ClientTest +"/inuNavi/GetNextPlace.php" :
            "http://" + DemoIP + "/getNextPlace");

    private Map<String, String> parameters;

    public NextPlaceRequest(String userEmail, Response.Listener<String> listener){

        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        parameters = new HashMap<>();
        parameters.put("email", userEmail);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
