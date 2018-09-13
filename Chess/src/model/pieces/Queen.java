package model.pieces;

import javafx.geometry.Point2D;
import model.Board;

public class Queen extends Piece {
    public Queen(Point2D position, boolean white, Board board) {
        super(position, white, board);
        value = 9;
        type = (white? Type.QUEEN_WHITE : Type.QUEEN_BLACK);
        if (white)
            code = 'Q';
        else
            code = 'q';
    }

    @Override
    public void calculatePreliminaryMoves() {
        legalMoves.clear();
        calculateLegalMovesDiagonal();
        calculateLegalMovesStraight();
    }

    /**
     * copied from bishop
     */
    private void calculateLegalMovesDiagonal() {
        int row = (int) position.getX();
        int column = (int) position.getY();
        int r = row;
        int c = column;

        // down right:
        while (++r < 8 && ++c < 8 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point2D(r, c));
        }
        if (r < 8 && c < 8 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point2D(r, c));
        }
        r = row;
        c = column;

        //up left
        while (--r >= 0 && --c >= 0 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point2D(r, c));
        }
        if (r >= 0 && c >= 0 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point2D(r, c));
        }
        r = row;
        c = column;

        //down left
        while (++r < 8 && --c >= 0 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point2D(r, c));
        }
        if (r < 8 && c >= 0 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point2D(r, c));
        }
        r = row;
        c = column;

        //up right
        while (--r >= 0 && ++c < 8 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point2D(r, c));
        }
        if (r >= 0 && c < 8 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point2D(r, c));
        }
    }

    /**
     * copied from Rook
     */
    private void calculateLegalMovesStraight() {
        int row = (int) position.getX();
        int column = (int) position.getY();
        int r = row;
        int c = column;

        // right:
        while (++c < 8 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point2D(r, c));
        }
        if (c < 8 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point2D(r, c));
        }
        r = row;
        c = column;

        //left
        while (--c >= 0 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point2D(r, c));
        }
        if (c >= 0 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point2D(r, c));
        }
        r = row;
        c = column;

        //down
        while (++r < 8 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point2D(r, c));
        }
        if (r < 8 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point2D(r, c));
        }
        r = row;
        c = column;

        //up
        while (--r >= 0 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point2D(r, c));
        }
        if (r >= 0 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point2D(r, c));
        }
    }
}
