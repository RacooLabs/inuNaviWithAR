package com.maru.inunavi.ui.timetable;

import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.IpAddress.DemoIP_ClientTest;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.maru.inunavi.IpAddress;

import java.util.HashMap;
import java.util.Map;

public class ModifyMajorRequest extends StringRequest {


    final static private String URL = IpAddress.isTest ? "http://" + DemoIP_ClientTest + "/inuNavi/ModifyMajor.php" :
            "http://" + DemoIP + "/user/insert";


    private Map<String, String> parameters;

    public ModifyMajorRequest(String userEmail, String userMajor, Response.Listener<String> listener){

        super(Method.POST, URL, listener, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("@@@", "error_signup_requst:24");
        }
    });

        parameters = new HashMap<>();
        parameters.put("email", userEmail);
        parameters.put("major", userMajor);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
