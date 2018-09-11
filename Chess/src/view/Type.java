package view;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Screen;

public enum Type {

    PAWN_WHITE(new ImagePattern(new Image("/resources/pieces/pawn_white.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    KNIGHT_WHITE(new ImagePattern(new Image("/resources/pieces/knight_white.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    BISHOP_WHITE(new ImagePattern(new Image("/resources/pieces/bishop_white.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    ROOK_WHITE(new ImagePattern(new Image("/resources/pieces/rook_white.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    QUEEN_WHITE(new ImagePattern(new Image("/resources/pieces/queen_white.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    KING_WHITE(new ImagePattern(new Image("/resources/pieces/king_white.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    PAWN_BLACK(new ImagePattern(new Image("/resources/pieces/pawn_black.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    KNIGHT_BLACK(new ImagePattern(new Image("/resources/pieces/knight_black.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    BISHOP_BLACK(new ImagePattern(new Image("/resources/pieces/bishop_black.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    ROOK_BLACK(new ImagePattern(new Image("/resources/pieces/rook_black.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    QUEEN_BLACK(new ImagePattern(new Image("/resources/pieces/queen_black.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true))),
    KING_BLACK(new ImagePattern(new Image("/resources/pieces/king_black.png", 60 * Screen.getPrimary().getOutputScaleX(), 60 * Screen.getPrimary().getOutputScaleY(), true, true)));

    private final ImagePattern img;

    Type(ImagePattern img) {
        this.img = img;
    }

    public ImagePattern getImage() {
        return img;
    }

}
