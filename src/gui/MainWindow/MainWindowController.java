package gui.MainWindow;

import gui.Business.Movie;
import gui.Business.SQLConnect;
import gui.Login.LoginController;
import gui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static gui.Login.LoginController.IDs;

public class MainWindowController implements Initializable {

    Connection conn = SQLConnect.LoginDB();
    ResultSet result;
    PreparedStatement statement;
    PreparedStatement statement2;

    public Label welcomeLabel;

    public TextField movieName;
    public TextField categoryMovie;
    public TextField lengthMovie;
    public TextField ratingMovie;
    public ComboBox<String> yearMovie;
    public CheckBox seenMovie;

    public TextField SearchID;


    public TableView<Movie> TableView;
    public TableColumn<Movie, String> movieColum;
    public TableColumn<Movie, String> categoryColum;
    public TableColumn<Movie, String> lengthColum;
    public TableColumn<Movie, Double> ratingColum;
    public TableColumn<Movie, Integer> yearColum;
    public TableColumn<Movie, String> seenColum;

    ObservableList<Movie> observableList = FXCollections.observableArrayList();
    //FilteredList<Movie> filteredData = new FilteredList<>(observableList, e -> true);

    public void BackToLoginWindowButtonPressed() throws IOException { Main.setLayout("LoginController"); }

    // DELETE
    public void DeleteButtonPressed() throws SQLException {
        int selectedIndex = TableView.getSelectionModel().getSelectedIndex();
        String selectedItem = movieColum.getCellData(selectedIndex);
        System.out.println(selectedItem);
        //System.out.println("ID = "selectedIndex);
        if (selectedIndex >= 0) {
            conn = SQLConnect.LoginDB();
            String query1 = "DELETE FROM movies WHERE name = ? AND ID_user = ?";
            statement = conn.prepareStatement(query1);
            statement.setString(1, selectedItem);
            statement.setString(2, IDs);
            statement.execute();

            fetch();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You have not selected an item to delete.");
            alert.showAndWait();
        }
    }


    /**
     * Search function
     *
     * @usage Filter table data
     */
    public void search() {
        // Search if search text and text length >= 3
        if (SearchID.getText().isEmpty() && SearchID.getText().length() < 3) {
            TableView.setItems(observableList);
        } else {
            // Set Table Items with filtered items /gg
            TableView.setItems(
                    search(SearchID.getText(), observableList)
            );
        }
    }

    /**
     * @param sub
     * @param full
     * @return
     */
    public ObservableList<Movie> search(String sub, ObservableList<Movie> full) {
        ObservableList<Movie> result = FXCollections.observableArrayList(); // create empty observable list
        boolean add; // add conditional stop
        for (Movie part : full) { // For each loop (for (i =0; i < full.length; i++ ) { part = full[i]; ... }
            add = false;

            // Filter items, if match, add to the list
            if (contain(part.getMovieName(), sub)) {
                add = true;
            } else if (contain(part.getCategoryMovie(), sub)) {
                add = true;
            } else if (contain(part.getLengthMovie(), sub)) {
                add = true;
            } else if (contain(part.getRatingMovie(), sub)) {
                add = true;
            } else if (contain(part.getYearMovie(), sub)) {
                add = true;
            } else if (contain(part.getSeenMovie(), sub)) {
                add = true;
            }
            if (add) {
                result.add(part);
            }
        }
        return result;
    }

    private boolean contain(Double text, String part) {
        return contain(String.valueOf(text), part);
    }

    private boolean contain(String text, String part) {
        text = text.trim().toLowerCase();
        part = part.trim().toLowerCase();
        return text.equals(part) || text.contains(part);
    }

    public void AddNewMovieButtonPressed() {
        if (movieName.getText().isEmpty() || categoryMovie.getText().isEmpty() || lengthMovie.getText().isEmpty() ||
                ratingMovie.getText().isEmpty() || yearMovie.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please fill all required fields!");
            alert.showAndWait();
        } else {
            String sql2 = "select * from movies where name = '" + movieName.getText() + "' AND ID_user='" + IDs + "'";
            System.out.println(movieName);
            System.out.println(IDs);
            try {
                statement2 = conn.prepareStatement(sql2);
                result = statement2.executeQuery();
                if (result.next()) {
                    JOptionPane.showMessageDialog(null, "This movie already exist", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String sql = "insert into movies (ID_user,name,category,length,rating,year,seen) values (?,?,?,?,?,?,?)";
                    statement = conn.prepareStatement(sql);
                    statement.setString(1, IDs);
                    statement.setString(2, movieName.getText());
                    statement.setString(3, categoryMovie.getText());
                    statement.setString(4, lengthMovie.getText());
                    statement.setString(5, ratingMovie.getText());
                    statement.setString(6, yearMovie.getValue());
                    statement.setString(7, seenMovie.isSelected() ? "Yes" : "No");
                    statement.execute();
                    statement.close();
                    // if all good, on every new item we will fetch data from db
                    fetch();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeLabel.setText(LoginController.username);

        fetch(); // reusable fetch moovies function  // adica aia care ia datele

        ObservableList<String> years = FXCollections.observableArrayList();
        for (int year = 2019; year > 1999; year--) {
            years.add(String.valueOf(year));
        }

        yearMovie.setItems(years);
        yearMovie.setValue("2019");

        movieColum.setCellValueFactory(new PropertyValueFactory<>("movieName"));
        categoryColum.setCellValueFactory(new PropertyValueFactory<>("categoryMovie"));
        lengthColum.setCellValueFactory(new PropertyValueFactory<>("lengthMovie"));
        ratingColum.setCellValueFactory(new PropertyValueFactory<>("ratingMovie"));
        yearColum.setCellValueFactory(new PropertyValueFactory<>("yearMovie"));
        seenColum.setCellValueFactory(new PropertyValueFactory<>("seenMovie"));
        TableView.setItems(observableList);
        TableView.setVisible(true);
        TableView.refresh();
    }

    private void fetch() {
        try {
            observableList.clear(); // clear the list
            Connection con = SQLConnect.LoginDB();
            ResultSet result = con.createStatement().executeQuery("select * from  movies where ID_user = '" + IDs + "'");

            while (result.next()) {
                // and fill with fetched data
                observableList.add(new Movie(
                        result.getString("name"),
                        result.getString("category"),
                        result.getString("length"),
                        result.getDouble("rating"),
                        result.getString("year"),
                        result.getString("seen")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Movie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

