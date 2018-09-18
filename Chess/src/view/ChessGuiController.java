package view;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import model.Point;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.MenuBar;
import model.Move;
import model.pieces.Piece;
import model.pieces.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChessGuiController {

    Controller controller = Main.getController();
    private int width = 60;
    private int height = width;
    private Color white = Color.rgb(200, 200, 200);
    private Color black = Color.rgb(92, 92, 92);


    /**
     * following HashMaps contain all the squares or pieces on the board
     * get a square or piece by calling any coordinate of the board
     */
    HashMap<Point, Rectangle> squares = new HashMap<>();
    HashMap<Point, Rectangle> coloredSquares = new HashMap<>();
    HashMap<Point, Rectangle> pieces = new HashMap<>();

    @FXML
    private GridPane board;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem newGameMenuItem;

    @FXML
    private Pane frame; // a frame around the chessboard

    /**
     * display moves here
     */
    @FXML
    private FlowPane flowPane;

    @FXML
    void newGameAction(ActionEvent event) {
        controller.onNewGameButtonClicked();
    }

    public void clearNotation(){
        flowPane.getChildren().clear();
    }

    public void showNotation(ArrayList<Move> moves) {
        flowPane.getChildren().clear();
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            Label label = new Label(" " + move.getNotation() + " ");

            label.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-background-color: #ebd6e6");
                }
            });
            label.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-opacity: 1");
                }
            });
            label.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-background-color: #c487b6");
                    controller.onNotationClicked(move);
                }
            });
