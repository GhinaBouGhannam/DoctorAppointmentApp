package com.example.midtermproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.midtermproject.HTTPCall.mapToJsonString;

public class userController {

    @FXML
    private TextField usernametxt;

    @FXML
    private TextField passwordtxt;

    @FXML
    private TextField numbertxt;

    @FXML
    private Label lblnoti;

    LoginController control = new LoginController();

    HelloApplication app = new HelloApplication();
    String username,password,phonenumber;

    SignupController c = new SignupController();
    public void initialize() throws SQLException, JSONException {
        Pair<Integer,String> res = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/user/"+control.getUserId(), "");
        if(res.getKey()== HttpURLConnection.HTTP_OK) {
            JSONObject obj = new JSONObject(res.getValue());
            username=obj.getString("name");
            password=obj.getString("password");
            phonenumber=obj.getString("phone_number");
        }
    }

    public void openHome(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openHome();
    }
    public void openSpecialty(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openSpecialty();
    }

    public void openAppointments(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openAppointments();
    }
    public void openUser(MouseEvent mouseEvent) throws IOException {
        HomeController c = new HomeController();
        c.openUser();
    }
    public void handleusername(ActionEvent actionEvent) throws SQLException {
        String jsonString = mapToJsonString(usernametxt.getText(), password, phonenumber);
        Pair<Integer,String> res = HTTPCall.executeAPI("PUT", "http://localhost:8080/api/healthub/user/username/"+control.getUserId(), jsonString);
        if(res.getKey()== HttpURLConnection.HTTP_OK) {
            lblnoti.setTextFill(Color.GREEN);
            lblnoti.setText("Username Changed Successfully!");
            username=usernametxt.getText();
        } else{
            lblnoti.setTextFill(Color.RED);
            lblnoti.setText(res.getValue());
        }
    }
    public void handlepassword(ActionEvent actionEvent) throws SQLException {
        String jsonString = mapToJsonString(username, passwordtxt.getText(), phonenumber);
        Pair<Integer,String> res = HTTPCall.executeAPI("PUT", "http://localhost:8080/api/healthub/user/"+control.getUserId(), jsonString);
        if(res.getKey()== HttpURLConnection.HTTP_OK) {
            lblnoti.setTextFill(Color.GREEN);
            lblnoti.setText("Password Changed Successfully!");
            password=passwordtxt.getText();
        }
        else{
            lblnoti.setTextFill(Color.RED);
            lblnoti.setText(res.getValue());
        }
    }

    public void handlephonenumber(ActionEvent actionEvent) throws SQLException, JSONException {
        String jsonString = mapToJsonString(username, password, numbertxt.getText());
        Pair<Integer,String> res = HTTPCall.executeAPI("PUT", "http://localhost:8080/api/healthub/user/"+control.getUserId(), jsonString);
        if(res.getKey()== HttpURLConnection.HTTP_OK) {
            lblnoti.setTextFill(Color.GREEN);
            lblnoti.setText("Phone Number Changed Successfully!");
            phonenumber = numbertxt.getText();
        } else{
            lblnoti.setTextFill(Color.RED);
            lblnoti.setText(res.getValue());
        }
    }
    public void handlelogout(ActionEvent actionEvent) throws IOException {
        control.setUserid(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("HealthHub Login");
        app.getPrimarystage().setScene(scene);
    }
    public void handledelete(ActionEvent actionEvent) throws SQLException, IOException {
        Pair<Integer,String> res = HTTPCall.executeAPI("DELETE", "http://localhost:8080/api/healthub/user/"+control.getUserId(),"");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("HealthHub Login");
        app.getPrimarystage().setScene(scene);


    }
}
