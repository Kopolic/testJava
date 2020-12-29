package sample.test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connection {
    java.sql.Connection conn;
    public Connection(){conn = ConnectionUtil.conDB();
    }
    ObservableList<Loxi> list = FXCollections.observableArrayList();
    ObservableList<Loxi2> list2 = FXCollections.observableArrayList();
    String str = "lox";

    @FXML
    public TableView<Loxi> tableViewName;
    @FXML
    public TableColumn<String,Loxi> columnName;
    @FXML
    public TableView<Loxi2> tableViewA;
    @FXML
    public TableColumn<String,Loxi2> columnA1;
    @FXML
    public TableColumn<String,Loxi2> columnA2;

    @FXML
    void initialize(){
        try {
            String sql = "select * from name";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Loxi loxi = new Loxi(
                        resultSet.getInt(1),
                        resultSet.getString(2));
                list.add(loxi);
            }
            columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tableViewName.setItems(list);
            getInfo(null);
            tableViewName.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> getInfo(newValue));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            String sql = "select * from " + str;
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Loxi2 loxi2 = new Loxi2(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
                list2.add(loxi2);
            }
            columnA1.setCellValueFactory(new PropertyValueFactory<>("data"));
            columnA2.setCellValueFactory(new PropertyValueFactory<>("a"));
            tableViewA.setItems(list2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void getInfo(Loxi loxi){
        if (loxi != null){
            str = loxi.getName();
            try {
                int i = 0;
                String sql = "select * from " + str;
                PreparedStatement statement = conn.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()){
                    Loxi2 loxi2 = new Loxi2(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3));
                    list2.set(i++,loxi2);
                }
                i = 0;
                columnA1.setCellValueFactory(new PropertyValueFactory<>("data"));
                columnA2.setCellValueFactory(new PropertyValueFactory<>("a"));
                tableViewA.setItems(list2);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
