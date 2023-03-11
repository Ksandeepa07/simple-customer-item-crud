package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CustomerController {
    private final static String url="jdbc:mysql://localhost:3306/demo_practical_jdbc?useSSL=false";
    private static final Properties props=new Properties();

    static{
        props.setProperty("user","root");
        props.setProperty("password","1234");

    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField salaryTxt;

    @FXML
    private Button saveBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button itemBtn;

    @FXML
    private Button updateBtn1;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    void saveBtnClick(ActionEvent event) throws SQLException {
        String id=idTxt.getText();
        String name=nameTxt.getText();
        String address=addressTxt.getText();
        String salary=salaryTxt.getText();

        PreparedStatement pstm;
        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql = "INSERT INTO customer (id,name,address,salary) VALUES(?,?,?,?)";
            pstm = connection.prepareStatement(sql);


            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);
            pstm.setString(4, salary);

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
                idTxt.setText(" ");
                nameTxt.setText(" ");
                addressTxt.setText(" ");
                salaryTxt.setText(String.valueOf(" "));
            }
        }

    }

    @FXML
    void deleteBtnClick(ActionEvent event) throws SQLException {
        String id=idTxt.getText();

        PreparedStatement pstm;
        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql = "DELETE FROM customer WHERE id=?";
            pstm = connection.prepareStatement(sql);

            pstm.setString(1, id);
            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted!").show();
                idTxt.setText(" ");
                nameTxt.setText(" ");
                addressTxt.setText(" ");
                salaryTxt.setText(String.valueOf(" "));
            }
        }


    }
    String idd;
    @FXML
    void txtClick(ActionEvent event) throws SQLException {
        idd=idTxt.getText();

        PreparedStatement pstm;
        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql = "SELECT * FROM customer WHERE id=? ";
            pstm = connection.prepareStatement(sql);


            pstm.setString(1, idd);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
//            String id=resultSet.getString(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String salary = resultSet.getString(4);

//            idTxt.setText(id);
                nameTxt.setText(name);
                addressTxt.setText(address);
                salaryTxt.setText(salary);

            }
        }

    }

    @FXML
    void updateBtnClick(ActionEvent event) throws SQLException {
        String id=idTxt.getText();
        String name=nameTxt.getText();
        String address=addressTxt.getText();
        String salary=salaryTxt.getText();

        PreparedStatement pstm;
        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql = "UPDATE customer SET id=?,name=?,address=?,salary=? WHERE id=?";
            pstm = connection.prepareStatement(sql);


            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);
            pstm.setString(4, salary);
            pstm.setString(5, idd);

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
                idTxt.setText(" ");
                nameTxt.setText(" ");
                addressTxt.setText(" ");
                salaryTxt.setText(String.valueOf(" "));
            }
        }


    }

    @FXML
    void itemClick(ActionEvent event) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("/sample/item.fxml"));
        anchorpane.getChildren().clear();
        anchorpane.getChildren().add(load);

    }

    @FXML
    void initialize() {
        assert addressTxt != null : "fx:id=\"addressTxt\" was not injected: check your FXML file 'customer.fxml'.";
        assert idTxt != null : "fx:id=\"idTxt\" was not injected: check your FXML file 'customer.fxml'.";
        assert nameTxt != null : "fx:id=\"nameTxt\" was not injected: check your FXML file 'customer.fxml'.";
        assert salaryTxt != null : "fx:id=\"salaryTxt\" was not injected: check your FXML file 'customer.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'customer.fxml'.";

    }

}
