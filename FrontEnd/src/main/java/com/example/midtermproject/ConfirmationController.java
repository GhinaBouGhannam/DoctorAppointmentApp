package com.example.midtermproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ConfirmationController {

    @FXML
    private ImageView image;

    @FXML
    private Label confirm;
    private static boolean firstappointment;

    public void initialize(){
        if(firstappointment){
            image.setImage(new Image("C:\\Users\\User\\Downloads\\hooray.png"));
            confirm.setText("Hooray! You've made your first appointment");
        }
        else{ image.setImage(new Image("C:\\Users\\User\\Downloads\\confirmed.png"));
            confirm.setText("Appointment Confirmed");}
    }

    public void setFirstappointment(boolean firstappointment) {
        this.firstappointment = firstappointment;
    }

    public void openHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
       Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        HelloApplication app = new HelloApplication();
        app.getPrimarystage().setTitle("Home");
        app.getPrimarystage().setScene(scene);
    }

    public void openAppointments(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patientAppointments.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        HelloApplication app = new HelloApplication();
        app.getPrimarystage().setTitle("Appointments ");
        app.getPrimarystage().setScene(scene);

    }
}
