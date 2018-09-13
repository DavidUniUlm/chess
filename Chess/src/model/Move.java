package model;

import model.Point;
import javafx.scene.control.Label;
import model.pieces.Piece;
import view.SpecialMove;

import java.util.ArrayList;


public class Move {

    private boolean whitePiece;
    private Point start;
    private Point destination;
    private String notation;
    private SpecialMove specialMove;
    private String fen;
    private int counter;

    public Move(Point start, Point destination, SpecialMove specialMove, String notation, String fen, ArrayList<Piece> allPieces, int counter) {
        this.start = start;
        this.destination = destination;
        this.specialMove = specialMove;
        this.notation = notation;
        this.fen = fen;
        this.counter = counter;
        setNotation(allPieces);
    }

    public Move(Point start, Point destination){
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

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
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
