package model.Board;

import model.Point;
import model.pieces.*;

public abstract class FEN {

    public static String createFen(Board board){
        String fen = "";
        for (int r = 0; r < 8; r++) {
            int noPiece = 0;
            for (int c = 0; c < 8; c++) {
                Piece piece = board.getPiece(r, c);
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
        fen += board.isWhitesTurn() ? 'w' : 'b';
        fen += " ";
        fen += board.isWhiteCastlingShort() ? 'K' : "";
        fen += board.isWhiteCastlingLong() ? 'Q' : "";
        fen += board.isBlackCastlingShort() ? 'k' : "";
        fen += board.isBlackCastlingLong() ? 'q' : "";
        if (!(board.isWhiteCastlingShort() || board.isWhiteCastlingLong()
                || board.isBlackCastlingShort() || board.isBlackCastlingLong())) {
            fen += "-";
        }
        fen += " ";
        fen += board.getEnPassant();

        return fen;
    }




    public static void setPosition(Board board, String fen){
        // sample fen looks like this:
        // rnbqkbnr/p1p1p1pp/1p3P2/5p2/2P5/3p4/PP1P1PPP/RNBQKBNR w KQkq -

        String[] fenGroup = fen.split(" ");

        // first group
        Piece[][] chessBoard = new Piece[8][8];
        String[] fenRows = fenGroup[0].split("/");
        for (int r = 0; r < 8; r++) {
            String row = fenRows[r];
            char[] codes = row.toCharArray();
            int emptySquares = 0;
            for (int c = 0; c < 8; c++) {
                char code = codes[c - emptySquares];
                switch (code) {
                    case 'p':
                        chessBoard[r][c] = new Pawn(new Point(r, c), false, board);
                        break;
                    case 'n':
                        chessBoard[r][c] = new Knight(new Point(r, c), false, board);
                        break;
                    case 'b':
                        chessBoard[r][c] = new Bishop(new Point(r, c), false, board);
                        break;
                    case 'r':
                        chessBoard[r][c] = new Rook(new Point(r, c), false, board);
                        break;
                    case 'q':
                        chessBoard[r][c] = new Queen(new Point(r, c), false, board);
                        break;
                    case 'k':
                        chessBoard[r][c] = new King(new Point(r, c), false, board);
                        break;
                    case 'P':
                        chessBoard[r][c] = new Pawn(new Point(r, c), true, board);
                        break;
                    case 'N':
                        chessBoard[r][c] = new Knight(new Point(r, c), true, board);
                        break;
                    case 'B':
                        chessBoard[r][c] = new Bishop(new Point(r, c), true, board);
                        break;
                    case 'R':
                        chessBoard[r][c] = new Rook(new Point(r, c), true, board);
                        break;
                    case 'Q':
                        chessBoard[r][c] = new Queen(new Point(r, c), true, board);
                        break;
                    case 'K':
                        chessBoard[r][c] = new King(new Point(r, c), true, board);
                        break;
                    default:
                        int emptySpace = (int) code - 48;
                        emptySquares += emptySpace - 1;
                        c += emptySquares;
                        break;
                }
            }
        }
        board.setChessBoard(chessBoard);

        // second group
        board.setWhitesTurn(fenGroup[1].equals("w") ? true : false);

        // third group
        for (char i : fenGroup[2].toCharArray()) {
            switch (i) {
                case 'K':
                    board.setWhiteCastlingShort(true);
                    break;
                case 'Q':
                    board.setWhiteCastlingLong(true);
                    break;
                case 'k':
                    board.setBlackCastlingShort(true);
                    break;
                case 'q':
                    board.setBlackCastlingLong(true);
                default:
                    break;
            }
        }

        // fourth group
        board.setEnPassant(fenGroup[3]);
    }

}
