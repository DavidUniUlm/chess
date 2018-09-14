package model.Board;

import controller.Translator;
import model.Point;
import model.pieces.Piece;
import model.pieces.Queen;
import model.pieces.Type;
import view.SpecialMove;


/**
 * performes a move
 */
public abstract class MoveHandler {

    /**
     * move a piece from a starting square to a destination square.
     * It has already been checked that it is a legal move.
     * Save move in a list afterwards
     *
     * @param board
     * @param start
     * @param destination
     */
    public static void move(Board board, Point start, Point destination) {
        SpecialMove specialMove = SpecialMove.FALSE;
        int r1 = start.getX();
        int c1 = start.getY();
        int r2 = destination.getX();
        int c2 = destination.getY();
        Piece pieceMoved = board.getPiece(start);
        Piece pieceTaken = board.getPiece(destination);
        String promotion = "";
        Piece[][] chessBoard = board.getChessBoard();

        checkCastling(board, r1, c1, r2, c2);

        // castling long
        if ((board.getPiece(start).getType().equals(Type.KING_WHITE) || board.getPiece(start).getType().equals(Type.KING_BLACK))
                && (c1 - c2) == 2) {
            specialMove = SpecialMove.CASTLE_LONG;
            if (r1 == 0) {
                board.setBlackCastlingShort(false);
                board.setBlackCastlingLong(false);
            } else {
                board.setWhiteCastlingShort(false);
                board.setWhiteCastlingLong(false);
            }
            chessBoard[r1][3] = chessBoard[r1][0];
            chessBoard[r1][0] = null;
            board.getPiece(r1, 3).setPosition(new Point(r1, 3));
        }
        // castling short
        if ((board.getPiece(start).getType().equals(Type.KING_WHITE) || board.getPiece(start).getType().equals(Type.KING_BLACK))
                && (c1 - c2) == -2) {
            specialMove = SpecialMove.CASTLE_SHORT;
            if (r1 == 0) {
                board.setBlackCastlingShort(false);
                board.setBlackCastlingLong(false);
            } else {
                board.setWhiteCastlingShort(false);
                board.setWhiteCastlingLong(false);
            }
            chessBoard[r1][5] = chessBoard[r1][7];
            chessBoard[r1][7] = null;
            board.getPiece(r1, 5).setPosition(new Point(r1, 5));
        }

        // take piece en passant
        if ((board.getPiece(start).getType().equals(Type.PAWN_WHITE) || board.getPiece(start).getType().equals(Type.PAWN_BLACK))
                && Translator.getAlgebraicNotation(r2, c2).equals(board.getEnPassant())) {
            chessBoard[r1][c2] = null; // remove taken pawn
            specialMove = SpecialMove.EN_PASSANT;
        }

        // check en passant next move
        if (board.getPiece(start).getType().equals(Type.PAWN_WHITE) || board.getPiece(start).getType().equals(Type.PAWN_BLACK)) {
            if ((r1 == 1 && r2 == 3)) {
                board.setEnPassant(Translator.getAlgebraicNotation(2, c1));
            } else if ((r1 == 6 && r2 == 4)) {
                board.setEnPassant(Translator.getAlgebraicNotation(5, c1));
            } else {
                board.setEnPassant("-");
            }
        } else {
            board.setEnPassant("-");
        }

        //move
        chessBoard[r2][c2] = chessBoard[r1][c1];
        chessBoard[r1][c1] = null;

        // pawn promotion
        if ((board.getPiece(r2, c2).getType().equals(Type.PAWN_WHITE) || board.getPiece(r2, c2).getType().equals(Type.PAWN_BLACK))
                && (r2 == 0 || r2 == 7)
        ) {
            //Todo Den Nutzer eine Figur wählen lassen
            promotion = "Q"; // remove later
            if (board.isWhitesTurn()) {
                specialMove = SpecialMove.PROMOTION_QUEEN_WHITE;
            } else {
                specialMove = SpecialMove.PROMOTION_QUEEN_BLACK;
            }
            chessBoard[r2][c2] = new Queen(destination, board.isWhitesTurn(), board);
        }

        board.getPiece(r2, c2).setPosition(destination);
        board.setWhitesTurn(!board.isWhitesTurn());
        String notation = Notation.createNotation(board.getAllPieces(), pieceMoved, pieceTaken, start, destination, specialMove, promotion); //TODO: anpassen
        board.saveMove(start, destination, specialMove, notation);

        board.setPreliminaryMoves();
    }


    /**
     * sets booleans of castling
     *
     * @param board
     * @param r1 starting coordinate row
     * @param c1 starting coordinate column
     * @param r2 destination coordinate row
     * @param c2 destination coordinate column
     */
    private static void checkCastling(Board board, int r1, int c1, int r2, int c2) {
        Type piece = board.getPiece(r1, c1).getType();
        // king moves
        if (piece.equals(Type.KING_BLACK)) {
            board.setBlackCastlingShort(false);
            board.setBlackCastlingLong(false);
            return;
        }
        if (piece.equals(Type.KING_WHITE)) {
            board.setWhiteCastlingShort(false);
            board.setWhiteCastlingLong(false);
            return;
        }
        // rook moves or rook is taken
        if ((r1 == 0 && c1 == 0) || (r2 == 0 && c2 == 0)) {
            board.setBlackCastlingLong (false);
        }
        if ((r1 == 0 && c1 == 7) || (r2 == 0 && c2 == 7)) {
            board.setBlackCastlingShort(false);
        }
        if ((r1 == 7 && c1 == 0) || (r2 == 7 && c2 == 0)) {
            board.setWhiteCastlingLong(false);
        }
        if ((r1 == 7 && c1 == 7) || (r2 == 7 && c2 == 7)) {
            board.setWhiteCastlingShort(false);
        }
    }

}
