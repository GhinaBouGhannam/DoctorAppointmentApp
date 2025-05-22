package com.example.midtermproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static com.example.midtermproject.HTTPCall.mapToJsonStringDoc;

public class patientAppointmentsController {
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TableColumn<Appointment, Date> datecol;
    @FXML
    private TableColumn<Appointment, Time> timecol;

    @FXML
    private TableColumn<Appointment,String> namecol;

    @FXML
    private TableColumn<Appointment, String> sttscol;

    @FXML
    private TableColumn<Appointment,String> emailcol;

    @FXML
    private TableColumn<Appointment,String> numcol;

    @FXML
    private TableView<Appointment> tableview;
    @FXML
    private Label lbldate;
    @FXML
    private Label lblNoti;
    @FXML
    private TextField searchfield;

    HelloApplication app= new HelloApplication();
    LoginController control = new LoginController();

    public void initialize() throws SQLException, JSONException {

        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/user/" + control.getUserId() + "/date/" + LocalDate.now(), "");
     if(r.getKey()== HttpURLConnection.HTTP_OK){
        generateTableView(r);
            comboBox.setValue("today");
            handleComboBox();}
        }
    public void handleComboBox() throws SQLException, JSONException {

        String selectedItem = comboBox.getValue();
        if(selectedItem.equals("future")) {
            Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/user/future/" + control.getUserId() + "/date/" + LocalDate.now(), "");
            generateTableView(r);
            lbldate.setText("Future");

        }
        else if(selectedItem.equals("history")){
            Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/user/hist/" + control.getUserId() + "/date/" + LocalDate.now(), "");
            generateTableView(r);
            lbldate.setText("History");

        }
        else {
            Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/user/" + control.getUserId() + "/date/" + LocalDate.now(), "");
            generateTableView(r);
            lbldate.setText("Today");
        }

    }
    @FXML
    private void searchAppointmentsByDate() throws SQLException, JSONException {
        if(searchfield.getText().isEmpty()){
            return;
        }
      else if(isValidDateFormat(searchfield.getText())){
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/user/" + control.getUserId() + "/date/" + LocalDate.parse(searchfield.getText()), "");
       if(r.getKey()== HttpURLConnection.HTTP_OK){
            generateTableView(r);
            comboBox.setValue(" ");
            lbldate.setText("Results for:" + searchfield.getText());}
      }
      else{
            comboBox.setValue(" ");
            tableview.setVisible(false);
            lbldate.setText("Results for:" + searchfield.getText());}
        }

     public void generateTableView( Pair<Integer, String> r) throws SQLException, JSONException {
         datecol.setCellValueFactory(new PropertyValueFactory<>("doa"));
         timecol.setCellValueFactory(new PropertyValueFactory<>("start_time"));
         namecol.setCellValueFactory(new PropertyValueFactory<>("doc_fullname"));
         numcol.setCellValueFactory(new PropertyValueFactory<>("doc_phonenumber"));
         emailcol.setCellValueFactory(new PropertyValueFactory<>("doc_email"));
         sttscol.setCellValueFactory(new PropertyValueFactory<>("appointment_acception"));

         ObservableList<Appointment> data = FXCollections.observableArrayList();
         if (!r.getValue().isEmpty()) {
             JSONArray jsonArray = new JSONArray(r.getValue());

             // Iterate over each JSON object in the array
             for (int i = 0; i < jsonArray.length(); i++) {
                 JSONObject jsonObject = jsonArray.getJSONObject(i);
                 JSONObject docObject = jsonObject.getJSONObject("doc");
                 String doa = jsonObject.getString("doa");
                 String start_time = jsonObject.getString("start_time");
                 String doc_fullname = docObject.getString("doc_fullname");
                 String doc_phonenumber = docObject.getString("doc_phonenumber");
                 String doc_email = docObject.getString("doc_email");
                 String appointment_acception = jsonObject.getString("appointment_acception");
                 String acc;
                 if (appointment_acception.equals("0")) {
                     acc = "Rejected";
                 } else if (appointment_acception.equals("1")) {
                     acc = "Accepted";
                 } else {
                     acc = "Not Confirmed";
                 }

                 data.add((new Appointment(doa, start_time, doc_fullname, doc_email, doc_phonenumber, acc)));
                 System.out.println(data);
             }
             if (data.isEmpty()) {
                 tableview.setVisible(false);
                 lblNoti.setText("No appointments found!");
                 return;
             }
             tableview.setItems(data);
             tableview.refresh();
             System.out.println(tableview.getItems());
             tableview.setVisible(true);
             lblNoti.setText(" ");

         }}
    public static boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {

            return false;
        }
    }

    public void openHome(MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openHome();
    }
    public void openUser(MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openUser();
    }
    public void openSpecialty(MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openSpecialty();
    }

    public void openAppointments(MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openAppointments();
    }
}