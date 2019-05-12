package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    static Scene scene;
    static Stage stage;
    static Parent root;
    static Main self;

    public static double xOffset = 0;
    public static double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.stage = primaryStage;
        Main.self = this;
        stage.setTitle("Movie Fave");
        //stage.initStyle(StageStyle.UNDECORATED);
        setLayout();
    }

    public static void setLayout() throws IOException {
        Main.setLayout("LoginController");
    }

    public static void setLayout(String layout) throws IOException {
        Parent root = FXMLLoader.load(
                Main.self.getClass().getResource(String.format("../resources/fxml/%s.fxml", layout))
        );

        String title = Main.stage.getTitle();

        Main.stage.close();
        Main.stage = new Stage();
        Main.stage.getIcons().add(new Image(Main.self.getClass().getResourceAsStream("../resources/images/film-reel_ICON.png")));
        Main.stage.setTitle(title);

        if (layout.equals("LoginController") || layout.equals("CreateAccountController") ) {
            stage.initStyle(StageStyle.UNDECORATED);
            /**
             * Making the scene movable / draggable
             */
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
        } else {
            stage.initStyle(StageStyle.DECORATED);
        }


        Main.root = root;
        Main.scene = new Scene(root);

        Main.stage.setScene(Main.scene);
        Main.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
