package com.example.midtermproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.example.midtermproject.HTTPCall.mapToJsonString;
import static com.example.midtermproject.HTTPCall.mapToJsonStringAppointment;

public class appointmentController {
    @FXML
    private Label docspecialty;

    @FXML
    private Label docaddress;

    @FXML
    private Label availaility;

    @FXML
    private Label docname;

    @FXML
    private Label phonenumber;

    @FXML
    private Label email;

    @FXML
    private Label fees;

    @FXML
    private TextField time;

    @FXML
    private DatePicker date;

    @FXML
    private TextField description;

    @FXML
    private Label lblerror;
    HelloApplication app = new HelloApplication();
    LoginController control = new LoginController();
    int doctorid;

    public void setDoctor(int docid) throws SQLException, JSONException {
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/" +docid, "");
        if (r.getKey() == HttpURLConnection.HTTP_OK) {
            if (!r.getValue().isEmpty()) {
                JSONObject jsonObj = new JSONObject(r.getValue());
                docname.setText("Dr." + jsonObj.getString("doc_fullname") );
                docid= Integer.parseInt(jsonObj.getString("doc_id"));
                docspecialty.setText( jsonObj.getString("doc_specialty"));
                docaddress.setText(jsonObj.getString("doc_address"));
                availaility.setText(jsonObj.getString("doc_availability"));
                phonenumber.setText( jsonObj.getString("doc_phonenumber"));
                email.setText( jsonObj.getString("doc_email"));
                fees.setText( jsonObj.getString("doc_fees")+" $");
            }
        } doctorid = docid;
    }
    public void handleClick() throws SQLException, IOException, JSONException {
        LocalDate selectedDate = date.getValue();
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/doctor/"+doctorid+"/date/"+selectedDate.toString()+"/time/"+time.getText(), "");
        if (r.getKey() == HttpURLConnection.HTTP_OK) {
            JSONArray array = new JSONArray(r.getValue());
            if(array.length()>0){
                lblerror.setTextFill(Color.RED);
                lblerror.setText("Appointment already assigned");
                return;
        }
        }
        String jsonString = mapToJsonStringAppointment(selectedDate.toString(),time.getText(), description.getText(),control.getUserId(), String.valueOf(doctorid), "2");
             System.out.println(jsonString);
            Pair<Integer, String> rw = HTTPCall.executeAPI("POST", "http://localhost:8080/api/healthub/appointment/add", jsonString);
            if (rw.getKey() == HttpURLConnection.HTTP_CREATED) {
                ConfirmationController c = new ConfirmationController();
                Pair<Integer, String> res = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/appointment/count/"+ control.getUserId(), "");
                JSONArray array1 = new JSONArray(res.getValue());
                if (array1.length()==1) {
                    c.setFirstappointment(true);} else {c.setFirstappointment(false);}
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Confirmation.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    app.getPrimarystage().setTitle("Appointment Confirmed");
                    HelloApplication.getPrimarystage().setScene(scene);
            }
            else{
                lblerror.setTextFill(Color.RED);
                lblerror.setText(r.getValue());
            }
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
