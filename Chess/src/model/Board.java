package model;

import com.google.gson.Gson;
import com.rits.cloning.Cloner;
import javafx.geometry.Point2D;
import model.pieces.*;
import view.SpecialMove;
import view.Type;

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

//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        Board board = (Board) super.clone();
//        board.chessBoard = board.chessBoard.clone();
//        board.allPieces = new ArrayList<>(board.allPieces);
//        //allPieces.forEach(x -> x = x.clone());
//        return board;
//    }


//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        Board clonedBoard = (Board)super.clone();
//        clonedBoard.chessBoard = this.chessBoard.clone();
//        return clonedBoard;
//    }


    public Object clone() {
//        Gson gson = new Gson();
////        String serialized = gson.toJson(this);
////        Board boardCopy = gson.fromJson(serialized, Board.class);
////        return boardCopy;
        Cloner cloner = new Cloner();
        Board boardCopy = cloner.deepClone(this);
        return boardCopy;
    }


    public boolean checkForCheck() {
        King king = (King) (whitesTurn ? getKing(false) : getKing(true));
        for (Piece piece : allPieces) {
            for (Point2D legalMove : piece.getLegalMoves()) {
//                System.out.println(legalMove);
//                System.out.println("king.getPosition(): " + king.getPosition());
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
        whiteCastlingShort = true;
        whiteCastlingLong = true;
        blackCastlingShort = true;
        blackCastlingLong = true;
        enPassant = "-";
        for (int k = 0; k < 8; k++) {
            chessBoard[1][k] = new Pawn(new Point2D(1, k), false, this);
            chessBoard[6][k] = new Pawn(new Point2D(6, k), true, this);
        }
        chessBoard[0][0] = new Rook(new Point2D(0, 0), false, this);
        chessBoard[0][1] = new Knight(new Point2D(0, 1), false, this);
        chessBoard[0][2] = new Bishop(new Point2D(0, 2), false, this);
        chessBoard[0][3] = new Queen(new Point2D(0, 3), false, this);
        chessBoard[0][4] = new King(new Point2D(0, 4), false, this);
        chessBoard[0][5] = new Bishop(new Point2D(0, 5), false, this);
        chessBoard[0][6] = new Knight(new Point2D(0, 6), false, this);
        chessBoard[0][7] = new Rook(new Point2D(0, 7), false, this);

        chessBoard[7][0] = new Rook(new Point2D(7, 0), true, this);
        chessBoard[7][1] = new Knight(new Point2D(7, 1), true, this);
        chessBoard[7][2] = new Bishop(new Point2D(7, 2), true, this);
        chessBoard[7][3] = new Queen(new Point2D(7, 3), true, this);
        chessBoard[7][4] = new King(new Point2D(7, 4), true, this);
        chessBoard[7][5] = new Bishop(new Point2D(7, 5), true, this);
        chessBoard[7][6] = new Knight(new Point2D(7, 6), true, this);
        chessBoard[7][7] = new Rook(new Point2D(7, 7), true, this);

        calculatePossibleMoves();
        printBoard();
    }

    /**
     * @param r column
     * @param c row
     * @return the chess piece on choosen position
     */
    public Piece getPiece(int r, int c) {
        return (chessBoard[r][c]);
    }

    public Piece getPiece(Point2D square) {
        return getPiece((int) square.getX(), (int) square.getY());
    }

    public boolean checkLegalMove(Point2D start, Point2D destination) {
        Piece piece = getPiece((int) start.getX(), (int) start.getY());
        if (piece.getLegalMoves().contains(destination)) {
            return true;
        }
        return false;
    }

    public void calculatePossibleMoves() {
        updateAllPieces();
        for (Piece piece : allPieces) {
            piece.calculateLegalMoves();
        }
    }

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
     * move a piece from a starting square to a destination square.
     * It has already been checked that it is a legal move.
     * Save move in a list afterwards
     *
     * @param start
     * @param destination
     */
    public void move(Point2D start, Point2D destination) {
        SpecialMove specialMove = SpecialMove.FALSE;
        int r1 = (int) start.getX();
        int c1 = (int) start.getY();
        int r2 = (int) destination.getX();
        int c2 = (int) destination.getY();
        Piece pieceMoved = getPiece(start);
        Piece pieceTaken = getPiece(destination);
        String promotion = "";

        // castling long
        if ((getPiece(start).getType().equals(Type.KING_WHITE) || getPiece(start).getType().equals(Type.KING_BLACK))
                && (c1 - c2) == 2) {
            specialMove = SpecialMove.CASTLE_LONG;
            ((King) getPiece(start)).setCastling(false);
            if (r1 == 0) {
                blackCastlingLong = false;
            } else {
                whiteCastlingLong = false;
            }
            chessBoard[r1][3] = chessBoard[r1][0];
            chessBoard[r1][0] = null;
            getPiece(r1, 3).setPosition(new Point2D(r1, 3));
        }
        // castling short
        if ((getPiece(start).getType().equals(Type.KING_WHITE) || getPiece(start).getType().equals(Type.KING_BLACK))
                && (c1 - c2) == -2) {
            specialMove = SpecialMove.CASTLE_SHORT;
            ((King) getPiece(start)).setCastling(false);
            if (r1 == 0) {
                blackCastlingShort = false;
            } else {
                whiteCastlingShort = false;
            }
            chessBoard[r1][5] = chessBoard[r1][7];
            chessBoard[r1][7] = null;
            getPiece(r1, 5).setPosition(new Point2D(r1, 5));
        }

        // take piece en passant
        if ((getPiece(start).getType().equals(Type.PAWN_WHITE) || getPiece(start).getType().equals(Type.PAWN_BLACK))
                && getAlgebraicNotation(r2, c2).equals(enPassant)) {
            chessBoard[r2][c2] = null;
            specialMove = SpecialMove.EN_PASSANT;
        }

        // check en passant next move
        if (getPiece(start).getType().equals(Type.PAWN_WHITE) || getPiece(start).getType().equals(Type.PAWN_BLACK)) {
            if ((r1 == 1 && r2 == 3)) {
                enPassant = getAlgebraicNotation(2, c1);
            } else if ((r1 == 6 && r2 == 4)) {
                enPassant = getAlgebraicNotation(5, c1);
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
        String notation = createNotation(pieceMoved, pieceTaken, start, destination, specialMove, promotion); //TODO: anpassen
        saveMove(start, destination, specialMove, notation);

        printBoard();
        System.out.println("move " + notation + "\n");

        calculatePossibleMoves();
    }

    public void saveMove(Point2D start, Point2D destination, SpecialMove specialMove, String notation) {
        moves.add(new Move(start, destination, specialMove, notation, getPosition(), allPieces));
    }

    public String createNotation(Piece pieceMoved, Piece pieceTaken, Point2D start, Point2D destination, SpecialMove specialMove, String promotion) {
        String notation = "";
        String column = "";
        String row = "";
        for (Piece otherPiece : allPieces) {
            if (otherPiece.getType().equals(pieceMoved.getType())) { // same color!
                for (Point2D legalMove : otherPiece.getLegalMoves()) {
                    if (legalMove.getX() == destination.getX() && legalMove.getY() == destination.getY()) {
                        if (!(otherPiece.getPosition().getY() == start.getY())) {
                            column = Double.toString((char) start.getY() + 97);
                        } else {
                            column = "";
                            row = Double.toString(8 - start.getX());
                            break;
                        }
                    }
                }
            }
            if (!row.equals("")) {
                break;
            }
        }

        String code = Character.toString(pieceMoved.getCode()).toUpperCase();

        switch (specialMove) {
            case FALSE:
                if (!code.equals("P")) {
                    notation += code;
                    notation += row.equals("") ? row : column;
                } else { // pawn
                    if (pieceTaken != null) {
                        notation += getAlgebraicNotationColumn(start);
                    }
                }
                notation += pieceTaken != null ? "x" : "";
                notation += getAlgebraicNotation(destination);
                break;
            case CASTLE_SHORT:
                notation = "O-O";
                break;
            case CASTLE_LONG:
                notation = "O-O-O";
                break;
            case EN_PASSANT:
                notation += getAlgebraicNotationColumn(start);
                notation += "x";
                notation += getAlgebraicNotationRow(destination);
                break;
            default: // all pawn promotions
                if (pieceTaken != null) {
                    notation += getAlgebraicNotationColumn(start);
                    notation += "x";
                }
                notation += getAlgebraicNotation(destination);
                notation += "=" + promotion;
        }

        return notation;
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


    public Piece[][] getChessBoard() {
        return chessBoard;
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

    public String getPosition() {
        String fen = "";
        for (int r = 0; r < 8; r++) {
            int noPiece = 0;
            for (int c = 0; c < 8; c++) {
                Piece piece = getPiece(r, c);
                if (piece == null) {
                    noPiece++;
                } else {
                    if (noPiece != 0) {
                        fen += noPiece;
                        noPiece = 0;
                    }
                    fen += piece.getCode();
                }
            }
            if (noPiece != 0) {
                fen += noPiece;
            }
            if (r != 7) {
                fen += "/";
            }
        }
        fen += " ";
        fen += whitesTurn ? 'w' : 'b';
        fen += " ";
        fen += whiteCastlingShort ? 'K' : "";
        fen += whiteCastlingLong ? 'Q' : "";
        fen += blackCastlingShort ? 'k' : "";
        fen += blackCastlingLong ? 'q' : "";
        if (!(whiteCastlingShort || whiteCastlingLong || blackCastlingShort || blackCastlingLong)) {
            fen += "-";
        }
        fen += " ";
        fen += enPassant;

        return fen;
    }


    public void setPosition(String fen) {
        // sample fen looks like this:
        // rnbqkbnr/p1p1p1pp/1p3P2/5p2/2P5/3p4/PP1P1PPP/RNBQKBNR w KQkq -

        clearBoard();
        resetSpecialMoves();
        String[] fenGroup = fen.split(" ");

        // first group
        String[] fenRows = fenGroup[0].split("/");
        for (int r = 0; r < 8; r++) {
            String row = fenRows[r];
            char[] codes = row.toCharArray();
            int emptySquares = 0;
            for (int c = 0; c < 8; c++) {
                char code = codes[c - emptySquares];
                switch (code) {
                    case 'p':
                        chessBoard[r][c] = new Pawn(new Point2D(r, c), false, this);
                        break;
                    case 'n':
                        chessBoard[r][c] = new Knight(new Point2D(r, c), false, this);
                        break;
                    case 'b':
                        chessBoard[r][c] = new Bishop(new Point2D(r, c), false, this);
                        break;
                    case 'r':
                        chessBoard[r][c] = new Rook(new Point2D(r, c), false, this);
                        break;
                    case 'q':
                        chessBoard[r][c] = new Queen(new Point2D(r, c), false, this);
                        break;
                    case 'k':
                        chessBoard[r][c] = new King(new Point2D(r, c), false, this);
                        break;
                    case 'P':
                        chessBoard[r][c] = new Pawn(new Point2D(r, c), true, this);
                        break;
                    case 'N':
                        chessBoard[r][c] = new Knight(new Point2D(r, c), true, this);
                        break;
                    case 'B':
                        chessBoard[r][c] = new Bishop(new Point2D(r, c), true, this);
                        break;
                    case 'R':
                        chessBoard[r][c] = new Rook(new Point2D(r, c), true, this);
                        break;
                    case 'Q':
                        chessBoard[r][c] = new Queen(new Point2D(r, c), true, this);
                        break;
                    case 'K':
                        chessBoard[r][c] = new King(new Point2D(r, c), true, this);
                        break;
                    default:
                        int emptySpace = (int) code - 48;
                        emptySquares += emptySpace - 1;
                        c += emptySquares;
                        break;
                }
            }
        }

        // second group
        whitesTurn = fenGroup[1].equals("w") ? true : false;

        // third group
        for (char i : fenGroup[2].toCharArray()) {
            switch (i) {
                case 'K':
                    whiteCastlingShort = true;
                    break;
                case 'Q':
                    whiteCastlingLong = true;
                    break;
                case 'k':
                    blackCastlingShort = true;
                    break;
                case 'q':
                    blackCastlingLong = true;
                default:
                    break;
            }
        }

        // fourth group
        enPassant = fenGroup[3];

        calculatePossibleMoves();
    }


    public static String getAlgebraicNotation(Point2D square) {
        return getAlgebraicNotation((int) square.getX(), (int) square.getY());
    }

    public static String getAlgebraicNotation(int r, int c) {
        String algebraicNotation = "";
        algebraicNotation += (char) (97 + c);
        algebraicNotation += 8 - r;
        return algebraicNotation;
    }

    public static String getAlgebraicNotationRow(int r) {
        return "" + (8 - r);
    }

    public static String getAlgebraicNotationRow(Point2D point) {
        return getAlgebraicNotationRow((int) point.getX());
    }

    public static String getAlgebraicNotationColumn(int c) {
        return "" + (char) (97 + c);
    }

    public static String getAlgebraicNotationColumn(Point2D point) {
        return getAlgebraicNotationColumn((int) point.getY());
    }


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

//    public boolean checkLegalPosition(Move move) {
//        // save
//        Board bufferBoard = new Board();
//        for (int r = 0; r < 8; r++) {
//            for (int c = 0; c < 8; c++) {
//                bufferBoard.chessBoard[r][c] = this.chessBoard[r][c];
//            }
//        }
//        bufferBoard[move.getStart()]
//
//        return false;
//    }


    public ArrayList<Move> getMoves() {
        return moves;
    }


    public Move getLastMove() {
        if (moves.isEmpty()) {
            return null;
        }
        return moves.get(moves.size() - 1);
    }

}


