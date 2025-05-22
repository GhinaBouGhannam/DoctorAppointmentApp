package com.example.springboot.model;

import com.example.springboot.validation.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doc_id;

    @Column(name = "doc_fullname", nullable = false,unique = true)
    @NotEmpty(message = "Doctor name cannot be empty")
    @Size(min = 2, message = "Doctor name should have at least 2 characters")
    private String doc_fullname;

    // email should be a valid email format
    // email should not be null or empty
    @NotEmpty(message = "Doctor email cannot be empty")
    @Email(message = "Invalid email format")
    private String doc_email;

    // password should not be null or empty
    // password should have at least 8 characters
    @NotEmpty(message = "Doctor password cannot be empty")
    @Size(min = 8, message = "Password should have at least 8 characters")
    @ValidPassword(message = "Password should contain upper and lower case, number , and special character")
    private String doc_password;
    @NotEmpty(message = "Doctor specialty cannot be empty")
    @Column(name = "doc_specialty")
    private String doc_specialty;
    @NotEmpty(message = "Doctor availability cannot be empty")
    @Column(name = "doc_availabilty")
    private String doc_availabilty;

    @Column(name = "doc_fees")
    private int doc_fees;
    @NotEmpty(message = "Doctor description cannot be empty")
    @Column(name = "doc_description")
    private String doc_description;
    @Size(min=8,max=8, message="Invalid Phone Number")
    @Column(name = "doc_phonenumber")
    private String doc_phonenumber;
    @NotEmpty(message = "Doctor address cannot be empty")
    @Column(name = "doc_address")
    private String doc_address;

    // Constructors
    public Doctor() {
    }

    public Doctor(String doc_fullname, String doc_email, String doc_password, String doc_specialty,
                  String doc_availability, int doc_fees, String doc_description,
                  String doc_phonenumber, String doc_address) {
        this.doc_fullname = doc_fullname;
        this.doc_email = doc_email;
        this.doc_password = doc_password;
        this.doc_specialty = doc_specialty;
        this.doc_availabilty = doc_availability;
        this.doc_fees = doc_fees;
        this.doc_description = doc_description;
        this.doc_phonenumber = doc_phonenumber;
        this.doc_address = doc_address;
    }

    // Getters and Setters
    public long getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(long doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_fullname() {
        return doc_fullname;
    }

    public void setDoc_fullname(String doc_fullname) {
        this.doc_fullname = doc_fullname;
    }

    public String getDoc_email() {
        return doc_email;
    }

    public void setDoc_email(String doc_email) {
        this.doc_email = doc_email;
    }

    public String getDoc_password() {
        return doc_password;
    }

    public void setDoc_password(String doc_password) {
        this.doc_password = doc_password;
    }

    public String getDoc_specialty() {
        return doc_specialty;
    }

    public void setDoc_specialty(String doc_specialty) {
        this.doc_specialty = doc_specialty;
    }

    public String getDoc_availability() {
        return doc_availabilty;
    }

    public void setDoc_availability(String doc_availability) {
        this.doc_availabilty = doc_availability;
    }

    public int getDoc_fees() {
        return doc_fees;
    }

    public void setDoc_fees(int doc_fees) {
        this.doc_fees = doc_fees;
    }

    public String getDoc_description() {
        return doc_description;
    }

    public void setDoc_description(String doc_description) {
        this.doc_description = doc_description;
    }

    public String getDoc_phonenumber() {
        return doc_phonenumber;
    }

    public void setDoc_phonenumber(String doc_phonenumber) {
        this.doc_phonenumber = doc_phonenumber;
    }

    public String getDoc_address() {
        return doc_address;
    }

    public void setDoc_address(String doc_address) {
        this.doc_address = doc_address;
    }
}
