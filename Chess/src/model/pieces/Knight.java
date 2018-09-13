package model.pieces;

import model.Point;
import model.Board;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(Point position, boolean white, Board board) {
        super(position, white, board);
        value = 3;
        type = (white? Type.KNIGHT_WHITE : Type.KNIGHT_BLACK);
        if (white)
            code = 'N';
        else
            code = 'n';
    }

    @Override
    public void calculatePreliminaryMoves() {
        legalMoves.clear();
        int r =  position.getX();
        int c =  position.getY();
        ArrayList<Point> possibleMoves = new ArrayList<>();

        // down right
        possibleMoves.add(new Point(r + 2, c + 1));
        possibleMoves.add(new Point(r + 1, c + 2));
        // down left
        possibleMoves.add(new Point(r + 2, c - 1));
        possibleMoves.add(new Point(r + 1, c - 2));
        // up right
        possibleMoves.add(new Point(r - 2, c + 1));
        possibleMoves.add(new Point(r - 1, c + 2));
        // up left
        possibleMoves.add(new Point(r - 2, c - 1));
        possibleMoves.add(new Point(r - 1, c - 2));

        // search for illegal moves outside board or moves to squares with own piece, then only add legal moves
        for (Point square : possibleMoves) {
            if (!(square.getX() < 0 || square.getY() < 0
                    || square.getX() > 7 || square.getY() > 7
                    || (board.getPiece(square) != null && board.getPiece(square).isWhite() == this.isWhite())
            )) {
                legalMoves.add(square);
            }
        }
    }

}
