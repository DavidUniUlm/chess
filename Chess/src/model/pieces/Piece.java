package model.pieces;

import javafx.geometry.Point2D;
import model.Board;

import java.util.ArrayList;

public abstract class Piece {

    boolean white; // white or black piece
    Type type;
    float value;
    char code;
    Point2D position;
    ArrayList<Point2D> legalMoves = new ArrayList<>();
    Board board;

    public Piece() {
    }

    /**
     * removes all moves that are illegal due to check
     */
    public void removeIllegalMoves() {
        ArrayList<Point2D> legalMovesChessChecked = new ArrayList<>();
        for (Point2D legalMove : legalMoves) {
            Board clonedBoard = (Board)board.clone();
            clonedBoard.move(getPosition(), legalMove);
            if (!clonedBoard.checkForCheck()) {
                legalMovesChessChecked.add(legalMove);
            }
        }
        legalMoves = legalMovesChessChecked;
    }


    public Piece(Point2D position, boolean white, Board board) {
        this.position = position;
        this.white = white;
        this.board = board;
    }

    /**
     * calculates every possible move of the king
     */
    public abstract void calculatePreliminaryMoves();


    //getter and setter

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public ArrayList<Point2D> getLegalMoves() {
        return legalMoves;
    }

    public void setLegalMoves(ArrayList legalMoves) {
        this.legalMoves = legalMoves;
    }


}
