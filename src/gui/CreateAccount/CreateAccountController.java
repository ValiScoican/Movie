package gui.CreateAccount;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.Business.SQLConnect;
import gui.Main;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import jdk.nashorn.internal.ir.IfNode;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateAccountController {

    Connection conn = SQLConnect.LoginDB();
    ResultSet result;
    PreparedStatement statement;

    public JFXTextField RegUser;
    public JFXPasswordField RegPass;
    public JFXPasswordField ConfirmRegPass;

    public void ReturnToLogin() throws IOException {
        Main.setLayout("LoginController");
    }

//    public void KeyPressed_Username() throws Exception{
//        KeyCode ke = new  KeyCode();
//        if (ke.getKeyCode() ==  KeyEvent.VK_SPACE){
//            Alert alert_1 = new Alert(Alert.AlertType.INFORMATION);
//            alert_1.setTitle("inf dialog");
//            alert_1.setHeaderText(null);
//            alert_1.setContentText("fara sdpb[asod'hsabl;v'sj");
//            alert_1.showAndWait();
//            Thread.sleep(1000);
//            alert_1.close();
//
//        }
//    }


    public void RegButtonPressed() throws IOException {
        if (RegUser.getText().isEmpty() || RegPass.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all required fields!", "Warning", JOptionPane.ERROR_MESSAGE);
        } else {
            // merge si fara if ul asta, lol
            if (!(RegUser.getText().isEmpty() && RegPass.getText().isEmpty())) {
                try {
//                    if (!(RegUser.getText().contains(AttributeTextField) || RegPass.getText().contains(AttributeTextField)))
//                        System.out.println("hi");

                    //RegUser.addEventFilter(KeyEvent.KEY_PRESSED,);
                    String sql = "insert into accounts (username,password) values (?,?)";
                    statement = conn.prepareStatement(sql);
                    statement.setString(1, RegUser.getText());
                    statement.setString(2, RegPass.getText());
                    statement.execute();
//                    if (RegPass.getText() == ConfirmRegPass.getText()){
//
//                        if (RegPass.getText() ==  ConfirmRegPass.getText())


                    //}
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Account Created Successfully!");
                    alert.show();
                    Thread.sleep(1100);
                    alert.close();
                    Main.setLayout("LoginController");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "User already exist!", "Warning", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
