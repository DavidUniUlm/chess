package model;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import model.pieces.Piece;
import view.SpecialMove;

import java.util.ArrayList;


public class Move {

    private boolean whitePiece;
    private Point2D start;
    private Point2D destination;
    private String notation;
    private SpecialMove specialMove;
    private String fen;
    private int counter;

    public Move(Point2D start, Point2D destination, SpecialMove specialMove, String notation, String fen, ArrayList<Piece> allPieces, int counter) {
        this.start = start;
        this.destination = destination;
        this.specialMove = specialMove;
        this.notation = notation;
        this.fen = fen;
        this.counter = counter;
        setNotation(allPieces);
    }

    public Move(Point2D start, Point2D destination){
        this.start = start;
        this.destination = destination;
    }

    private void setNotation(ArrayList<Piece> allPieces) {
        String notation = "";

//        switch (specialMove) {
//            case FALSE:
//
//
//            default:
//                break;
//        }
//        this.notation = notation;
    }

    public String getNotation() {
        return notation;
    }


    // getter and setter

    public boolean isWhitePiece() {
        return whitePiece;
    }

    public void setWhitePiece(boolean whitePiece) {
        this.whitePiece = whitePiece;
    }

    public Point2D getStart() {
        return start;
    }

    public void setStart(Point2D start) {
        this.start = start;
    }

    public Point2D getDestination() {
        return destination;
    }

    public void setDestination(Point2D destination) {
        this.destination = destination;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public SpecialMove getSpecialMove() {
        return specialMove;
    }

    public void setSpecialMove(SpecialMove specialMove) {
        this.specialMove = specialMove;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
