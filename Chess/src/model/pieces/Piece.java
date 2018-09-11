package model.pieces;

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

    public void removeIllegalMoves(){
//        newlegalmoves
//        saveFen..
//        for legalMoves ...
//            board.move...
//            board.checkForCheck...
//            insert into newlegalmoves oder nicht..
//            resetBoard..
//        legalmoves = newlegalmoves
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

    public void calculateLegalMovesAndCheckForChess() {
        calculateLegalMoves();
        if (legalMoves.size() == 0) {
            return;
        }
        ArrayList<Point2D> realLegalMoves = new ArrayList<>();
        for (Point2D legalMove : legalMoves) {
            System.out.println("LegalMove: " + legalMove);
            if (!moveIntoCheck(new Move(getPosition(), legalMove))) {
                realLegalMoves.add(legalMove);
                System.out.println("realLegalMove: " + legalMove);
            }
        }
        legalMoves = realLegalMoves;
    }

    /**
     * checks if a move is illegal because king could be taken by opponent
     *
     * @param move the move to be checked
     * @return true if player moves into check (illegal), false if move is legal
     */
    public boolean moveIntoCheck(Move move) {
        try {
            Board boardCopy = (Board) board.clone();
            boardCopy.move(move.getStart(), move.getDestination());
            System.out.println("board: " + board);
            System.out.println("boardCopy: " +boardCopy);
            System.out.println("board piece: " + board.getPiece(2,2));
            System.out.println("boardCopy piece: " + boardCopy.getPiece(2,2));
            for (Piece piece : boardCopy.getAllPieces()) {
                piece.calculateLegalMoves();
                for (Point2D destination : legalMoves) {
                    System.out.println(boardCopy.isWhitesTurn());
                    System.out.println(boardCopy.getKing(!boardCopy.isWhitesTurn()).getType());
                    if (destination.equals(boardCopy.getKing(!boardCopy.isWhitesTurn()))) {
                        return true;
                    }
                }
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("das war wohl nix");
            e.printStackTrace();
        }
        return false;
    }


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
