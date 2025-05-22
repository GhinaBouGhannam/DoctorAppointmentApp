package com.example.midtermproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.*;
import java.time.LocalDate;

public class doctorslistController {
 @FXML
 private ListView<Node> doctorListView;
    @FXML
    private Label notilbl;

    @FXML
    private TextField searchtxt;

     private Parent root;
     private String special;

     @FXML
     private Label titlelbl;

    public void setSpecialty(String specialty) throws SQLException, IOException, JSONException {
        titlelbl.setText("Category: "+ specialty);
        special=specialty;
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/specialty/" + specialty, "");
        System.out.println(r.getKey());
        if (r.getKey() == HttpURLConnection.HTTP_OK) {
            if (!r.getValue().isEmpty()) {
                JSONArray jsonArray = new JSONArray(r.getValue());
                setList(jsonArray);
            }
            return;
        }
    }

   public void handleSearch() throws SQLException, IOException, JSONException {
       if(searchtxt.getText().isEmpty()){
           setSpecialty(special);
           return;
       }
       Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/specialty/" + special+"/doc/"+searchtxt.getText(), "");
       if (r.getKey() == HttpURLConnection.HTTP_OK) {
           if (!r.getValue().isEmpty()) {
               JSONArray jsonArray = new JSONArray(r.getValue());
               setList(jsonArray);
           }}
    }

    public void setList(JSONArray jsonArray) throws SQLException, JSONException {
        ObservableList<Node> doctorList = FXCollections.observableArrayList();
        // Iterate over each JSON object in the array
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String docFullname = jsonObject.getString("doc_fullname");
            Button name = new Button();
            name.setStyle("-fx-background-color: transparent");
            name.setText("Dr. "+ docFullname);
            name.setOnMouseClicked(event -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("doctor.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                doctorController doctorController = loader.getController();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                HelloApplication app = new HelloApplication();
                app.getPrimarystage().setTitle("Doctor Information");
                app.getPrimarystage().setScene(scene);
                try {
                    doctorController.setDoctor(jsonObject.getInt("doc_id"));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            });
            doctorList.add(name);
        }
        if(doctorList.isEmpty()){
            doctorListView.setVisible(false);
            notilbl.setText("No doctors for this specialty: "+ special);
            return;
        }
        doctorListView.setVisible(true);
        notilbl.setText(" ");
        doctorListView.setItems(doctorList);
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