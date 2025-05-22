package com.example.midtermproject;

import java.sql.Time;
import java.util.Date;

public class Appointment{
    private String doa;
    private String start_time;
    private String doc_fullname;
    private String doc_email;
    private String doc_phonenumber;
    private String appointment_acception;

    public Appointment(String doa, String startTime, String docFullname, String docEmail,
                       String docPhonenumber,String appointmentAcception) {
        this.doa = doa;
        doc_email = docEmail;
        doc_phonenumber = docPhonenumber;
        doc_fullname = docFullname;
        start_time= startTime;
        appointment_acception= appointmentAcception;}

    public String getAppointment_acception() {
        return appointment_acception;
    }

    public String getDoa() {
        return doa;
    }

    public String getDoc_email() {
        return doc_email;
    }

    public String getDoc_fullname() {
        return doc_fullname;
    }

    public String getDoc_phonenumber() {
        return doc_phonenumber;
    }

    public String getStart_time() {
        return start_time;
    }
}
