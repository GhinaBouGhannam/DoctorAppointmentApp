package com.example.midtermproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.*;

public class doctorController {
    @FXML
    private Label docspecialty;

    @FXML
    private Label docaddress;

    @FXML
    private Label docdescription;

    @FXML
    private Label availaility;

    @FXML
    private Label doctorname;

    @FXML
    private Button bookbtn;

    private static int docid;
    HelloApplication app = new HelloApplication();

    void setDoctor(int doctorid) throws SQLException, JSONException {
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/" +doctorid, "");
        if (r.getKey() == HttpURLConnection.HTTP_OK) {
          if (!r.getValue().isEmpty()) {
                JSONObject jsonObj = new JSONObject(r.getValue());
                doctorname.setText("Dr." + jsonObj.getString("doc_fullname") );
                docid= Integer.parseInt(jsonObj.getString("doc_id"));
                docspecialty.setText( jsonObj.getString("doc_specialty"));
                docaddress.setText(jsonObj.getString("doc_address"));
                docdescription.setText(jsonObj.getString("doc_description"));
                availaility.setText(jsonObj.getString("doc_availability"));
            }
    }}
    public void handlebutton(){
        bookbtn.setOnAction(e->{
            Parent r = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("appointment.fxml"));
            try {
                r = loader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            Scene scene = new Scene(r);
            appointmentController appointmentController = loader.getController();
            try {
                appointmentController.setDoctor(docid);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            app.getPrimarystage().setTitle("New Appointment");
            app.getPrimarystage().setScene(scene);
            scene.setFill(Color.TRANSPARENT);

        });
    }
    public void openHome(MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openHome();
    }
    public void openSpecialty(MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openSpecialty();
    }

    public void openAppointments(MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openAppointments();
    }
    public void openUser(MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openUser();
    }
}