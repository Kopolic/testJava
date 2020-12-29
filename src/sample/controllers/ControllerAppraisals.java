package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.ConnectionUtil;
import sample.Students;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerAppraisals {
    Connection conn;
    public ControllerAppraisals(){
        conn = ConnectionUtil.conDB();
    }
    String tableName = ControllerSelectCourses.tableChange;
    ObservableList<Students> list = FXCollections.observableArrayList();
    Students students;
    String a1 = "";
    String a2 = "";
    String a3 = "";
    String a4 = "";
    String name = "";
    int id;
    static boolean bool = true;

    @FXML
    public TableView<Students> tableView;
    @FXML
    public TableColumn<String,Students> columnName;
    @FXML
    public TableColumn<String,Students> columnA1;
    @FXML
    public TableColumn<String,Students> columnA2;
    @FXML
    public TableColumn<String,Students> columnA3;
    @FXML
    public TableColumn<String,Students> columnA4;
    @FXML
    public TextField txtA1;
    @FXML
    public TextField txtA2;
    @FXML
    public TextField txtA3;
    @FXML
    public TextField txtA4;
    @FXML
    public Text textSelected;
    @FXML
    public Button exit;
    @FXML
    public Button goBack;
    @FXML
    public Label nameTable;
    @FXML
    public Label nameTable1;

    @FXML
    void initialize(){
        if (tableName.equals(ConnectionUtil.JAVA)){
            nameTable1.setText("Объектно-ориентированное программирование на языке Java");
            nameTable.setText("");
        }else {
            nameTable.setText("Бизнес");
            nameTable1.setText("");
        }
        try {
            String sql = "select * from " + tableName;
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                students = new Students(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6));
                list.add(students);
            }
            columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnA1.setCellValueFactory(new PropertyValueFactory<>("a1"));
            columnA2.setCellValueFactory(new PropertyValueFactory<>("a2"));
            columnA3.setCellValueFactory(new PropertyValueFactory<>("a3"));
            columnA4.setCellValueFactory(new PropertyValueFactory<>("a4"));
            tableView.setItems(list);
            getInfo(null);
            tableView.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> getInfo(newValue));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @FXML
    public void keyPressed(){
        ControllerMenu.a1 = txtA1.getText();
        ControllerMenu.a2 = txtA2.getText();
        ControllerMenu.a3 = txtA3.getText();
        ControllerMenu.a4 = txtA4.getText();
    }
    @FXML
    public void save(){
        textSelected.setText("");
        if (id >= 2) {
            bool = true;
            textSelected.setText("");
            String column1 = txtA1.getText();
            String column2 = txtA2.getText();
            String column3 = txtA3.getText();
            String column4 = txtA4.getText();
            if (txtA1.getText().isEmpty()) {
                column1 = a1;
            }
            if (txtA2.getText().isEmpty()) {
                column2 = a2;
            }
            if (txtA3.getText().isEmpty()) {
                column3 = a3;
            }
            if (txtA4.getText().isEmpty()) {
                column4 = a4;
            }
            try {
                String sql = "UPDATE " + tableName + " SET a1 = ?, a2 = ?, a3 = ?, a4 = ? WHERE (id = ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, column1);
                statement.setString(2, column2);
                statement.setString(3, column3);
                statement.setString(4, column4);
                statement.setInt(5, id);
                statement.executeUpdate();
                list.set(id - 1,new Students(id,name,column1,column2,column3,column4));
                textSelected.setText("Данные изменены успешно");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (id == 1){
            textSelected.setText("Выберите человека а не дату");
        } else
            textSelected.setText("Выберите человека в таблице");
    }
    @FXML
    public void exit(){
        a1 = txtA1.getText();
        a2 = txtA2.getText();
        a3 = txtA3.getText();
        a4 = txtA4.getText();
        if (a1.isEmpty() && a2.isEmpty() && a3.isEmpty() && a4.isEmpty() || bool) {
            exit.getScene().getWindow().hide();
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
    @FXML
    public void back (){
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/selectCourses.fxml"));
            stage.setTitle("Выберите предмет");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        goBack.getScene().getWindow().hide();
    }
    public void getInfo(Students students){
        if(students != null){
            a1 = students.getA1();
            a2 = students.getA2();
            a3 = students.getA3();
            a4 = students.getA4();
            name = students.getName();
            id = students.getId();
            textSelected.setText("");
            bool = false;
        }
    }
}
