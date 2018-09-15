package controller;

import model.Point;
import javafx.scene.paint.Color;
import model.Board.Board;
import model.Move;
import model.pieces.Piece;
import view.ChessGuiController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;

public class Controller {

    private ChessGuiController chessGuiController;
    private Board board = new Board();
    private Point previousClick = null;
    private Color color = Color.rgb(180, 0, 0, 0.5);

    public Controller() {
        board.setNewGame();
    }


    public void onSquareClicked(int r, int c) {
        if (previousClick == null) {
            handleFirstClick(r, c);
        } else {
            handleSecondClick(r, c);
        }
    }

    public void onNotationClicked(Move move){
        board.setPositionFromFen(move.getFen());
        chessGuiController.setPosition(board.getChessBoard());
    }


    private void handleFirstClick(int r, int c) {
        Point square = new Point(r, c);
        Piece piece = board.getPiece(square);

        chessGuiController.resetColors();
        if (piece != null && (piece.isWhite() == board.isWhitesTurn())) {
            chessGuiController.colorSquare(square, color);
            showLegalMoves(r, c);
            previousClick = square;
        }
    }


    private void handleSecondClick(int r, int c) {
        Point square = new Point(r, c);
        Piece piece = board.getPiece(square);

        // clicked on same square again
        if (piece != null && piece.equals(board.getPiece(previousClick))) {
            previousClick = null;
            chessGuiController.resetColors();
            return;
        }
        // clicked on other own piece
        if (piece != null && piece.isWhite() == board.isWhitesTurn()) {
            previousClick = null;
            onSquareClicked(r, c);
            return;
        }

        boolean legalMove = board.checkLegalMove(previousClick, square);
        if (legalMove) handleLegalMove(square);
        else chessGuiController.resetColors();

        previousClick = null;
    }


    private void handleLegalMove(Point destination) {
        // board
        board.move(previousClick, destination);
        board.setLegalMoves();
        System.out.println(board.getLastMove().getNotation());
        board.printBoard();
        // GUI
        chessGuiController.resetColors();
        chessGuiController.colorSquare(previousClick, color);
        chessGuiController.colorSquare(destination, color);
        chessGuiController.move(previousClick, destination, board.getLastMove().getSpecialMove());
        chessGuiController.showNotation(board.getMoves());
    }


    public void saveGame() {
        if (board.getMoves().size() == 0) {
            System.out.println("no moves to save");
            return;
        }
        File file = new File("src/resources/" + Long.toString(System.currentTimeMillis()) + ".txt");
        try {
            file.createNewFile();
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
            chessGuiController.colorSquare((Point) square, colorLegalMoves);
        }
    }


    public void setChessGuiController(ChessGuiController chessGuiController) {
        this.chessGuiController = chessGuiController;
    }

}