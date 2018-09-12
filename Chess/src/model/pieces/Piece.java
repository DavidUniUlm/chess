package model.pieces;

import com.google.gson.Gson;
import javafx.geometry.Point2D;
import model.Board;
import model.Move;
import view.Type;

import java.lang.reflect.Array;
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

    public void removeIllegalMoves() {
        ArrayList<Point2D> legalMovesChessChecked = new ArrayList<>();
        //String fen = board.getPosition();
        for (Point2D legalMove : legalMoves) {
            Board clonedBoard = (Board)board.clone();
            clonedBoard.move(getPosition(), legalMove);
            if (!clonedBoard.checkForCheck()) {
                legalMovesChessChecked.add(legalMove);
            }
            //board.setPosition(fen);
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
    public abstract void calculateLegalMoves();


//    /**
//     * checks if a move is illegal because king could be taken by opponent
//     *
//     * @param move the move to be checked
//     * @return true if player moves into check (illegal), false if move is legal
//     */
//    public boolean moveIntoCheck(Move move) {
//        try {
//            Board boardCopy = (Board) board.clone();
//            boardCopy.move(move.getStart(), move.getDestination());
//            System.out.println("board: " + board);
//            System.out.println("boardCopy: " + boardCopy);
//            System.out.println("board piece: " + board.getPiece(2, 2));
//            System.out.println("boardCopy piece: " + boardCopy.getPiece(2, 2));
//            for (Piece piece : boardCopy.getAllPieces()) {
//                piece.calculateLegalMoves();
//                for (Point2D destination : legalMoves) {
//                    System.out.println(boardCopy.isWhitesTurn());
//                    System.out.println(boardCopy.getKing(!boardCopy.isWhitesTurn()).getType());
//                    if (destination.equals(boardCopy.getKing(!boardCopy.isWhitesTurn()))) {
//                        return true;
//                    }
//                }
//            }
//        } catch (CloneNotSupportedException e) {
//            System.out.println("das war wohl nix");
//            e.printStackTrace();
//        }
//        return false;
//    }


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
