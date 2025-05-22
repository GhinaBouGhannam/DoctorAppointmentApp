package com.example.midtermproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.midtermproject.HTTPCall.mapToJsonStringDocDetails;

public class EditdoctorController {

    @FXML
    private Label notilbl;
    @FXML
    private TextField docname;
    @FXML
    private TextField docemail;
    @FXML
    private TextField docspecialty;
    @FXML
    private PasswordField docpass;
    @FXML
    private TextField doc_fees;
    @FXML
    private TextField doc_phonenumber;
    @FXML
    private TextField doc_description;
    @FXML
    private TextField doc_availability;
    @FXML
    private TextField doc_address;

    HelloApplication app = new HelloApplication();
    adminController c = new adminController();
    long x;

    public void initialize() throws SQLException, JSONException {
        x = c.getDoc();
        Pair<Integer, String> res = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/" + x, "");

        JSONObject obj = new JSONObject(res.getValue());
        docname.setText(obj.getString("doc_fullname"));
        docemail.setText(obj.getString("doc_email"));
        docspecialty.setText(obj.getString("doc_specialty"));
        docpass.setText(obj.getString("doc_password"));
        doc_fees.setText(String.valueOf(obj.getInt("doc_fees")));
        doc_phonenumber.setText(String.valueOf(obj.getInt("doc_phonenumber")));
        doc_address.setText(obj.getString("doc_address"));
        doc_availability.setText(obj.getString("doc_availability"));
        doc_description.setText(obj.getString("doc_description"));
    }

    public void editDoctor(MouseEvent mouseEvent) throws SQLException {
        if(docname.getText()=="" || doc_description.getText()=="" || doc_availability.getText()==""|| doc_address.getText()==""||
        docemail.getText()=="" || doc_phonenumber.getText()=="" || doc_fees.getText()==""|| docpass.getText()=="" || docspecialty.getText()==""){
            notilbl.setText("Empty Field!");
            notilbl.setTextFill(Color.RED);
            return;
        }
        if (!checkInt(doc_fees.getText())) {
            notilbl.setText("Fees must be whole number !");
            notilbl.setTextFill(Color.RED);
            return;
        }
        String stringjson = mapToJsonStringDocDetails(docname.getText(), doc_description.getText(), doc_availability.getText(), doc_address.getText()
                , docemail.getText(), doc_phonenumber.getText(), Integer.parseInt(doc_fees.getText()), docpass.getText(), docspecialty.getText());

        Pair<Integer, String> res = HTTPCall.executeAPI("PUT", "http://localhost:8080/api/healthub/doctor/" + x, stringjson);
        if (res.getKey() == HttpURLConnection.HTTP_OK) {
            notilbl.setText("Edited Successfully!");
            notilbl.setTextFill(Color.GREEN);
        } else {
            notilbl.setText(res.getValue());
            notilbl.setTextFill(Color.RED);
        }
    }

    public void handleHome(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("Admin Home Page");
        app.getPrimarystage().setScene(scene);
    }
    public boolean checkInt(String x) {
        try {
            int intValue = Integer.parseInt(x);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


