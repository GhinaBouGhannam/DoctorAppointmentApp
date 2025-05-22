package com.example.midtermproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

public class docAppointmentController {
    @FXML
    private DatePicker datePicker;

    LoginController control = new LoginController();
    DochomeController c = new DochomeController();
    @FXML
    private ListView listview;

    public void initialize() throws SQLException, JSONException {
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/doctor/" + control.getDocId() + "/date/" + LocalDate.now()+"/accept/1", "");
        setListview(r);
    }

    @FXML
    public void handleDateSelection() throws SQLException, JSONException {
      LocalDate selectedDate = datePicker.getValue();
      System.out.println(selectedDate);
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/doctor/" + control.getDocId() + "/date/" + selectedDate+"/accept/1", "");
       setListview(r);
    }

    public void setListview(Pair<Integer, String> r ) throws SQLException, JSONException {
        listview.setStyle("-fx-font-size: 50px;");
        listview.setFixedCellSize(30);
        listview.setStyle("-fx-control-inner-background: #f0f0f0; -fx-text-fill: black");
        ObservableList<Node> List = FXCollections.observableArrayList();
       List.add(new Label("Time"+"\t\t\t\t\t" +"Name" + "\t\t\t" + "phone number"+ "\t\t\t" +"Appointment Description"));
        if (r.getKey() == HttpURLConnection.HTTP_OK) {
            JSONArray jsonArray = new JSONArray(r.getValue());
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!r.getValue().isEmpty()) {
                    JSONObject jsonObj =  jsonArray.getJSONObject(i);
                    JSONObject userObject = jsonObj.getJSONObject("user");
                    String time= jsonObj.getString("start_time");
                    String name=   userObject.getString("name");
                    String num= userObject.getString("phone_number");
                    String desc = jsonObj.getString("appointment_description");


            Label lbl = new Label();
            lbl.setText(time + "\t\t\t" + name + "\t\t\t" + num + "\t\t\t" + desc);
            lbl.setStyle("-fx-font-size: 14px;");
            List.add(lbl);
        }  }
        listview.setItems(List);}
    }

    public void handleNewAppointments() throws IOException {
      c.handleNewAppointments();
    }
    public void handleAppointments() throws IOException {
        c.handleAppointments();
    }
    public void handleHome() throws IOException {
        c.handleHome();
    }
    public void handlelogout() throws IOException {
        c.handlelogout();
    }

}

