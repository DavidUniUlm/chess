package model;

import com.rits.cloning.Cloner;
import controller.Translator;
import model.pieces.*;
import view.SpecialMove;
import model.pieces.Type;

import java.util.ArrayList;

public class Board {

    private Piece[][] chessBoard = new Piece[8][8];
    private boolean whitesTurn = true;
    private ArrayList<Piece> allPieces = new ArrayList<>();
    private ArrayList<Move> moves = new ArrayList<>();
    private boolean whiteCastlingShort;
    private boolean whiteCastlingLong;
    private boolean blackCastlingShort;
    private boolean blackCastlingLong;
    private String enPassant = "-";

    /**
     * deep clones any Object.
     * see also https://github.com/kostaskougios/cloning
     *
     * @return
     */
    public Object clone() {
        Cloner cloner = new Cloner();
        Board boardCopy = cloner.deepClone(this);
        return boardCopy;
    }

    /**
     * checks if a position is legal or if the king can be taken
     *
     * @return
     */
    public boolean checkForCheck() {
        King king = (King) (whitesTurn ? getKing(false) : getKing(true));
        for (Piece piece : allPieces) {
            for (Point legalMove : piece.getLegalMoves()) {
                if (legalMove.equals(king.getPosition())) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * sets model.pieces to their initial position on chessboard
     */
    public void setNewGame() {
        setPositionFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -");
        printBoard();
    }

    /**
     *
     * @param fen
     */
    public void setPositionFromFen(String fen) {
        clearBoard();                              // only in case something goes wrong
        resetSpecialMoves();                       // only in case something goes wrong
        FEN.setPosition(this, fen);
        setPreliminaryMoves();
    }

    /**
     *
     * @return the fen notation of the recent position
     */
    public String getPositionAsFen() {
        return FEN.createFen(this);
    }


    /**
     * @param r column
     * @param c row
     * @return the chess piece on choosen position
     */
    public Piece getPiece(int r, int c) {
        return (chessBoard[r][c]);
    }

    public Piece getPiece(Point square) {
        return getPiece(square.getX(), square.getY());
    }

    public boolean checkLegalMove(Point start, Point destination) {
        Piece piece = getPiece(start.getX(), start.getY());
        if (piece.getLegalMoves().contains(destination)) {
            return true;
        }
        return false;
    }

    /**
     * these are temporary moves that also contain illegal moves into check
     */
    public void setPreliminaryMoves() {
        updateAllPieces();
        for (Piece piece : allPieces) {
            piece.calculatePreliminaryMoves();
        }
    }

    /**
     * only use if preliminary moves have been determined before.
     * all illegal moves are removed from preliminary determined moves
     */
    public void setLegalMoves() {
        for (Piece piece : getAllPieces()) {
            if ((isWhitesTurn() && piece.isWhite())
                    || (!isWhitesTurn() && !piece.isWhite())) {
                piece.removeIllegalMoves();
            }
        }
    }

    /**
     * finds and returns the King of a specified color
     * @param white
     * @return
     */
    public Piece getKing(boolean white) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = getPiece(r, c);
                if (piece == null) {
                    continue;
                }
                if (white) {
                    if (piece.getType().equals(Type.KING_WHITE)) {
                        return piece;
                    }
                } else {
                    if (piece.getType().equals(Type.KING_BLACK)) {
                        return piece;
                    }
                }
            }
        }
        return null;
    }


    /**
     *
     * @param start
     * @param destination
     * @param specialMove
     * @param notation
     */
    public void saveMove(Point start, Point destination, SpecialMove specialMove, String notation) {
        int counter = getLastMove() == null ? 1 : getLastMove().getCounter() + 1;
        String addToNotation = "";
        if (counter % 2 != 0) { // white move
            addToNotation = "" + ((counter + 1) / 2) + ".";
        }
        notation = addToNotation + notation;
        moves.add(new Move(start, destination, specialMove, notation, getPositionAsFen(), allPieces, counter));
    }



    /**
     * prints the board to console
     */
    public void printBoard() {
        for (Piece[] row : chessBoard) {
            for (Piece column : row) {
                if (column != null) {
                    System.out.print(column.getCode() + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * puts all pieces on the chessboard into an ArrayList
     */
    public void updateAllPieces() {
        allPieces.clear();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = chessBoard[r][c];
                if (piece != null) {
                    allPieces.add(piece);
                }
            }
        }
    }

//    public String getPGN() {
//        //TODO: Implement this: http://www.saremba.de/chessgml/standards/pgn/pgn-complete.htm
//        return null;
//    }


    /**
     * removes all the pieces from the chessboard
     */
    public void clearBoard() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                chessBoard[r][c] = null;
            }
        }
    }

    /**
     * sets all special moves to default which means none of them is possible
     */
    public void resetSpecialMoves() {
        whiteCastlingShort = false;
        whiteCastlingLong = false;
        blackCastlingShort = false;
        blackCastlingLong = false;
        enPassant = "-";
    }

    public Move getLastMove() {
        if (moves.isEmpty()) {
            return null;
        }
        return moves.get(moves.size() - 1);
    }

