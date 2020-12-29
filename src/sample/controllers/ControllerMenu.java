package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ConnectionUtil;
import sample.Students;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerMenu {
    Connection conn;
    public ControllerMenu(){
        conn = ConnectionUtil.conDB();
    }
    String tableName = ControllerSelectCourses.tableChange;
    static String a1 = "";
    static String a2 = "";
    static String a3 = "";
    static String a4 = "";

    @FXML
    public BorderPane pane;

    @FXML
    public void export(){
        try {
            List<Students> list = new ArrayList<>();
            String sql = "select * from " + tableName;
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Students students = new Students(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6));
                list.add(students);
            }
            File csv = new File("table.csv");
            csv.createNewFile();
            PrintWriter fw = new PrintWriter(csv, "windows-1251");
            for (Students students: list) {
                fw.write(students.toString());
            }
            fw.close();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/export.fxml"));
            primaryStage.setTitle("Успешно");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
    @FXML
    public void exit (){
        if (a1.isEmpty() && a2.isEmpty() && a3.isEmpty() && a4.isEmpty() || ControllerAppraisals.bool) {
            pane.getScene().getWindow().hide();
        } else {
            try {
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/windowExit.fxml"));
                primaryStage.setTitle("Внимание!");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
