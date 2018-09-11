package model.pieces;

import javafx.geometry.Point2D;
import model.Board;
import view.Type;

public class Bishop extends Piece {
    public Bishop(Point2D position, boolean white, Board board) {
        super(position, white, board);
        value = 3;
        type = (white? Type.BISHOP_WHITE : Type.BISHOP_BLACK);
        if (white)
            code = 'B';
        else
            code = 'b';
    }

    @Override
    public void calculateLegalMoves() {
        legalMoves.clear();
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
        while (--r >= 0 && ++c < 8 && board.getPiece(r, c) == null){
            legalMoves.add(new Point2D(r, c));
        }
        if (r >= 0 && c < 8 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point2D(r, c));
        }
    }


}