    /**
     * move a piece from a starting square to a destination square.
     * It has already been checked that it is a legal move.
     * Save move in a list afterwards
     *
     * @param start
     * @param destination
     */
    public void move(Point start, Point destination) {
        SpecialMove specialMove = SpecialMove.FALSE;
        int r1 = start.getX();
        int c1 = start.getY();
        int r2 = destination.getX();
        int c2 = destination.getY();
        Piece pieceMoved = getPiece(start);
        Piece pieceTaken = getPiece(destination);
        String promotion = "";

        checkCastling(r1, c1, r2, c2);

        // castling long
        if ((getPiece(start).getType().equals(Type.KING_WHITE) || getPiece(start).getType().equals(Type.KING_BLACK))
                && (c1 - c2) == 2) {
            specialMove = SpecialMove.CASTLE_LONG;
            if (r1 == 0) {
                blackCastlingShort = false;
                blackCastlingLong = false;
            } else {
                whiteCastlingShort = false;
                whiteCastlingLong = false;
            }
            chessBoard[r1][3] = chessBoard[r1][0];
            chessBoard[r1][0] = null;
            getPiece(r1, 3).setPosition(new Point(r1, 3));
        }
        // castling short
        if ((getPiece(start).getType().equals(Type.KING_WHITE) || getPiece(start).getType().equals(Type.KING_BLACK))
                && (c1 - c2) == -2) {
            specialMove = SpecialMove.CASTLE_SHORT;
            if (r1 == 0) {
                blackCastlingShort = false;
                blackCastlingLong = false;
            } else {
                whiteCastlingShort = false;
                whiteCastlingLong = false;
            }
            chessBoard[r1][5] = chessBoard[r1][7];
            chessBoard[r1][7] = null;
            getPiece(r1, 5).setPosition(new Point(r1, 5));
        }

        // take piece en passant
        if ((getPiece(start).getType().equals(Type.PAWN_WHITE) || getPiece(start).getType().equals(Type.PAWN_BLACK))
                && Translator.getAlgebraicNotation(r2, c2).equals(enPassant)) {
            chessBoard[r1][c2] = null; // remove taken pawn
            specialMove = SpecialMove.EN_PASSANT;
        }

        // check en passant next move
        if (getPiece(start).getType().equals(Type.PAWN_WHITE) || getPiece(start).getType().equals(Type.PAWN_BLACK)) {
            if ((r1 == 1 && r2 == 3)) {
                enPassant = Translator.getAlgebraicNotation(2, c1);
            } else if ((r1 == 6 && r2 == 4)) {
                enPassant = Translator.getAlgebraicNotation(5, c1);
            } else {
                enPassant = "-";
            }
        } else {
            enPassant = "-";
        }

        //move
        chessBoard[r2][c2] = chessBoard[r1][c1];
        chessBoard[r1][c1] = null;

        // pawn promotion
        if ((getPiece(r2, c2).getType().equals(Type.PAWN_WHITE) || getPiece(r2, c2).getType().equals(Type.PAWN_BLACK))
                && (r2 == 0 || r2 == 7)
        ) {
            //Todo Den Nutzer eine Figur wählen lassen
            promotion = "Q"; // remove later
            if (whitesTurn) {
                specialMove = SpecialMove.PROMOTION_QUEEN_WHITE;
            } else {
                specialMove = SpecialMove.PROMOTION_QUEEN_BLACK;
            }
            chessBoard[r2][c2] = new Queen(destination, whitesTurn, this);
        }

        getPiece(r2, c2).setPosition(destination);
        whitesTurn = !whitesTurn;
        String notation = Notation.createNotation(allPieces, pieceMoved, pieceTaken, start, destination, specialMove, promotion); //TODO: anpassen
        saveMove(start, destination, specialMove, notation);

        setPreliminaryMoves();
    }

    private void checkCastling(int r1, int c1, int r2, int c2) {
        Type piece = getPiece(r1, c1).getType();
        // king moves
        if (piece.equals(Type.KING_BLACK)) {
            blackCastlingShort = false;
            blackCastlingLong = false;
            return;
        }
        if (piece.equals(Type.KING_WHITE)) {
            whiteCastlingShort = false;
            whiteCastlingLong = false;
            return;
        }
        // rook moves or rook is taken
        if ((r1 == 0 && c1 == 0) || (r2 == 0 && c2 == 0)) {
            blackCastlingLong = false;
        }
        if ((r1 == 0 && c1 == 7) || (r2 == 0 && c2 == 7)) {
            blackCastlingShort = false;
        }
        if ((r1 == 7 && c1 == 0) || (r2 == 7 && c2 == 0)) {
            whiteCastlingLong = false;
        }
        if ((r1 == 7 && c1 == 7) || (r2 == 7 && c2 == 7)) {
            whiteCastlingShort = false;
        }
    }


    //getter and setter

    public Piece[][] getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(Piece[][] chessBoard) {
        this.chessBoard = chessBoard;
    }

    public boolean isWhitesTurn() {
        return whitesTurn;
    }

    public void setWhitesTurn(boolean whitesTurn) {
        this.whitesTurn = whitesTurn;
    }

    public ArrayList<Piece> getAllPieces() {
        return allPieces;
    }

    public void setAllPieces(ArrayList<Piece> allPieces) {
        this.allPieces = allPieces;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public boolean isWhiteCastlingShort() {
        return whiteCastlingShort;
    }

    public void setWhiteCastlingShort(boolean whiteCastlingShort) {
        this.whiteCastlingShort = whiteCastlingShort;
    }

    public boolean isWhiteCastlingLong() {
        return whiteCastlingLong;
    }

    public void setWhiteCastlingLong(boolean whiteCastlingLong) {
        this.whiteCastlingLong = whiteCastlingLong;
    }

    public boolean isBlackCastlingShort() {
        return blackCastlingShort;
    }

    public void setBlackCastlingShort(boolean blackCastlingShort) {
        this.blackCastlingShort = blackCastlingShort;
    }

    public boolean isBlackCastlingLong() {
        return blackCastlingLong;
    }

    public void setBlackCastlingLong(boolean blackCastlingLong) {
        this.blackCastlingLong = blackCastlingLong;
    }

    public String getEnPassant() {
        return enPassant;
    }

    public void setEnPassant(String enPassant) {
        this.enPassant = enPassant;
    }
}
