package com.example.springboot.model;
import com.example.springboot.validation.ValidDate;
import com.example.springboot.validation.ValidTime;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long appointment_id;
    @NotEmpty(message = "Appointment acception empty!")
    @Column(name = "appointment_acceptation",nullable = false)
    private String  appointment_acceptation;
    @NotEmpty(message = "Date empty!")
    @ValidDate
    @Column(name = "doa", nullable = false)
    private String doa;

    @NotEmpty(message = "Time empty!")
    @ValidTime
    @Column(name = "start_time", nullable = false)
    private String start_time;
    @Size(max=200,message = "Description too long!")
    @NotEmpty(message = "Description empty!")
    @Column(name = "appointment_description", nullable = false)
    private String appointment_description;


    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "doc_id",referencedColumnName = "doc_id")
    private Doctor doc;


    public Appointment() {
    }

    public Appointment( String doa, String start_time, String appointment_description,
                        User user, Doctor doc, String appointment_acception) {
        this.doa = doa;
        this.start_time = start_time;
        this.appointment_description = appointment_description;
        this.user = user;
        this.doc = doc;
        this.appointment_acceptation = appointment_acception;
    }

    // Getters and Setters
    public long getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(long appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getDoa() {
        return doa;
    }

    public void setDoa(String doa) {
        this.doa = doa;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getAppointment_description() {
        return appointment_description;
    }

    public void setAppointment_description(String appointment_description) {
        this.appointment_description = appointment_description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Doctor getDoc() {
        return doc;
    }

    public void setDoc(Doctor doc) {
        this.doc = doc;
    }

    public String getAppointment_acception() {
      return appointment_acceptation;
   }

    public void setAppointment_acception(String appointment_acception) {
        this.appointment_acceptation = appointment_acception;
    }
}
