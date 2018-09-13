package model.pieces;

import model.Point;
import model.Board;

public class Bishop extends Piece {
    public Bishop(Point position, boolean white, Board board) {
        super(position, white, board);
        value = 3;
        type = (white? Type.BISHOP_WHITE : Type.BISHOP_BLACK);
        if (white)
            code = 'B';
        else
            code = 'b';
    }

    @Override
    public void calculatePreliminaryMoves() {
        legalMoves.clear();
        int row =  position.getX();
        int column =  position.getY();
        int r = row;
        int c = column;

        // down right:
        while (++r < 8 && ++c < 8 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point(r, c));
        }
        if (r < 8 && c < 8 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point(r, c));
        }
        r = row;
        c = column;

        //up left
        while (--r >= 0 && --c >= 0 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point(r, c));
        }
        if (r >= 0 && c >= 0 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point(r, c));
        }
        r = row;
        c = column;

        //down left
        while (++r < 8 && --c >= 0 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point(r, c));
        }
        if (r < 8 && c >= 0 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point(r, c));
        }
        r = row;
        c = column;

        //up right
        while (--r >= 0 && ++c < 8 && board.getPiece(r, c) == null){
            legalMoves.add(new Point(r, c));
        }
        if (r >= 0 && c < 8 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point(r, c));
        }
    }


}
