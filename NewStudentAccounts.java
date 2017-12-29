package com.example.sammay.loginactivity;

/**
 * Created by sammay on 15/12/2017.
 */

public class NewStudentAccounts
{
    String studentID;
    String loginID;
    String email;
    String fullname;
    String module;
    String degree;
    String room;


    //required default constructor
    public NewStudentAccounts()
    {

    }

    public NewStudentAccounts(
            String studentID, String loginID, String email, String fullname, String module, String degree, String room)
    {
        this.studentID = studentID;
        this.loginID = loginID;
        this.email = email;
        this.fullname = fullname;
        this.module = module;
        this.degree = degree;
        this.room = room;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getLoginID() {
        return loginID;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getModule() {
        return module;
    }

    public String getDegree() {
        return degree;
    }

    public String getRoom() {
        return room;
    }


}
