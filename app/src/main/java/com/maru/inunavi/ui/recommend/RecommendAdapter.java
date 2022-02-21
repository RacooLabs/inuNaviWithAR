package com.maru.inunavi.ui.recommend;

import static com.maru.inunavi.IpAddress.DemoIP;
import static com.maru.inunavi.IpAddress.DemoIP_ClientTest;
import static com.maru.inunavi.MainActivity.sessionURL;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.maru.inunavi.IpAddress;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.search.AddRequest;
import com.maru.inunavi.ui.timetable.search.Lecture;
import com.maru.inunavi.ui.timetable.search.Schedule;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {


    FragmentActivity parent;

    private String userEmail = "";
    private Schedule schedule = new Schedule();

    private RecommendAdapter.OnItemClickListener mListener = null ;
    private ArrayList<Lecture> mData = null;

    private String sUrl = sessionURL;

    String target;

    {
        target = IpAddress.isTest ? "http://"+ DemoIP_ClientTest +"/inuNavi/ScheduleList.php":
                "http://" + DemoIP +"/user/select/class";
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }


    public void setOnItemClickListener(RecommendAdapter.OnItemClickListener listener) {

        this.mListener = listener;

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView textView_recommend_lectureName;
        public TextView textView_recommend_professor;
        public TextView textView_recommend_info;
        public TextView textView_recommend_time;
        public TextView textView_recommend_add;

        public TextView textView_subtitle;


        public MyViewHolder(View v,int viewType) {

            super(v);

            switch (viewType){
                case 0:
                    textView_subtitle = (TextView) v.findViewById(R.id.textView_subtitle);
                    break;

                case 1:
                    textView_subtitle = (TextView) v.findViewById(R.id.textView_subtitle);
                    break;

                case 2:

                    textView_recommend_lectureName = (TextView) v.findViewById(R.id.textView_recommend_lectureName);
                    textView_recommend_professor = (TextView) v.findViewById(R.id.textView_recommend_professor);
                    textView_recommend_info = (TextView) v.findViewById(R.id.textView_recommend_info);
                    textView_recommend_time = (TextView) v.findViewById(R.id.textView_recommend_time);
                    textView_recommend_add = itemView.findViewById(R.id.textView_recommend_add);

                    break;

                default:
                    textView_subtitle = (TextView) v.findViewById(R.id.textView_subtitle);
                    break;

            }

        }

    }

    public RecommendAdapter(ArrayList<Lecture> list, FragmentActivity parent) {

        this.mData = list;
        this.parent = parent;
        schedule = new Schedule();
        GetUserTableInfoBackgroundTask();

    }


    @Override
    public RecommendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        LinearLayout v;

        switch (viewType){
            case 0:

                v = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recommend_cardview_subtitle, parent, false);
                break;

            case 1:

                v = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recommend_cardview_subtitle, parent, false);
                break;

            case 2:

                v = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recommend_cardview, parent, false);

                break;

            default:
                v = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recommend_cardview_subtitle, parent, false);
                break;
        }

        MyViewHolder vh = new MyViewHolder(v,viewType);
        return vh;

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        if (mData.get(position).getLecturename().equals("거리 맞춤 추천")){
            holder.textView_subtitle.setText("거리 맞춤 추천");

        } else if (mData.get(position).getLecturename().equals("개인 맞춤 추천")){
            holder.textView_subtitle.setText("개인 맞춤 추천");

        } else {

            holder.textView_recommend_lectureName.setText(mData.get(position).getLecturename());
            holder.textView_recommend_professor.setText(mData.get(position).getProfessor());
            holder.textView_recommend_info.setText(mData.get(position).getGrade() + "학년 " + mData.get(position).getCategory() + " " +
                    mData.get(position).getPoint() + "학점 " + mData.get(position).getNumber());
            holder.textView_recommend_time.setText(mData.get(position).getClasstime_raw());



            holder.textView_recommend_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean validate = false;
                    boolean alreadyIn = false;

                    validate = schedule.validate(mData.get(holder.getAdapterPosition()).getClasstime());
                    alreadyIn = schedule.alreadyIn(mData.get(holder.getAdapterPosition()).getNumber());

                    if(alreadyIn){

                        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                        AlertDialog dialog = builder.setMessage("이미 추가한 강의입니다.").setPositiveButton("확인", null)
                                .create();
                        dialog.show();

                    }else if(validate == false) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                        AlertDialog dialog = builder.setMessage("시간표가 중복됩니다.").setPositiveButton("확인", null)
                                .create();
                        dialog.show();

                    }else{

                        Response.Listener<String> responseListener = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                try {

                                    Log.d("@@@", "RecommendAdapter_213 : " + response);

                                    JSONObject jsonResponse = new JSONObject(response);

                                    boolean success = jsonResponse.getBoolean("success");

                                    if (success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                                        AlertDialog dialog = builder.setMessage("강의를 추가하였습니다.").setPositiveButton("확인", null)
                                                .create();
                                        dialog.show();

                                        int pos = holder.getAdapterPosition() ;

                                        if (pos != RecyclerView.NO_POSITION) {
                                            if(mListener != null){
                                                mListener.onItemClick(view,pos);
                                            }
                                        }

                                        schedule.addSchedule(mData.get(holder.getAdapterPosition()));


                                    }else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(parent);
                                        AlertDialog dialog = builder.setMessage("강의 추가를 실패하였습니다.").setPositiveButton("확인", null)
                                                .create();
                                        dialog.show();
                                    }

                                } catch (Exception e) {

                                    e.printStackTrace();

                                }

                            }

                        };

                        userEmail = (MainActivity.cookieManager.getCookie(sUrl)).replace("cookieKey=", "");

                        AddRequest addRequest = new AddRequest(userEmail, mData.get(holder.getAdapterPosition()).getNumber(),responseListener);
                        RequestQueue queue = Volley.newRequestQueue(parent);
                        queue.add(addRequest);


                    }

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }


    public int getItemViewType(int position) {

        if (mData.get(position).getLecturename().equals("거리 맞춤 추천")) {
            return 0;
        } else if (mData.get(position).getLecturename().equals("개인 맞춤 추천")) {
            return 1;
        } else {
            return 2;
        }

    }

    Disposable backgroundtask;

    // 기존 시간표 불러오기.
    void GetUserTableInfoBackgroundTask() {

        backgroundtask = Observable.fromCallable(() -> {
            // doInBackground

            try {
                URL url = new URL(target);
                //HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                Map<String,Object> params = new LinkedHashMap<>();
                params.put("email", MainActivity.cookieManager.getCookie(sUrl).replace("cookieKey=", ""));

                StringBuilder postData = new StringBuilder();
                for(Map.Entry<String,Object> param : params.entrySet()) {
                    if(postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                httpURLConnection.setDoOutput(true);
                httpURLConnection.getOutputStream().write(postDataBytes);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("@@@search adapter 229", e.toString());
            }

            return null;

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorReturn(___ -> "{response : []}").subscribe((result) -> {

            // onPostExecute

            try {

                Log.d("@@@search adapter 258", result);
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");

                int count = 0;

                int id;
                String department;
                String grade;
                String category;
                String number;
                String lecturename;
                String professor;
                String classroom_raw;
                String classtime_raw;
                String classroom;
                String classtime;
                String how;
                String point;



                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);

                    id = object.getInt("id");
                    department = object.getString("department");
                    grade = object.getString("grade");
                    category = object.getString("category");
                    number = object.getString("number");
                    lecturename = object.getString("lecturename");
                    professor = object.getString("professor");
                    classroom_raw = object.getString("classroom_raw");
                    classtime_raw = object.getString("classtime_raw");
                    classroom = object.getString("classroom");
                    classtime = object.getString("classtime");
                    how = object.getString("how");
                    point = object.getString("point");

                    classtime_raw = classtime_raw.trim();
                    classtime_raw = classtime_raw.replaceAll("\"", "");
                    classtime_raw = classtime_raw.replaceAll(" ", "");
                    classtime_raw = classtime_raw.replace("[", "");
                    classtime_raw = classtime_raw.replaceAll("]", "");

                    Lecture lecture = new Lecture(id, department, grade, category, number, lecturename,
                            professor, classroom_raw, classtime_raw, classroom, classtime, how, Integer.parseInt(point));

                    schedule.addSchedule(lecture);

                    count++;

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            backgroundtask.dispose();


        });

    }

}
