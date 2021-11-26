package com.maru.inunavi.ui.timetable.search;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.maru.inunavi.IpAddress;

import java.util.HashMap;
import java.util.Map;

public class DeleteRequest extends StringRequest {


    final static private String URL = IpAddress.isTest ? "http://192.168.0.101/inuNavi/LectureDelete.php" :
            "http://58.234.251.64:7777/user/delete/class";


    private Map<String, String> parameters;

    public DeleteRequest(String userID, String lectureNumber, Response.Listener<String> listener){

        super(Method.POST, URL, listener, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("@@@", "error_addrequest_requst:24" + error);
        }
    });

        parameters = new HashMap<>();
        parameters.put("id", userID);
        parameters.put("class_id", lectureNumber);

    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
