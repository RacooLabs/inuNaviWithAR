package com.maru.inunavi.ui.recommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.maru.inunavi.R;
import com.maru.inunavi.ui.timetable.search.Lecture;
import com.maru.inunavi.ui.timetable.search.Schedule;

import java.util.ArrayList;
import java.util.Arrays;


public class RecommendFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecommendAdapter adapter;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.recommend_fragment, container, false);

        recyclerView = (RecyclerView)root.findViewById(R.id.frag_recommend_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false)) ;




        ArrayList<Lecture> list = new ArrayList<Lecture>(Arrays.asList(

                new Lecture(0,"",0,"","","거리 맞춤 추천","","","", "","","",0),
                new Lecture(1,"국어국문학과",0,"전공선택","9062005","RISE","노지승","제15호관 인문대학-119 강의실(중)-1[SP119]"," [SP119:월(야1-2A)(야2B-3)]", "SP119","36-41","온라인(동영상+화상)",3),
                new Lecture(2,"국어국문학과",1,"전공기초","4890001","문학과문화","전병준","제15호관 인문대학-119 강의실(중)-1[SP119]"," [SP119:목(2)(3)]", "SP119","161-164","온라인(화상)",2),
                new Lecture(3,"국어국문학과",1,"전공기초","4890002","문학과문화","전병준","제15호관 인문대학-119 강의실(중)-1[SP119]"," [SP119:목(5)(6)]", "SP119","167-170","온라인(화상)",2),
                new Lecture(4,"국어국문학과",1,"전공기초","4894001","한자와생활","배은희","제15호관 인문대학-119 강의실(중)-1[SP119]"," [SP119:화(2)(3)]", "SP119","67-70","온라인(화상)",2),
                new Lecture(0,"",0,"","","개인 맞춤 추천","","","", "","","",0),
                new Lecture(5,"국어국문학과",1,"전공기초","4894002","한자와생활","배은희","제15호관 인문대학-119 강의실(중)-1[SP119]"," [SP119:화(5)(6)]", "SP119","73-76","온라인(화상)",2),
                new Lecture(6,"국어국문학과",1,"전공기초","4905001","한국어논리와표현","조현우","제15호관 인문대학-119 강의실(중)-1[SP119]"," [SP119:수(6)(7)]", "SP119","122-125","온라인(동영상+화상)",2),
                new Lecture(7,"국어국문학과",1,"전공기초","4905002","한국어논리와표현","조현우","제15호관 인문대학-119 강의실(중)-1[SP119]"," [SP119:수(8)(9)]", "SP119","126-129","온라인(동영상+화상)",2),
                new Lecture(8,"국어국문학과",1,"전공선택","540001","1급한국어(1)","송원용","제15호관 인문대학-120 어학원강의실-1[SP120]"," [SP120:월(5B-6),화(5B-6)]", "SP120,SP120","27-29,74-76,","온라인(화상)",3)));


        adapter = new RecommendAdapter(list);
        recyclerView.setAdapter(adapter);

        return root;
        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}