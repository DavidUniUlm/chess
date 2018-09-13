package model.pieces;

import javafx.geometry.Point2D;
import model.Board;

public class Pawn extends Piece {

    public Pawn(Point2D position, boolean white, Board board) {
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
        int r = (int) position.getX();
        int c = (int) position.getY();

        if (white) {
            // one forward (no out of bounds check necessary)
            if (board.getChessBoard()[r - 1][c] == null) {
                legalMoves.add(new Point2D(r - 1, c));
            }
            // two forward
            if (r == 6 && board.getChessBoard()[r - 1][c] == null
                    && board.getChessBoard()[r - 2][c] == null) {
                legalMoves.add(new Point2D(r - 2, c));
            }
            // take piece to the right
            if (c + 1 < 8
                    && board.getChessBoard()[r - 1][c + 1] != null
                    && !board.getChessBoard()[r - 1][c + 1].white) {
                legalMoves.add(new Point2D(r - 1, c + 1));
            }
            // take piece to the left
            if (c - 1 >= 0
                    && board.getChessBoard()[r - 1][c - 1] != null
                    && !board.getChessBoard()[r - 1][c - 1].white) {
                legalMoves.add(new Point2D(r - 1, c - 1));
            }
            // take piece to the right en passant
            if (r == 3
                    && c + 1 < 8
                    && board.getLastMove() != null
                    && board.getLastMove().getStart().equals(new Point2D(1, c + 1))
                    && board.getLastMove().getDestination().equals(new Point2D(3, c + 1))) {
                legalMoves.add(new Point2D(r - 1, c + 1));
            }
            // take piece to the left en passant
            if (r == 3
                    && c - 1 >= 0
                    && board.getLastMove() != null
                    && board.getLastMove().getStart().equals(new Point2D(1, c - 1))
                    && board.getLastMove().getDestination().equals(new Point2D(3, c - 1))) {
                legalMoves.add(new Point2D(r - 1, c - 1));
            }
        }

        if (!white) {
            // one forward (no out of bounds check necessary)
            if (board.getChessBoard()[r + 1][c] == null) {
                legalMoves.add(new Point2D(r + 1, c));
            }
            // two forward
            if (r == 1 && board.getChessBoard()[r + 1][c] == null
                    && board.getChessBoard()[r + 2][c] == null) {
                legalMoves.add(new Point2D(r + 2, c));
            }
            // take piece to the right (white point of view)
            if (c + 1 < 8
                    && board.getChessBoard()[r + 1][c + 1] != null
                    && board.getChessBoard()[r + 1][c + 1].white) {
                legalMoves.add(new Point2D(r + 1, c + 1));
            }
            // take piece to the left (white point of view)
            if (c - 1 >= 0
                    && board.getChessBoard()[r + 1][c - 1] != null
                    && board.getChessBoard()[r + 1][c - 1].white) {
                legalMoves.add(new Point2D(r + 1, c - 1));
            }
            // take piece to the right en passant
            if (r == 4
                    && c + 1 < 8
                    && board.getLastMove() != null
                    && board.getLastMove().getStart().equals(new Point2D(6, c + 1))
                    && board.getLastMove().getDestination().equals(new Point2D(4, c + 1))) {
                legalMoves.add(new Point2D(r + 1, c + 1));
            }
            // take piece to the left en passant
            if (r == 4
                    && c - 1 >= 0
                    && board.getLastMove() != null
                    && board.getLastMove().getStart().equals(new Point2D(6, c - 1))
                    && board.getLastMove().getDestination().equals(new Point2D(4, c - 1))) {
                legalMoves.add(new Point2D(r + 1, c - 1));
            }
        }
    }


}
