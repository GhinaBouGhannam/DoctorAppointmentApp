package com.example.midtermproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class docnewAppointmentController {

    @FXML
    private TableColumn<Patient, Date> datecol;
    @FXML
    private TableColumn<Patient, Time> timecol;

    @FXML
    private TableColumn<Patient,String> namecol;

    @FXML
    private TableColumn<Patient,String> numcol;

    @FXML
    private TableColumn<Patient, Button> accept;

    @FXML
    private TableColumn<Patient, Button> reject;

    @FXML
    private TableView<Patient> tableview;

    @FXML
    private Label notilbl;

    @FXML
    private TextField searchfield;

    LoginController control = new LoginController();
    DochomeController c = new DochomeController();

    public void initialize() throws SQLException, JSONException {
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/doctor/" + control.getDocId() +"/accept/2", "");
        generateTableView5(r);
    }

    public void generateTableView5( Pair<Integer, String> r ) throws SQLException, JSONException {
        datecol.setCellValueFactory(new PropertyValueFactory<>("doa"));
        timecol.setCellValueFactory(new PropertyValueFactory<>("start_time"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        numcol.setCellValueFactory(new PropertyValueFactory<>("phone_number"));
        accept.setCellValueFactory(new PropertyValueFactory<>("accept"));
        reject.setCellValueFactory(new PropertyValueFactory<>("reject"));

        ObservableList<Patient> data = FXCollections.observableArrayList();
        if (r.getKey() == HttpURLConnection.HTTP_OK) {
            JSONArray jsonArray = new JSONArray(r.getValue());
            // Iterate over each JSON object in the array
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!r.getValue().isEmpty()) {
                    JSONObject jsonObj =  jsonArray.getJSONObject(i);
                    JSONObject userObject = jsonObj.getJSONObject("user");
                    int appid = jsonObj.getInt("appointment_id");
                    String doa= jsonObj.getString("doa");
                    String t= jsonObj.getString("start_time");
                    String s=   userObject.getString("name");
                    String num= userObject.getString("phone_number");
            data.add((new Patient( appid,doa, t, s,num)));
        }}}
        if(data.isEmpty()){
            tableview.setVisible(false);
            notilbl.setText("No appointments found!");
            return;
        }
        tableview.setItems(data);
        tableview.refresh();
        System.out.println(tableview.getItems());
        tableview.setVisible(true);
        notilbl.setText(" ");

    }
    @FXML
    private void searchAppointmentsByDate() throws SQLException, JSONException {
        if(searchfield.getText().isEmpty()){
            initialize();
            return;
        }
        String selectedDate;
        if(isValidDateFormat(searchfield.getText())){
            selectedDate = searchfield.getText();
        }
        else {
            Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/doctor/" + control.getDocId() + "/name/" + searchfield.getText()+"/accept/2", "");
            generateTableView5(r);
            System.out.println(tableview.getItems());
            tableview.refresh();
            return;
        }
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/doctor/" + control.getDocId() + "/date/" + selectedDate+"/accept/2", "");

        generateTableView5(r);
        tableview.refresh();
    }
    public static boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {

            return false;
        }
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
