package com.example.midtermproject;

import java.sql.Time;
import java.util.Date;

public class Doctor {

private long doc_id;
    private String doc_fullname;
    private String doc_email;
    private int doc_phonenumber;
    private String doc_password;
    private int doc_fees;
    private String doc_address;

    private String doc_specialty;

    private String doc_availability;
    private String doc_description;

    public String getDoc_fullname() {
        return doc_fullname;
    }
    public String getDoc_email() {
        return doc_email;
    }
    public int getDoc_fees() {
        return doc_fees;
    }
    public int getDoc_phonenumber() {
        return doc_phonenumber;
    }
    public String getDoc_address() {
        return doc_address;
    }
    public String getDoc_availability() {
        return doc_availability;
    }

    public String getDoc_description() {
        return doc_description;
    }

    public String getDoc_password() {
        return doc_password;
    }

    public String getDoc_specialty() {
        return doc_specialty;
    }

    public Doctor(String doc_fullname,String doc_password, String doc_email,String doc_address, String doc_description,
                  String doc_specialty,  String doc_availability, int doc_fees, int doc_phonenumber,long doc_id){
        this.doc_address =  doc_address;
        this.doc_description = doc_description;
        this.doc_fees= doc_fees;
        this.doc_specialty= doc_specialty;
        this.doc_availability= doc_availability;
        this.doc_email=doc_email;
        this.doc_fullname=doc_fullname;
        this.doc_phonenumber= doc_phonenumber;
        this.doc_password= doc_password;
        this.doc_id=doc_id;
    }

    public long getDoc_id() {
        return doc_id;
    }
}
