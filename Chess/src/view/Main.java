package view;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static Controller controller = new Controller();
    private static ChessGuiController chessGuiController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader chessGUI = new FXMLLoader(getClass().getResource("chessGUI.fxml"));
        FXMLLoader promotionDialog = new FXMLLoader(getClass().getResource("promotionDialog.fxml"));

        StackPane root = new StackPane();
        root.getChildren().add(chessGUI.load());
        //root.getChildren().add(promotionDialog.load());

        chessGuiController = chessGUI.getController();
        controller.setChessGuiController(chessGuiController);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Controller getController() {
        return controller;
    }

}