//            label.setOnMouseReleased(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent event) {
//                    label.setStyle("-fx-background-color: #ebd6e6");
//                    System.err.println("Jetzt neue Stellung aufbauen!");
//                }
//            });

            flowPane.getChildren().add(label);
        }
        // color last move
        flowPane.getChildren().get(flowPane.getChildren().size() - 1).setStyle("-fx-background-color: #cccccc");
    }


    EventHandler<MouseEvent> onBoardClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            // TODO: getX zeigt falsche Werte an, sobald ein Padding-Wert (Rahmen) gesetzt wird. JavaFX-Fehler?
            int row = (int) Math.floor((event.getY() - board.getPadding().getTop()) / width);
            int column = (int) Math.floor((event.getX() - board.getPadding().getLeft()) / height);
            controller.onSquareClicked(row, column);
        }
    };

    public void colorSquare(Point coordinates, Color color) {
        coloredSquares.get(coordinates).setFill(color);
    }

    public void resetColors() {
        for (Map.Entry e : coloredSquares.entrySet()) {
            Rectangle square = (Rectangle) e.getValue();
            square.setFill(Color.TRANSPARENT);
        }
    }


    /**
     * creates a board with colored squares and initial pieces
     */
    @FXML
    void initialize() {
        frame.setStyle("-fx-background-color: #130042");
        initializeSquares(); // squares and colored squares
        initializePieces();
        board.setOnMouseClicked(onBoardClicked);
    }


    /**
     * move a piece from a starting square to a destination square
     *
     * @param start       starting coordinates
     * @param destination destination coordinates
     * @param specialMove a move that causes another action like pawn promotion etc.
     */
    public void move(Point start, Point destination, SpecialMove specialMove) {
        switch (specialMove) {
            case FALSE:
                move(start, destination);
                break;
            case CASTLE_SHORT:
                if (start.getX() == 0) { // black castle
                    move(start, destination);
                    move(Square.h8.getCoordinates(), Square.f8.getCoordinates());
                } else { // white castle
                    move(start, destination);
                    move(Square.h1.getCoordinates(), Square.f1.getCoordinates());
                }
                break;
            case CASTLE_LONG:
                if (start.getX() == 0) { // black castle
                    move(start, destination);
                    move(Square.a8.getCoordinates(), Square.d8.getCoordinates());
                } else { // white castle
                    move(start, destination);
                    move(Square.a1.getCoordinates(), Square.d1.getCoordinates());
                }
                break;
            case PROMOTION_KNIGHT_WHITE:
                pieces.get(start).setFill(Color.TRANSPARENT);
                pieces.get(destination).setFill(Type.KNIGHT_WHITE.getImage());
                break;
            case PROMOTION_KNIGHT_BLACK:
                pieces.get(start).setFill(Color.TRANSPARENT);
                pieces.get(destination).setFill(Type.KNIGHT_BLACK.getImage());
                break;
            case PROMOTION_BISHOP_WHITE:
                pieces.get(start).setFill(Color.TRANSPARENT);
                pieces.get(destination).setFill(Type.BISHOP_WHITE.getImage());
                break;
            case PROMOTION_BISHOP_BLACK:
                pieces.get(start).setFill(Color.TRANSPARENT);
                pieces.get(destination).setFill(Type.BISHOP_BLACK.getImage());
                break;
            case PROMOTION_ROOK_WHITE:
                pieces.get(start).setFill(Color.TRANSPARENT);
                pieces.get(destination).setFill(Type.ROOK_WHITE.getImage());
                break;
            case PROMOTION_ROOK_BLACK:
                pieces.get(start).setFill(Color.TRANSPARENT);
                pieces.get(destination).setFill(Type.ROOK_BLACK.getImage());
                break;
            case PROMOTION_QUEEN_WHITE:
                pieces.get(start).setFill(Color.TRANSPARENT);
                pieces.get(destination).setFill(Type.QUEEN_WHITE.getImage());
                break;
            case PROMOTION_QUEEN_BLACK:
                pieces.get(start).setFill(Color.TRANSPARENT);
                pieces.get(destination).setFill(Type.QUEEN_BLACK.getImage());
                break;
            case EN_PASSANT:
                move(start, destination);
                Point takenPawn = (new Point(start.getX(), destination.getY()));
                pieces.get(takenPawn).setFill(Color.TRANSPARENT);
                break;
        }

    }

    /**
     * move a piece from a starting square to a destination square
     *
     * @param start       starting coordinates
     * @param destination destination coordinates
     */
    public void move(Point start, Point destination) {
        Paint Piece = pieces.get(start).getFill();
        pieces.get(start).setFill(Color.TRANSPARENT);
        pieces.get(destination).setFill(Piece);
    }

    /**
     * move a piece from a starting square to a destination square
     *
     * @param x1 starting row
     * @param y1 starting column
     * @param x2 destination row
     * @param y2 destination column
     */
    public void move(int x1, int y1, int x2, int y2) {
        move(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * move a piece from a starting square to a destination square
     *
     * @param x1          starting row
     * @param y1          starting column
     * @param x2          destination row
     * @param y2          destination column
     * @param specialMove a move that causes another action like pawn promotion etc.
     */
    public void move(int x1, int y1, int x2, int y2, SpecialMove specialMove) {
        move(new Point(x1, y1), new Point(x2, y2), specialMove);
    }

    public void setPosition(Piece[][] chessBoard) {
        resetColors();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = chessBoard[r][c];
                if (piece == null) {
                    pieces.get(new Point(r, c)).setFill(Color.TRANSPARENT);
                } else {
                    Paint image = piece.getType().getImage();
                    pieces.get(new Point(r, c)).setFill(image);
                }
            }
        }
    }


    private void initializeSquares() {
        boolean whiteSquare = true;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares.put(new Point(r, c), new Rectangle(width, height, whiteSquare ? white : black));
                board.add(squares.get(new Point(r, c)), c, r);
                coloredSquares.put(new Point(r, c), new Rectangle(width, height, Color.TRANSPARENT));
                board.add(coloredSquares.get(new Point(r, c)), c, r);
                whiteSquare = !whiteSquare;
            }
            whiteSquare = !whiteSquare;
        }
    }


    /**
     * put pieces on the board
     */
    private void initializePieces() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                pieces.put(new Point(r, c), new Rectangle(width, height, Color.TRANSPARENT));
                board.add(pieces.get(new Point(r, c)), c, r);
            }
        }
        //white pieces
        pieces.get(Square.a1.getCoordinates()).setFill(Type.ROOK_WHITE.getImage());
        pieces.get(Square.b1.getCoordinates()).setFill(Type.KNIGHT_WHITE.getImage());
        pieces.get(Square.c1.getCoordinates()).setFill(Type.BISHOP_WHITE.getImage());
        pieces.get(Square.d1.getCoordinates()).setFill(Type.QUEEN_WHITE.getImage());
        pieces.get(Square.e1.getCoordinates()).setFill(Type.KING_WHITE.getImage());
        pieces.get(Square.f1.getCoordinates()).setFill(Type.BISHOP_WHITE.getImage());
        pieces.get(Square.g1.getCoordinates()).setFill(Type.KNIGHT_WHITE.getImage());
        pieces.get(Square.h1.getCoordinates()).setFill(Type.ROOK_WHITE.getImage());
        for (int c = 0; c < 8; c++) {
            pieces.get(new Point(6, c)).setFill(Type.PAWN_WHITE.getImage());
        }
        //black pieces
        pieces.get(Square.a8.getCoordinates()).setFill(Type.ROOK_BLACK.getImage());
        pieces.get(Square.b8.getCoordinates()).setFill(Type.KNIGHT_BLACK.getImage());
        pieces.get(Square.c8.getCoordinates()).setFill(Type.BISHOP_BLACK.getImage());
        pieces.get(Square.d8.getCoordinates()).setFill(Type.QUEEN_BLACK.getImage());
        pieces.get(Square.e8.getCoordinates()).setFill(Type.KING_BLACK.getImage());
        pieces.get(Square.f8.getCoordinates()).setFill(Type.BISHOP_BLACK.getImage());
        pieces.get(Square.g8.getCoordinates()).setFill(Type.KNIGHT_BLACK.getImage());
        pieces.get(Square.h8.getCoordinates()).setFill(Type.ROOK_BLACK.getImage());
        for (int c = 0; c < 8; c++) {
            pieces.get(new Point(1, c)).setFill(Type.PAWN_BLACK.getImage());
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public GridPane getBoard() {
        return board;
    }


}