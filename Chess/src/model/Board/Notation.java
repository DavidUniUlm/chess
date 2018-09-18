package model.Board;

import controller.Translator;
import model.Point;
import model.pieces.Piece;
import view.SpecialMove;

import java.util.ArrayList;

public final class Notation {

    public static String createNotation(ArrayList<Piece> allPieces, Piece pieceMoved, Piece pieceTaken, Point start, Point destination,
                                        SpecialMove specialMove, String promotion){

        String notation = "";
        String column = "";
        String row = "";
        for (Piece otherPiece : allPieces) {
            if (otherPiece.getType().equals(pieceMoved.getType())) { // same color!
                for (Point legalMove : otherPiece.getLegalMoves()) {
                    if (legalMove.getX() == destination.getX() && legalMove.getY() == destination.getY()) {
                        if (!(otherPiece.getPosition().getY() == start.getY())) {
                            column = Double.toString((char) start.getY() + 97);
                        } else {
                            column = "";
                            row = Double.toString(8 - start.getX());
                            break;
                        }
                    }
                }
            }
            if (!row.equals("")) {
                break;
            }
        }

        String code = Character.toString(pieceMoved.getCode()).toUpperCase();

        switch (specialMove) {
            case FALSE:
                if (!code.equals("P")) {
                    notation += code;
                    notation += row.equals("") ? row : column;
                } else { // pawn
                    if (pieceTaken != null) {
                        notation += Translator.getAlgebraicNotationColumn(start);
                    }
                }
                notation += pieceTaken != null ? "x" : "";
                notation += Translator.getAlgebraicNotation(destination);
                break;
            case CASTLE_SHORT:
                notation = "O-O";
                break;
            case CASTLE_LONG:
                notation = "O-O-O";
                break;
            case EN_PASSANT:
                notation += Translator.getAlgebraicNotationColumn(start);
                notation += "x";
                notation += Translator.getAlgebraicNotation(destination);
                break;
            default: // all pawn promotions
                if (pieceTaken != null) {
                    notation += Translator.getAlgebraicNotationColumn(start);
                    notation += "x";
                }
                notation += Translator.getAlgebraicNotation(destination);
                notation += "=" + promotion;
        }

        return notation;
    }



}
