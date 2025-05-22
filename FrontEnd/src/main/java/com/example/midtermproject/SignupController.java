package com.example.midtermproject;

import com.fasterxml.jackson.databind.util.JSONPObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.midtermproject.HTTPCall.mapToJsonString;

public class SignupController {

    @FXML
    private TextField usernamefield;
    @FXML
    private TextField passwordfield;
    @FXML
    private TextField phonenumberfield;
    @FXML
    private Label notificationlbl;
    @FXML
    private Button signupbtn;


    public void handleSignup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
        VBox root = (VBox) loader.load();
        signupbtn.setOnAction(e -> {
            String jsonString = mapToJsonString(usernamefield.getText(), passwordfield.getText(), phonenumberfield.getText());
            Pair<Integer,String> res = HTTPCall.executeAPI("POST", "http://localhost:8080/api/healthub/user/add", jsonString);
            if(res.getKey()== HttpURLConnection.HTTP_CREATED) {
                notificationlbl.setTextFill(Color.GREEN);
                notificationlbl.setText("Signed Up Successfully!");
            }
            else {
                    notificationlbl.setTextFill(Color.RED);
                    notificationlbl.setText(res.getValue());
            }

            });

        }
}
