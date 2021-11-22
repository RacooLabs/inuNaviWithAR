package com.maru.inunavi.ui.timetable;

import static com.maru.inunavi.MainActivity.cookieManager;
import static com.maru.inunavi.MainActivity.sessionURL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.maru.inunavi.IpAddress;
import com.maru.inunavi.ui.timetable.search.Lecture;
import com.maru.inunavi.ui.timetable.search.Schedule;
import com.maru.inunavi.ui.timetable.search.SearchActivity;
import com.maru.inunavi.user.LoginActivity;
import com.maru.inunavi.MainActivity;
import com.maru.inunavi.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CalendarFragment extends Fragment {

    private String url = sessionURL;

    private TextView schedule_textView[];

    private String userID;
    private Schedule schedule;

    public static String target;
    public View root;
    public RelativeLayout linearLayout_frag_tita;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.timetable_fragment, container, false);
        LinearLayout frag_tita_login_box = root.findViewById(R.id.frag_tita_login_box);
        ConstraintLayout constraint_frag_tita_main = root.findViewById(R.id.constraint_frag_tita_main);

        Button button_frag_tita_login = root.findViewById(R.id.button_frag_tita_login);
        ImageView imageView_frag_tita_setting = root.findViewById(R.id.imageView_frag_tita_setting);
        ImageView imageView_frag_tita_add = root.findViewById(R.id.imageView_frag_tita_add);

        linearLayout_frag_tita = root.findViewById(R.id.linearLayout_frag_tita);

        frag_tita_login_box.setVisibility(View.VISIBLE);
        constraint_frag_tita_main.setVisibility(View.INVISIBLE);

        cookieManager = ((MainActivity)getActivity()).getCookieManager();


        schedule_textView = new TextView[] {

                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,root.findViewById(R.id.timetable_cell16),
                root.findViewById(R.id.timetable_cell17),
                root.findViewById(R.id.timetable_cell18),
                root.findViewById(R.id.timetable_cell19),
                root.findViewById(R.id.timetable_cell20),
                root.findViewById(R.id.timetable_cell21),
                root.findViewById(R.id.timetable_cell22),
                root.findViewById(R.id.timetable_cell23),
                root.findViewById(R.id.timetable_cell24),
                root.findViewById(R.id.timetable_cell25),
                root.findViewById(R.id.timetable_cell26),
                root.findViewById(R.id.timetable_cell27),
                root.findViewById(R.id.timetable_cell28),
                root.findViewById(R.id.timetable_cell29),
                root.findViewById(R.id.timetable_cell30),
                root.findViewById(R.id.timetable_cell31),
                root.findViewById(R.id.timetable_cell32),
                root.findViewById(R.id.timetable_cell33),
                root.findViewById(R.id.timetable_cell34),
                root.findViewById(R.id.timetable_cell35),
                root.findViewById(R.id.timetable_cell36),
                root.findViewById(R.id.timetable_cell37),
                root.findViewById(R.id.timetable_cell38),
                root.findViewById(R.id.timetable_cell39),
                root.findViewById(R.id.timetable_cell40),
                root.findViewById(R.id.timetable_cell41),
                root.findViewById(R.id.timetable_cell42),
                root.findViewById(R.id.timetable_cell43),
                root.findViewById(R.id.timetable_cell44),
                root.findViewById(R.id.timetable_cell45),
                root.findViewById(R.id.timetable_cell46),
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,root.findViewById(R.id.timetable_cell64),
                root.findViewById(R.id.timetable_cell65),
                root.findViewById(R.id.timetable_cell66),
                root.findViewById(R.id.timetable_cell67),
                root.findViewById(R.id.timetable_cell68),
                root.findViewById(R.id.timetable_cell69),
                root.findViewById(R.id.timetable_cell70),
                root.findViewById(R.id.timetable_cell71),
                root.findViewById(R.id.timetable_cell72),
                root.findViewById(R.id.timetable_cell73),
                root.findViewById(R.id.timetable_cell74),
                root.findViewById(R.id.timetable_cell75),
                root.findViewById(R.id.timetable_cell76),
                root.findViewById(R.id.timetable_cell77),
                root.findViewById(R.id.timetable_cell78),
                root.findViewById(R.id.timetable_cell79),
                root.findViewById(R.id.timetable_cell80),
                root.findViewById(R.id.timetable_cell81),
                root.findViewById(R.id.timetable_cell82),
                root.findViewById(R.id.timetable_cell83),
                root.findViewById(R.id.timetable_cell84),
                root.findViewById(R.id.timetable_cell85),
                root.findViewById(R.id.timetable_cell86),
                root.findViewById(R.id.timetable_cell87),
                root.findViewById(R.id.timetable_cell88),
                root.findViewById(R.id.timetable_cell89),
                root.findViewById(R.id.timetable_cell90),
                root.findViewById(R.id.timetable_cell91),
                root.findViewById(R.id.timetable_cell92),
                root.findViewById(R.id.timetable_cell93),
                root.findViewById(R.id.timetable_cell94),
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,root.findViewById(R.id.timetable_cell112),
                root.findViewById(R.id.timetable_cell113),
                root.findViewById(R.id.timetable_cell114),
                root.findViewById(R.id.timetable_cell115),
                root.findViewById(R.id.timetable_cell116),
                root.findViewById(R.id.timetable_cell117),
                root.findViewById(R.id.timetable_cell118),
                root.findViewById(R.id.timetable_cell119),
                root.findViewById(R.id.timetable_cell120),
                root.findViewById(R.id.timetable_cell121),
                root.findViewById(R.id.timetable_cell122),
                root.findViewById(R.id.timetable_cell123),
                root.findViewById(R.id.timetable_cell124),
                root.findViewById(R.id.timetable_cell125),
                root.findViewById(R.id.timetable_cell126),
                root.findViewById(R.id.timetable_cell127),
                root.findViewById(R.id.timetable_cell128),
                root.findViewById(R.id.timetable_cell129),
                root.findViewById(R.id.timetable_cell130),
                root.findViewById(R.id.timetable_cell131),
                root.findViewById(R.id.timetable_cell132),
                root.findViewById(R.id.timetable_cell133),
                root.findViewById(R.id.timetable_cell134),
                root.findViewById(R.id.timetable_cell135),
                root.findViewById(R.id.timetable_cell136),
                root.findViewById(R.id.timetable_cell137),
                root.findViewById(R.id.timetable_cell138),
                root.findViewById(R.id.timetable_cell139),
                root.findViewById(R.id.timetable_cell140),
                root.findViewById(R.id.timetable_cell141),
                root.findViewById(R.id.timetable_cell142),
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,root.findViewById(R.id.timetable_cell160),
                root.findViewById(R.id.timetable_cell161),
                root.findViewById(R.id.timetable_cell162),
                root.findViewById(R.id.timetable_cell163),
                root.findViewById(R.id.timetable_cell164),
                root.findViewById(R.id.timetable_cell165),
                root.findViewById(R.id.timetable_cell166),
                root.findViewById(R.id.timetable_cell167),
                root.findViewById(R.id.timetable_cell168),
                root.findViewById(R.id.timetable_cell169),
                root.findViewById(R.id.timetable_cell170),
                root.findViewById(R.id.timetable_cell171),
                root.findViewById(R.id.timetable_cell172),
                root.findViewById(R.id.timetable_cell173),
                root.findViewById(R.id.timetable_cell174),
                root.findViewById(R.id.timetable_cell175),
                root.findViewById(R.id.timetable_cell176),
                root.findViewById(R.id.timetable_cell177),
                root.findViewById(R.id.timetable_cell178),
                root.findViewById(R.id.timetable_cell179),
                root.findViewById(R.id.timetable_cell180),
                root.findViewById(R.id.timetable_cell181),
                root.findViewById(R.id.timetable_cell182),
                root.findViewById(R.id.timetable_cell183),
                root.findViewById(R.id.timetable_cell184),
                root.findViewById(R.id.timetable_cell185),
                root.findViewById(R.id.timetable_cell186),
                root.findViewById(R.id.timetable_cell187),
                root.findViewById(R.id.timetable_cell188),
                root.findViewById(R.id.timetable_cell189),
                root.findViewById(R.id.timetable_cell190),
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,root.findViewById(R.id.timetable_cell208),
                root.findViewById(R.id.timetable_cell209),
                root.findViewById(R.id.timetable_cell210),
                root.findViewById(R.id.timetable_cell211),
                root.findViewById(R.id.timetable_cell212),
                root.findViewById(R.id.timetable_cell213),
                root.findViewById(R.id.timetable_cell214),
                root.findViewById(R.id.timetable_cell215),
                root.findViewById(R.id.timetable_cell216),
                root.findViewById(R.id.timetable_cell217),
                root.findViewById(R.id.timetable_cell218),
                root.findViewById(R.id.timetable_cell219),
                root.findViewById(R.id.timetable_cell220),
                root.findViewById(R.id.timetable_cell221),
                root.findViewById(R.id.timetable_cell222),
                root.findViewById(R.id.timetable_cell223),
                root.findViewById(R.id.timetable_cell224),
                root.findViewById(R.id.timetable_cell225),
                root.findViewById(R.id.timetable_cell226),
                root.findViewById(R.id.timetable_cell227),
                root.findViewById(R.id.timetable_cell228),
                root.findViewById(R.id.timetable_cell229),
                root.findViewById(R.id.timetable_cell230),
                root.findViewById(R.id.timetable_cell231),
                root.findViewById(R.id.timetable_cell232),
                root.findViewById(R.id.timetable_cell233),
                root.findViewById(R.id.timetable_cell234),
                root.findViewById(R.id.timetable_cell235),
                root.findViewById(R.id.timetable_cell236),
                root.findViewById(R.id.timetable_cell237),
                root.findViewById(R.id.timetable_cell238),
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,root.findViewById(R.id.timetable_cell256),
                root.findViewById(R.id.timetable_cell257),
                root.findViewById(R.id.timetable_cell258),
                root.findViewById(R.id.timetable_cell259),
                root.findViewById(R.id.timetable_cell260),
                root.findViewById(R.id.timetable_cell261),
                root.findViewById(R.id.timetable_cell262),
                root.findViewById(R.id.timetable_cell263),
                root.findViewById(R.id.timetable_cell264),
                root.findViewById(R.id.timetable_cell265),
                root.findViewById(R.id.timetable_cell266),
                root.findViewById(R.id.timetable_cell267),
                root.findViewById(R.id.timetable_cell268),
                root.findViewById(R.id.timetable_cell269),
                root.findViewById(R.id.timetable_cell270),
                root.findViewById(R.id.timetable_cell271),
                root.findViewById(R.id.timetable_cell272),
                root.findViewById(R.id.timetable_cell273),
                root.findViewById(R.id.timetable_cell274),
                root.findViewById(R.id.timetable_cell275),
                root.findViewById(R.id.timetable_cell276),
                root.findViewById(R.id.timetable_cell277),
                root.findViewById(R.id.timetable_cell278),
                root.findViewById(R.id.timetable_cell279),
                root.findViewById(R.id.timetable_cell280),
                root.findViewById(R.id.timetable_cell281),
                root.findViewById(R.id.timetable_cell282),
                root.findViewById(R.id.timetable_cell283),
                root.findViewById(R.id.timetable_cell284),
                root.findViewById(R.id.timetable_cell285),
                root.findViewById(R.id.timetable_cell286),
                null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};

        schedule = new Schedule();



        //설정 콜백
        ActivityResultLauncher<Intent> logoutActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);

                            if(CallType == 1001) {

                                // 로그아웃 요청

                                frag_tita_login_box.setVisibility(View.VISIBLE);
                                constraint_frag_tita_main.setVisibility(View.INVISIBLE);
                                cookieManager.removeAllCookies(null);

                            }


                        }
                    }
                });


        //설정 버튼
        imageView_frag_tita_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), SettingActivity.class);
                logoutActivityResultLauncher.launch(intent);

            }
        });


        //강의 추가 콜백
        ActivityResultLauncher<Intent> searchActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 0);

                            if(CallType == 2001) {

                                ScheduleBackgroundTask();

                            }


                        }
                    }
                });


        //강의 추가 버튼
        imageView_frag_tita_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                searchActivityResultLauncher.launch(intent);

            }
        });

        if(cookieManager.getCookie(url) != null && !cookieManager.getCookie(url).equals("")){


            userID = MainActivity.cookieManager.getCookie(url).replace("cookieKey=", "");

            {
                try {
                    target = IpAddress.isTest ? "http://192.168.0.106/inuNavi/ScheduleList.php?userID=\"" + URLEncoder.encode(userID, "UTF-8") +"\"":
                            "http://219.248.233.170/project1_war_exploded/user/login";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            Log.d("@@@ fragmentcalendar : 50", cookieManager.getCookie(url));

            frag_tita_login_box.setVisibility(View.INVISIBLE);
            constraint_frag_tita_main.setVisibility(View.VISIBLE);

            ScheduleBackgroundTask();

            //설정버튼 액티비티 리스너

        }else{

            //Log.d("@@@ fragmentcalendar : 61", cookieManager.getCookie(url).toString());

            //로그인 버튼 리스너
            frag_tita_login_box.setVisibility(View.VISIBLE);
            constraint_frag_tita_main.setVisibility(View.INVISIBLE);

        }



        //로그인 콜백
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();

                            int CallType = intent.getIntExtra("CallType", 2);
                            String userID = intent.getStringExtra("userID");

                            //로그인 요청, 쿠키 저장

                            if(CallType == 2) {
                                ((BottomNavigationView) getActivity().findViewById(R.id.nav_view)).setSelectedItemId(R.id.navigation_satisfied);
                            }
                            cookieManager.setCookie(url,"cookieKey="+userID);
                            frag_tita_login_box.setVisibility(View.INVISIBLE);
                            constraint_frag_tita_main.setVisibility(View.VISIBLE);

                            try {
                                target = IpAddress.isTest ? "http://192.168.0.106/inuNavi/ScheduleList.php?userID=\"" + URLEncoder.encode(userID, "UTF-8") +"\"":
                                        "http://219.248.233.170/project1_war_exploded/user/login";

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            ScheduleBackgroundTask();

                        }
                    }
                });


        //로그인 버튼
        button_frag_tita_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                someActivityResultLauncher.launch(intent);

            }
        });




        return root;

    }


    Disposable backgroundtask;

    public void ScheduleBackgroundTask() {

        backgroundtask = Observable.fromCallable(() -> {
            // doInBackground

            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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

        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {

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

                    Lecture lecture = new Lecture(id, department, Integer.parseInt(grade), category, number, lecturename,
                            professor, classroom_raw, classtime_raw, classroom, classtime, how, Integer.parseInt(point));

                    schedule.addSchedule(lecture);

                    count++;

                }




            } catch (Exception e) {
                e.printStackTrace();
            }

            schedule.setting(schedule_textView, getActivity(), linearLayout_frag_tita);

            backgroundtask.dispose();


        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}