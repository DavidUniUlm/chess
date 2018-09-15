package model;

import model.pieces.Piece;
import view.SpecialMove;

import java.util.ArrayList;
import java.util.HashMap;


public class Move {

    private boolean whitePlayer;
    private Point start;
    private Point destination;
    private String notation;
    private SpecialMove specialMove;
    private String fen;
    private int moveNumber;

    public Move(Point start, Point destination, SpecialMove specialMove, String notation, String fen, ArrayList<Piece> allPieces, int moveNumber) {
        this.start = start;
        this.destination = destination;
        this.specialMove = specialMove;
        this.notation = notation;
        this.fen = fen;
        this.moveNumber = moveNumber;
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

    public boolean isWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(boolean whitePlayer) {
        this.whitePlayer = whitePlayer;
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

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }
}
