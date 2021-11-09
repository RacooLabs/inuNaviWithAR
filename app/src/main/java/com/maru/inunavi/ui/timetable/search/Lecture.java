package com.maru.inunavi.ui.timetable.search;

public class Lecture {

    String department; // 학과
    int grade; // 학년
    String category;
    String number;
    String lectureName;
    String professor;
    String place;
    String time;
    String how;
    int score;

    public Lecture(String department, int grade, String category, String number, String lectureName, String professor, String place, String time, String how, int score, String university) {
        this.department = department;
        this.grade = grade;
        this.category = category;
        this.number = number;
        this.lectureName = lectureName;
        this.professor = professor;
        this.place = place;
        this.time = time;
        this.how = how;
        this.score = score;
        this.university = university;
    }

    String university; //단과대

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
