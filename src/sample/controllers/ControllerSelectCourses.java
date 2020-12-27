package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.ConnectionUtil;
import java.io.IOException;
import java.sql.Connection;

public class ControllerSelectCourses {
    Connection conn;
    public ControllerSelectCourses(){
        conn = ConnectionUtil.conDB();
    }
    public static String tableChange = "";
    BorderPane root;

    @FXML
    public Button buttonJava;
    @FXML
    public Button buttonBusiness;

    @FXML
    public void business(){
        select(ConnectionUtil.BUSINESS);
        buttonBusiness.getScene().getWindow().hide();
    }
    @FXML
    public void java(){
        select(ConnectionUtil.JAVA);
        buttonJava.getScene().getWindow().hide();
    }
    public void select(String str){
        tableChange = str;
        try {
            Stage primaryStage = new Stage();
            root = (BorderPane) FXMLLoader.load(getClass().getResource("/sample/fxml/menu.fxml"));
            primaryStage.setTitle("Оценки");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            Parent root2 = FXMLLoader.load(getClass().getResource("/sample/fxml/appraisals.fxml"));
            root.setCenter(root2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
