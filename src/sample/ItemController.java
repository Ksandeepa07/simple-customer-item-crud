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

public class ItemController {
    private static final String url="jdbc:mysql://localhost:3306/demo_practical_jdbc? useSSL=false";
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
    private Button deleteBtn;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField qtyTxt;

    @FXML
    private Button saveBtn;

    @FXML
    private Button updateBtn1;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button customerBtn;

    @FXML
    void deleteBtnClick(ActionEvent event) throws SQLException {
        String id=idTxt.getText();

        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql="DELETE FROM item WHERE id=?";
            PreparedStatement pstm=connection.prepareStatement(sql);
            pstm.setString(1,id);
            pstm.executeUpdate();
        }
    }

    @FXML
    void saveBtnClick(ActionEvent event) throws SQLException {
        String id=idTxt.getText();
        String name=nameTxt.getText();
        String quantity=qtyTxt.getText();
        String price=priceTxt.getText();

        PreparedStatement pstm;
        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql = "INSERT INTO item (id,name,quantity,price) VALUES (?,?,?,?)";
            pstm = connection.prepareStatement(sql);


        pstm.setString(1,id);
        pstm.setString(2,name);
        pstm.setString(3,quantity);
        pstm.setString(4,price);

        int affectedRows=pstm.executeUpdate();

        if (affectedRows > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
            idTxt.setText(" ");
            nameTxt.setText(" ");
            qtyTxt.setText(" ");
            priceTxt.setText(" ");
        }
        }

    }
    String idd;
    @FXML
    void txtClick(ActionEvent event) throws SQLException {
        idd=idTxt.getText();

        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql="SELECT * FROM item WHERE id=? ";
            PreparedStatement pstm=connection.prepareStatement(sql);

            pstm.setString(1,idd);
            ResultSet resultSet=pstm.executeQuery();

            if(resultSet.next()){
                String name=resultSet.getString(2);
                String quantity=resultSet.getString(3);
                String price=resultSet.getString(4);

                nameTxt.setText(name);
                qtyTxt.setText(quantity);
                priceTxt.setText(price);

            }

        }

    }

    @FXML
    void updateBtnClick(ActionEvent event) throws SQLException {
        String id=idTxt.getText();
        String name=nameTxt.getText();
        String quantity=qtyTxt.getText();
        String price=priceTxt.getText();

        try (Connection connection = DriverManager.getConnection(url, props)) {
            String sql="UPDATE item SET id=?,name=?,quantity=?,price=? WHERE id=?";
            PreparedStatement pstm=connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,quantity);
            pstm.setString(4,price);
            pstm.setString(5,idd);

            pstm.executeUpdate();
        }

    }

    @FXML
    void customerClick(ActionEvent event) throws IOException {
        Parent load= FXMLLoader.load(getClass().getResource("/sample/customer.fxml"));
        anchorpane.getChildren().clear();
        anchorpane.getChildren().add(load);

    }

    @FXML
    void initialize() {
        assert deleteBtn != null : "fx:id=\"deleteBtn\" was not injected: check your FXML file 'item.fxml'.";
        assert idTxt != null : "fx:id=\"idTxt\" was not injected: check your FXML file 'item.fxml'.";
        assert nameTxt != null : "fx:id=\"nameTxt\" was not injected: check your FXML file 'item.fxml'.";
        assert priceTxt != null : "fx:id=\"priceTxt\" was not injected: check your FXML file 'item.fxml'.";
        assert qtyTxt != null : "fx:id=\"qtyTxt\" was not injected: check your FXML file 'item.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'item.fxml'.";
        assert updateBtn1 != null : "fx:id=\"updateBtn1\" was not injected: check your FXML file 'item.fxml'.";

    }

}
