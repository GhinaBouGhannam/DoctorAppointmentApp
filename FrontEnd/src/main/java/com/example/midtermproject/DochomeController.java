package com.example.midtermproject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

public class DochomeController {
    @FXML
    private VBox vbox;

    @FXML
    private VBox vbox1;

    @FXML
    private Label docname;

    @FXML
    private Label datelbl;
    HelloApplication app = new HelloApplication();
    LoginController control = new LoginController();

    public void initialize() throws SQLException, JSONException {
        datelbl.setText("Today: "+LocalDate.now());
        Pair<Integer, String> res = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/" + control.getDocId() , "");
        JSONObject doctorname1 = new JSONObject(res.getValue());
        docname.setText("Hello, Dr." + doctorname1.getString("doc_fullname") );
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/doctor/" + control.getDocId() + "/date/" + LocalDate.now()+"/accept/1", "");
        if (r.getKey() == HttpURLConnection.HTTP_OK) {
            JSONArray jsonArray = new JSONArray(r.getValue());
            // Iterate over each JSON object in the array
            for (int i = 0; i < jsonArray.length(); i++) {
            if (!r.getValue().isEmpty()) {
                JSONObject jsonObj =  jsonArray.getJSONObject(i);
                JSONObject userObject = jsonObj.getJSONObject("user");
                CheckBox box = new CheckBox();
                String t= jsonObj.getString("start_time");
          String s=   userObject.getString("name");
           String num= userObject.getString("phone_number");
            vbox.getChildren().add(new Label(t+"\t\t"+s+"\t\t"+num));
        vbox1.getChildren().add(box);}}
    }}
    public void handleNewAppointments() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("docnewappointment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("New Appointments");
        app.getPrimarystage().setScene(scene);
    }
    public void handleAppointments() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("docappointment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("Your Appointments");
        app.getPrimarystage().setScene(scene);
    }

    public void handleHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dochome.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("Home");
        app.getPrimarystage().setScene(scene);
    }

    public void handlelogout() throws IOException {
        control.setDocid(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("HealthHub Login");
        app.getPrimarystage().setScene(scene);
    }

}