package com.example.midtermproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class adminController {

    @FXML
    private TableColumn<Doctor, String> passcol;
    @FXML
    private TableColumn<Doctor, String>availabilitycol;

    @FXML
    private TableColumn<Doctor, String> namecol;

    @FXML
    private TableColumn<Doctor, Integer> numcol;

    @FXML
    private TableColumn<Doctor, Integer> feescol;

    @FXML
    private TableColumn<Doctor, String> descriptioncol ;

    @FXML
    private TableColumn<Doctor, String> addresscol ;

    @FXML
    private TableColumn<Doctor, String> emailcol ;

    @FXML
    private TableColumn<Doctor, String> specialtycol ;

    @FXML
    private TableView<Doctor> tableview;

    @FXML
    private Label notilbl;

    @FXML
    private TextField searchfield;

    static long doc;

    HelloApplication app = new HelloApplication();
    LoginController c = new LoginController();

    public void initialize() throws SQLException, JSONException {
        Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/all", "");
        generateTableView1(r);
        setupRowFactory();
    }

    public boolean generateTableView1(Pair<Integer, String> r ) throws SQLException, JSONException {
        namecol.setCellValueFactory(new PropertyValueFactory<>("doc_fullname"));
        passcol.setCellValueFactory(new PropertyValueFactory<>("doc_password"));
        addresscol.setCellValueFactory(new PropertyValueFactory<>("doc_address"));
        availabilitycol.setCellValueFactory(new PropertyValueFactory<>("doc_availability"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("doc_email"));
        numcol.setCellValueFactory(new PropertyValueFactory<>("doc_phonenumber"));
        feescol.setCellValueFactory(new PropertyValueFactory<>("doc_fees"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("doc_description"));
        specialtycol.setCellValueFactory(new PropertyValueFactory<>("doc_specialty"));

        ObservableList<Doctor> data = FXCollections.observableArrayList();
            if (!r.getValue().isEmpty()) {
                JSONArray jsonArray = new JSONArray(r.getValue());
                // Iterate over each JSON object in the array
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    long doc_id = jsonObject.getLong("doc_id");
            String doc_fullname = jsonObject.getString("doc_fullname");
            String doc_availability=jsonObject.getString("doc_availability");
            String doc_address=jsonObject.getString("doc_address");
            String  doc_description=jsonObject.getString("doc_description");
            String  doc_specialty=jsonObject.getString("doc_specialty");
            int doc_fees=jsonObject.getInt("doc_fees");
            int doc_phonenumber=jsonObject.getInt("doc_phonenumber");
            String doc_password=jsonObject.getString("doc_password");
            String doc_email=jsonObject.getString("doc_email");
            data.add(new Doctor(  doc_fullname, doc_password,doc_email,doc_address, doc_description,
                  doc_specialty,  doc_availability, doc_fees,  doc_phonenumber,doc_id));
        }
        if(data.isEmpty()){
            tableview.setVisible(false);
            notilbl.setText("No appointments found!");
            return false;
        }
        tableview.setItems(data);
        tableview.refresh();
        tableview.setVisible(true);
        notilbl.setText(" ");}
      return true;
    }

     @FXML
    private void searchDoctorByName() throws SQLException, JSONException {
        if(searchfield.getText().isEmpty()){
            initialize();
            return;}
         Pair<Integer, String> r = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/specialty/"+searchfield.getText(), "");
         if(!generateTableView1(r)){
            Pair<Integer, String> res = HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/doctorname/"+searchfield.getText(), "");
            generateTableView1(res);
        }}
    private void setupRowFactory() {
        tableview.setRowFactory(tv -> {
            TableRow<Doctor> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Doctor doctor = row.getItem();
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem deleteItem = new MenuItem("Delete");
                    MenuItem updateItem = new MenuItem("Update");

                    deleteItem.setOnAction(e -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete Doctor");
                        alert.setHeaderText("Are you sure you want to delete this doctor?");
                        alert.setContentText("Doctor: " + doctor.getDoc_fullname());
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            try {
                                Pair<Integer,String> res = HTTPCall.executeAPI("DELETE", "http://localhost:8080/api/healthub/doctor/"+doctor.getDoc_id(),"");

                                generateTableView1(HTTPCall.executeAPI("GET", "http://localhost:8080/api/healthub/doctor/all", ""));
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            } catch (JSONException ex) {
                                throw new RuntimeException(ex);
                            }

                        }
                    });
                    updateItem.setOnAction(e -> {

                         doc= doctor.getDoc_id();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("editdoctor.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        Scene scene = new Scene(root);
                        scene.setFill(Color.TRANSPARENT);
                        app.getPrimarystage().setTitle("Update Doctor");
                        app.getPrimarystage().setScene(scene);

                    });

                    contextMenu.getItems().addAll(deleteItem, updateItem);
                    row.setContextMenu(contextMenu);
                    tableview.setEditable(true);
                }
            });
            return row;
        });
    }
    @FXML
    public void openAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adddoctor.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("New Doctor");
        app.getPrimarystage().setScene(scene);
    }

    public void handlelogout(MouseEvent mouseEvent) throws IOException {
        c.setUserid(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        app.getPrimarystage().setTitle("HealthHub Login ");
        app.getPrimarystage().setScene(scene);

    }

    public static long getDoc(){
        return doc;
    }
}

