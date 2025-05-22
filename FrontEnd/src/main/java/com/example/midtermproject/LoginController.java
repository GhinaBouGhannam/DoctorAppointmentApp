package com.example.midtermproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.midtermproject.HTTPCall.mapToJsonString;
import static com.example.midtermproject.HTTPCall.mapToJsonStringDoc;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label notifilbl;

    private Parent root;
    @FXML
    private Button loginbtn;

    private static int userid;
    private static int docid;
    HelloApplication app = new HelloApplication();
    public void handleLogin(ActionEvent event) {
        loginbtn.setOnAction(e ->
        {
            try { if(username.getText().isEmpty()|| password.getText().isEmpty()){
                notifilbl.setText("Empty Fields!");
                notifilbl.setTextFill(Color.RED);
                return;
            }
                int x = validateLogin(username.getText(), password.getText());
                    if (x !=0) {
                        if (userid != 0&& userid!=1) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
                            root = loader.load();
                            Scene scene = new Scene(root);
                            scene.setFill(Color.TRANSPARENT);
                            app.getPrimarystage().setTitle("Home");
                            app.getPrimarystage().setScene(scene);
                        }
                        else if(userid==1){
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
                            root = loader.load();
                            Scene scene = new Scene(root);
                            scene.setFill(Color.TRANSPARENT);
                            app.getPrimarystage().setTitle("Admin Home Page");
                            app.getPrimarystage().setScene(scene);
                        }
                        else if (docid != 0) {

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("dochome.fxml"));
                            root = loader.load();
                            Scene scene = new Scene(root);
                            scene.setFill(Color.TRANSPARENT);
                            app.getPrimarystage().setTitle("Home ");
                            app.getPrimarystage().setScene(scene);
                        }
                    }else{
                        notifilbl.setTextFill(Color.RED);
                        notifilbl.setText("Wrong Password or Username");
                    }
                        } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (JSONException exception) {
                throw new RuntimeException(exception);
            }

        });
    }

    int validateLogin(String username, String password) throws JSONException {
            String jsonString = mapToJsonString(username, password,"");

            Pair<Integer,String> res = HTTPCall.executeAPI("POST", "http://localhost:8080/api/healthub/user/authenticate", jsonString);
            if(res.getKey()== HttpURLConnection.HTTP_OK) {
                if(!res.getValue().isEmpty()) {
                    JSONObject Obj = new JSONObject(res.getValue());
                    userid = Integer.parseInt(Obj.getString("id"));
                    return 1;
                }
            }
                String jsonStringd = mapToJsonStringDoc(username, password);

                Pair<Integer,String> r = HTTPCall.executeAPI("POST", "http://localhost:8080/api/healthub/doctor/authenticate", jsonStringd);
                if(r.getKey()== HttpURLConnection.HTTP_OK) {
                    if(!r.getValue().isEmpty()){
                    JSONObject Obj = new JSONObject(r.getValue());
                        docid = Integer.parseInt(Obj.getString("doc_id"));
                        return 1;
                    }}

            return 0;}

    public int getUserId(){
        return userid;
    }
    public int getDocId(){
        return docid;
    }

    public void setUserid(int id){
        userid=id;
    }
    public void setDocid(int id){
        docid=id;
    }
}
