package com.example.midtermproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController {
    @FXML
    private ListView<Node> doctorListView;
    @FXML
    private ListView<String> appointmenttoday;
    private final String originalStyle = "-fx-background-color:  #56aeff; -fx-background-radius: 10;";
    private final String hoverStyle = "-fx-background-color:  #1893f8 ; -fx-background-radius: 15;";
    HelloApplication app = new HelloApplication();
    LoginController control = new LoginController();

    public void initialize() throws SQLException, JsonProcessingException, JSONException {
        Pair<Integer, String> s = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/top", "");
        if (s.getKey() == HttpURLConnection.HTTP_OK) {
            ObservableList<Node> doctorList = FXCollections.observableArrayList();
            if (!s.getValue().isEmpty()) {
                JSONArray jsonArray = new JSONArray(s.getValue());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Button name = new Button();
                    name.setStyle("-fx-background-color: transparent");
                    String specialty = jsonObject.getString("doc_specialty");
                    int docid = jsonObject.getInt("doc_id");
                    String doctorname = jsonObject.getString("doc_fullname");
                    name.setText(doctorname + " _ " + specialty);
                    doctorList.add(name);
                    name.setOnMouseClicked(event -> {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("doctor.fxml"));
                        Parent root;
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        doctorController doctorController = loader.getController();

                        Scene scene = new Scene(root);
                        scene.setFill(Color.TRANSPARENT);
                        HelloApplication app = new HelloApplication();
                        app.getPrimarystage().setScene(scene);
                        try {
                            doctorController.setDoctor(docid);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });}}
        doctorListView.setItems(doctorList);}

        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/user/" + control.getUserId() + "/date/" + LocalDate.now(), "");
        System.out.println(r.getKey());
        if (r.getKey() == HttpURLConnection.HTTP_OK) {
            ObservableList<String> n = FXCollections.observableArrayList();
            if (!r.getValue().isEmpty()) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(r.getValue());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                // Iterate over each JSON object in the array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject docObject = jsonObject.getJSONObject("doc");

                    String docFullname = docObject.getString("doc_fullname");
                    n.add(jsonObject.getString("start_time") + "\t-\t Dr. " + docFullname);
                    appointmenttoday.setItems(n);
                }
            }
        }}
    @FXML
    public void onLabelEntered(javafx.scene.input.MouseEvent event) {
        Label label = (Label) event.getTarget();
        label.setStyle(hoverStyle);
        label.setScaleX(1.1);
        label.setScaleY(1.1);
    }


    public void onLabelExited(javafx.scene.input.MouseEvent mouseEvent) {
        Label label = (Label) mouseEvent.getTarget();
        label.setStyle(originalStyle);
        label.setScaleX(1.0);
        label.setScaleY(1.0);
    }

    public void seemorefunction() throws IOException {

        Parent r = FXMLLoader.load(getClass().getResource("specialties.fxml"));
        Scene scene = new Scene(r);
        app.getPrimarystage().setTitle("Specialties Of Doctors");
        app.getPrimarystage().setScene(scene);
        scene.setFill(Color.TRANSPARENT);
    }


    public void handleClick(javafx.scene.input.MouseEvent event){
        Label clickedLabel = (Label) event.getSource();

        String specialty = clickedLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorslist.fxml"));
            Parent root = loader.load();

            doctorslistController doctorListController = loader.getController();
            doctorListController.setSpecialty(specialty);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            HelloApplication app = new HelloApplication();
            app.getPrimarystage().setTitle("List Of Doctors");
            app.getPrimarystage().setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void openHome() throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("Home");
        app.getPrimarystage().setScene(scene);
    }
    public void openAppointments() throws IOException {
        Parent root;
        app.getPrimarystage().setTitle("Your appointments");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patientAppointments.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        app.getPrimarystage().setScene(scene);
    }

    public void openSpecialty() throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("specialties.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("Specialties Of Doctors");
        app.getPrimarystage().setScene(scene);
    }
    public void openUser() throws IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("User Account");
        app.getPrimarystage().setScene(scene);
    }

}
