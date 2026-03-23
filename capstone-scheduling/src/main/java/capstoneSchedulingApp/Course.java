package capstoneSchedulingApp;

import java.sql.ResultSet;

public class Course {

    public int id;
    public int clas_num;            //Unique identifier
    public int course_num;        //Class number, ex: CS 1501          //Unique identifier 
    public int asso_num;            //Helps identify which classes and recs work
    public String days;
    public Boolean day_mon;
    public Boolean day_tues;
    public Boolean day_wed;
    public Boolean day_thurs;
    public Boolean day_fri;
    public String start;
    public String end;
    public int start_int;
    public int end_int;
    public String room;
    public String instructor;
    public String type;
    public int enroll;
    
    public Course(ResultSet input) throws Exception{
        this.id         = input.getInt("id");
        this.clas_num   = input.getInt("clas_num");
        this.course_num = input.getInt("course_num");
        this.asso_num   = input.getInt("asso_num");
        this.days       = input.getString("days");
        this.day_mon    = input.getBoolean("day_mon");
        this.day_tues   = input.getBoolean("day_tues");
        this.day_wed    = input.getBoolean("day_wed");
        this.day_thurs  = input.getBoolean("day_thurs");
        this.day_fri    = input.getBoolean("day_fri");
        this.start      = input.getString("start");
        this.end        = input.getString("end");
        this.start_int  = input.getInt("start_int");
        this.end_int    = input.getInt("end_int");
        this.room       = input.getString("room");
        this.instructor = input.getString("instructor");
        this.type       = input.getString("type");
        this.enroll     = input.getInt("enroll");
    }

    public Course() {
        this.id         = -1;
        this.clas_num   = -1;
        this.course_num = -1;
        this.asso_num   = -1;
        this.days       = "";
        this.day_mon    = false;
        this.day_tues   = false;
        this.day_wed    = false;
        this.day_thurs  = false;
        this.day_fri    = false;
        this.start      = "";
        this.end        = "";
        this.start_int  = -1;
        this.end_int    = -1;
        this.room       = "";
        this.instructor = "";
        this.type       = "";
        this.enroll     = -1;
    }

    public String toString() {
        return clas_num + " " + days + " " + start + "-" + end + " " + instructor;
    }
}
