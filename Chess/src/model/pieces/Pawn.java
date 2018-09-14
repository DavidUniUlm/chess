package model.pieces;

import model.Point;
import model.Board.Board;

public class Pawn extends Piece {

    public Pawn(Point position, boolean white, Board board) {
        super(position, white, board);
        value = 1;
        type = (white? Type.PAWN_WHITE : Type.PAWN_BLACK);
        if (white)
            code = 'P';
        else
            code = 'p';
    }

    /**
     * put all legal moves into a list
     * TODO: look for a check;
     */
    @Override
    public void calculatePreliminaryMoves() {
        legalMoves.clear();
        int r =  position.getX();
        int c =  position.getY();

        if (white) {
            // one forward (no out of bounds check necessary)
            if (board.getChessBoard()[r - 1][c] == null) {
                legalMoves.add(new Point(r - 1, c));
            }
            // two forward
            if (r == 6 && board.getChessBoard()[r - 1][c] == null
                    && board.getChessBoard()[r - 2][c] == null) {
                legalMoves.add(new Point(r - 2, c));
            }
            // take piece to the right
            if (c + 1 < 8
                    && board.getChessBoard()[r - 1][c + 1] != null
                    && !board.getChessBoard()[r - 1][c + 1].white) {
                legalMoves.add(new Point(r - 1, c + 1));
            }
            // take piece to the left
            if (c - 1 >= 0
                    && board.getChessBoard()[r - 1][c - 1] != null
                    && !board.getChessBoard()[r - 1][c - 1].white) {
                legalMoves.add(new Point(r - 1, c - 1));
            }
            // take piece to the right en passant
            if (r == 3
                    && c + 1 < 8
                    && board.getLastMove() != null
                    && board.getLastMove().getStart().equals(new Point(1, c + 1))
                    && board.getLastMove().getDestination().equals(new Point(3, c + 1))) {
                legalMoves.add(new Point(r - 1, c + 1));
            }
            // take piece to the left en passant
            if (r == 3
                    && c - 1 >= 0
                    && board.getLastMove() != null
                    && board.getLastMove().getStart().equals(new Point(1, c - 1))
                    && board.getLastMove().getDestination().equals(new Point(3, c - 1))) {
                legalMoves.add(new Point(r - 1, c - 1));
            }
        }

        if (!white) {
            // one forward (no out of bounds check necessary)
            if (board.getChessBoard()[r + 1][c] == null) {
                legalMoves.add(new Point(r + 1, c));
            }
            // two forward
            if (r == 1 && board.getChessBoard()[r + 1][c] == null
                    && board.getChessBoard()[r + 2][c] == null) {
                legalMoves.add(new Point(r + 2, c));
            }
            // take piece to the right (white point of view)
            if (c + 1 < 8
                    && board.getChessBoard()[r + 1][c + 1] != null
                    && board.getChessBoard()[r + 1][c + 1].white) {
                legalMoves.add(new Point(r + 1, c + 1));
            }
            // take piece to the left (white point of view)
            if (c - 1 >= 0
                    && board.getChessBoard()[r + 1][c - 1] != null
                    && board.getChessBoard()[r + 1][c - 1].white) {
                legalMoves.add(new Point(r + 1, c - 1));
            }
            // take piece to the right en passant
            if (r == 4
                    && c + 1 < 8
                    && board.getLastMove() != null
                    && board.getLastMove().getStart().equals(new Point(6, c + 1))
                    && board.getLastMove().getDestination().equals(new Point(4, c + 1))) {
                legalMoves.add(new Point(r + 1, c + 1));
            }
            // take piece to the left en passant
            if (r == 4
                    && c - 1 >= 0
                    && board.getLastMove() != null
                    && board.getLastMove().getStart().equals(new Point(6, c - 1))
                    && board.getLastMove().getDestination().equals(new Point(4, c - 1))) {
                legalMoves.add(new Point(r + 1, c - 1));
            }
        }
    }


}
