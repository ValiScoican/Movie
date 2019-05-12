package gui.Business;
import javax.swing.*;
import java.sql.*;

public class SQLConnect {
    Connection conn;
    public static Connection LoginDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinema", "root", "");
            return con;
        }catch (Exception e){
            e.printStackTrace();
           // JOptionPane.showMessageDialog(null,e,"Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }
}

