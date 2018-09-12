package controller;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import model.Board;
import model.Move;
import model.pieces.Piece;
import view.ChessGuiController;
import view.SpecialMove;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Brain {
    ChessGuiController chessGuiController;
    Board board = new Board();
    Point2D previousClick = null;
    Color color = Color.rgb(180, 0, 0, 0.5);

    public Brain() {
        board.setNewGame();
    }

    public void onSquareClicked(int x, int y) {
        Point2D square = new Point2D(x, y);
        Piece piece = board.getPiece(square);
        if (previousClick == null) {
            chessGuiController.resetColors();
            if (piece != null && (piece.isWhite() == board.isWhitesTurn())) {
                chessGuiController.colorSquare(square, color);
                showLegalMoves(x, y);
                previousClick = square;
            }
        } else {
            boolean legalMove = board.checkLegalMove(previousClick, square);
            if (legalMove) {
                board.move(previousClick, square);
                for (Piece pieceOnBoard : board.getAllPieces()) {
                    if ((board.isWhitesTurn() && pieceOnBoard.isWhite())
                            || (!board.isWhitesTurn() && !pieceOnBoard.isWhite())) {
                        pieceOnBoard.removeIllegalMoves();
                    }
                }
                chessGuiController.showMoves(board.getMoves());
                chessGuiController.resetColors();
                chessGuiController.colorSquare(previousClick, color);
                chessGuiController.colorSquare(square, color);
                chessGuiController.move(previousClick, square, board.getLastMove().getSpecialMove());
            } else {
                chessGuiController.resetColors();
            }
            previousClick = null;
        }
    }

    public void saveGame() {
        if (board.getMoves().size() == 0) {
            System.out.println("no moves to save");
            return;
        }
        File file = new File("src/resources/" + Long.toString(System.currentTimeMillis()) + ".txt");
        try {
            file.createNewFile();
            System.out.println("dsfjls");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (Move move : board.getMoves()) {
                bw.write(move.getNotation());
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLegalMoves(int x, int y) {
        Color colorLegalMoves = Color.rgb(0, 160, 0, 0.2);
        ArrayList legalMoves = board.getPiece(x, y).getLegalMoves();
        if (legalMoves == null) {
            System.out.println("empty square");
            return;
        }
        for (Object square : legalMoves) {
            chessGuiController.colorSquare((Point2D) square, colorLegalMoves);

        }
    }


    public void setChessGuiController(ChessGuiController chessGuiController) {
        this.chessGuiController = chessGuiController;
    }


}
