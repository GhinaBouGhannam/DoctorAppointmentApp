 package com.example.midtermproject;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.stage.StageStyle;

 public class HelloApplication extends Application {
    protected static Stage primarystage ;
     @Override
    public void start( Stage stage) throws IOException {
        primarystage = stage;
        stage.setTitle("HealthHub Login");
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primarystage.setScene(scene);
        primarystage.show();
    }
    public static Stage getPrimarystage(){
         return primarystage;
    }
     public static void main(String[] args) {
        launch();
    }
}