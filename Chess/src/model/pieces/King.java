package model.pieces;

import javafx.geometry.Point2D;
import model.Board;
import view.Type;

import java.util.ArrayList;

public class King extends Piece {

    //boolean castling = true;

    public King(Point2D position, boolean white, Board board) {
        super(position, white, board);
        value = Float.POSITIVE_INFINITY;
        type = (white ? Type.KING_WHITE : Type.KING_BLACK);
        if (white)
            code = 'K';
        else
            code = 'k';
    }


    @Override
    public void calculateLegalMoves() {
        legalMoves.clear();
        int r = (int) position.getX();
        int c = (int) position.getY();
        ArrayList<Point2D> possibleMoves = new ArrayList<>();
        // move forwards
        if (r - 1 >= 0) {
            possibleMoves.add(new Point2D(r - 1, c));
            if (c - 1 >= 0)
                possibleMoves.add(new Point2D(r - 1, c - 1));
            if (c + 1 < 8)
                possibleMoves.add(new Point2D(r - 1, c + 1));
        }
        // move backwards
        if (r + 1 < 8) {
            possibleMoves.add(new Point2D(r + 1, c));
            if (c - 1 >= 0)
                possibleMoves.add(new Point2D(r + 1, c - 1));
            if (c + 1 < 8)
                possibleMoves.add(new Point2D(r + 1, c + 1));
        }
        // move left
        if (c - 1 >= 0)
            possibleMoves.add(new Point2D(r, c - 1));
        // move right
        if (c + 1 < 8)
            possibleMoves.add(new Point2D(r, c + 1));
        // make sure you don't move to a square containing your own piece
        if (possibleMoves.size() > 0) {
            for (Point2D possibleMove : possibleMoves) {
                Piece piece = board.getPiece(possibleMove);
                if (piece == null) {
                    legalMoves.add(possibleMove);
                } else {
                    if (piece.isWhite() != this.isWhite()) {
                        legalMoves.add(possibleMove);
                    }
                }
            }
        }
        // short castling
        if (board.isWhiteCastlingShort() && this.isWhite()) {
            if (board.getPiece(r, c + 1) == null && board.getPiece(r, c + 2) == null
            ) {
                legalMoves.add(new Point2D(r, c + 2));
            }
        }
        if (board.isBlackCastlingShort() && !this.isWhite()) {
            if (board.getPiece(r, c + 1) == null && board.getPiece(r, c + 2) == null
            ) {
                legalMoves.add(new Point2D(r, c + 2));
            }
        }
        // long castling
        if (board.isWhiteCastlingLong() && this.isWhite()) {
            if (board.getPiece(r, c - 1) == null
                    && board.getPiece(r, c - 2) == null
                    && board.getPiece(r, c - 3) == null
            ) {
                legalMoves.add(new Point2D(r, c - 2));
            }
        }
        if (board.isBlackCastlingLong() && !this.isWhite()) {
            if (board.getPiece(r, c - 1) == null
                    && board.getPiece(r, c - 2) == null
                    && board.getPiece(r, c - 3) == null
            ) {
                legalMoves.add(new Point2D(r, c - 2));
            }
        }


    }
}