package com.example.midtermproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Pair;
import org.json.JSONException;

import java.sql.*;
import java.util.Date;


public class Patient {

    private String doa;
    private String start_time;
    private String user_name;
    private String phone_number;
    private Button accept;
    private Button reject;

    HelloApplication app = new HelloApplication();
    LoginController control = new LoginController();
    docnewAppointmentController cc = new docnewAppointmentController();
    public Patient(int appointment_id,String doa,String start_time, String user_name,String phone_number){
         this.doa=doa;
         this.user_name=user_name;
         this.phone_number=phone_number;
         this.start_time=start_time;
         this.accept= new Button("Accept");
         accept.setStyle("-fx-background-color: green;");
         accept.setOnAction(e-> {
                 Pair<Integer, String> res = HTTPCall.executeAPI("POST", "http://localhost:8080/api/healthub/appointment/accept/" +appointment_id, "");
             Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/doctor/" + control.getDocId() +"/accept/2", "");


         });
         this.reject = new Button("Reject");
         reject.setStyle("-fx-background-color: red;");

        reject.setOnAction(e-> {
                //Statement statement =app.getConnection().createStatement();
                Pair<Integer, String> res = HTTPCall.executeAPI("POST", "http://localhost:8080/api/healthub/appointment/reject/" +appointment_id, "");
            Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/doctor/" + control.getDocId() +"/accept/2", "");

        });
    }

    public String getStart_time() {
        return start_time;
    }
    public String getDoa() {
        return doa;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public String getUser_name() {
        return user_name;
    }
    public Button getAccept() {
        return accept;
    }
    public Button getReject() {
        return reject;
    }
}
