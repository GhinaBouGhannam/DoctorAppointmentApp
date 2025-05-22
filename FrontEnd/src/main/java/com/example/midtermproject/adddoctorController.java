package com.example.midtermproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.midtermproject.HTTPCall.mapToJsonStringDocDetails;

/*For admin to add new doctors as doctors can't sign up*/
public class adddoctorController {

    @FXML
    private TextField docname;
    @FXML
    private TextField docemail;
    @FXML
    private TextField docspecialty;
    @FXML
    private TextField docpass;
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
    @FXML
    private Label notilbl;
    HelloApplication app = new HelloApplication();

    /* To open the home page for admin */
    public void handleHome(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setScene(scene);
    }

    /*The function is called by clicking button of adding new doctor*/
    public void addDoctor(MouseEvent mouseEvent) throws SQLException {
        if (docname.getText() == "" || docspecialty.getText() == "" || doc_description.getText() == "" ||
                doc_availability.getText() == "" || doc_address.getText() == "" || docemail.getText() == "" ||
                doc_phonenumber.getText() == "" || doc_fees.getText() == "" || docpass.getText() == "") {
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


        Pair<Integer, String> res = HTTPCall.executeAPI("POST", "http://localhost:8080/api/healthub/doctor/add ", stringjson);

        if (res.getKey() == HttpURLConnection.HTTP_CREATED) {
            notilbl.setText("Added Successfully!");
            notilbl.setTextFill(Color.GREEN);
        } else {
            notilbl.setText(res.getValue());
            notilbl.setTextFill(Color.RED);
        }
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