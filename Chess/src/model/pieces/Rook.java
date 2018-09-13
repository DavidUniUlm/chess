package model.pieces;

import model.Point;
import model.Board;

public class Rook extends Piece {
    public Rook(Point position, boolean white, Board board) {
        super(position, white, board);
        value = 4.5f;
        type = (white? Type.ROOK_WHITE : Type.ROOK_BLACK);
        if (white)
            code = 'R';
        else
            code = 'r';
    }

    @Override
    public void calculatePreliminaryMoves() {
        legalMoves.clear();
        int row = (int) position.getX();
        int column = (int) position.getY();
        int r = row;
        int c = column;

        // right:
        while (++c < 8 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point(r, c));
        }
        if (c < 8 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point(r, c));
        }
        r = row;
        c = column;

        //left
        while (--c >= 0 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point(r, c));
        }
        if (c >= 0 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point(r, c));
        }
        r = row;
        c = column;

        //down
        while (++r < 8 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point(r, c));
        }
        if (r < 8 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point(r, c));
        }
        r = row;
        c = column;

        //up
        while (--r >= 0 && board.getPiece(r, c) == null) {
            legalMoves.add(new Point(r, c));
        }
        if (r >= 0 && board.getPiece(r, c).isWhite() != this.isWhite()) {
            legalMoves.add(new Point(r, c));
        }
    }

}

