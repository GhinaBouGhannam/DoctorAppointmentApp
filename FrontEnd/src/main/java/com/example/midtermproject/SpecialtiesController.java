package com.example.midtermproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;
import java.sql.*;

public class SpecialtiesController {
    @FXML
    private TextField search;

    @FXML
    private VBox specialties;

   public void initialize() throws SQLException {
           search.setOnAction(event -> {
               String searchText = search.getText().toLowerCase();
               specialties.getChildren().forEach(node -> {
                   if (node instanceof HBox) {
                       HBox hbox = (HBox) node;
                       hbox.getChildren().forEach(labelNode -> {
                           if (labelNode instanceof Label) {
                               Label label = (Label) labelNode;
                               if (searchText.isEmpty()) {
                                   label.setVisible(true);
                               } else {
                                   if (label.getText().toLowerCase().contains(searchText)) {
                                       label.setVisible(true);
                                   } else {
                                       label.setVisible(false);
                                   }
                               }
                           }
                       });
                   }
               });
           });
       }
       public void handleClick(javafx.scene.input.MouseEvent event){
        Label clickedLabel = (Label) event.getSource();

        String specialty = clickedLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorslist.fxml"));
            Parent root = loader.load();
            doctorslistController doctorListController = loader.getController();
            doctorListController.setSpecialty(specialty);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            HelloApplication app = new HelloApplication();
            app.getPrimarystage().setTitle("List Of Doctors");
            app.getPrimarystage().setScene(scene);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
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
