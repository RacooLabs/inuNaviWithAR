package com.maru.inunavi;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUpRequest extends StringRequest {

    //final static private String URL = "http://192.168.55.162/inuNavi/UserRegister.php";
    final static private String URL = "http://219.248.233.170/project1_war_exploded/user/insert";

    private Map<String, String> parameters;

    public SignUpRequest(String userID, String userPassword, String userEmail, String userName ,Response.Listener<String> listener){

        super(Method.POST, URL, listener, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("@@@", "error_signup_requst:24");
        }
    });

        parameters = new HashMap<>();
        parameters.put("id", userID);
        parameters.put("name", userName);
        parameters.put("password", userPassword);
        parameters.put("email", userEmail);


    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
