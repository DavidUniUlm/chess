package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

public class PromotionDialogController {

    @FXML
    private HBox promotionHBox;

    @FXML
    private Button queen;

    @FXML
    private Button rook;

    @FXML
    private Button bishop;

    @FXML
    private Button knight;

    @FXML
    void initialize() {
        Image image = new Image("/resources/pieces/Unbenannt.png", queen.getPrefWidth(),queen.getPrefHeight(), false, false);
        queen.setGraphic(new ImageView(image));
        //queen.setBackground(new Background(new BackgroundFill()));
    }

}
