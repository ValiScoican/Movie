package gui.Login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.Business.SQLConnect;
import gui.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    public JFXTextField txtuser;
    public JFXPasswordField txtpassword;

    public static String username;
    public static Integer ID;
    public static String IDs;


    Connection conn = SQLConnect.LoginDB();
    ResultSet result;
    PreparedStatement statement;
    ResultSet result2;
    PreparedStatement statement2;

    @FXML
    public void goRegister(MouseEvent event) throws IOException { Main.setLayout("CreateAccountController"); }
    public void login_text_close_pressed(MouseEvent event) { System.exit(0); }

    public void LoginIn_ButtonPressed() throws IOException {
        String sql = "select * from accounts where username=? and password=?";
        try{
            statement = conn.prepareStatement(sql);
            statement.setString(1,txtuser.getText());
            statement.setString(2,txtpassword.getText());
            result = statement.executeQuery();
            if (result.next()) {
                String sql2 = "select ID from accounts where username = '"+txtuser.getText()+"'";
                try{
                    statement2=conn.prepareStatement(sql2);
                    result2=statement2.executeQuery();
                    if(result2.next()){
                        ID = result.getInt(1);
                        IDs = Integer.toString(ID);
                        result.close();
                        statement.close();
                    }else {
                        System.out.println(0);
                    }
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,e,"Error",JOptionPane.ERROR_MESSAGE);
                }
                // keep in mind
                username = txtuser.getText();
                Main.setLayout("MainWindowController");

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill all required fields correctly!");
                alert.showAndWait();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e,"Error",JOptionPane.ERROR_MESSAGE);

        }
    }
}
