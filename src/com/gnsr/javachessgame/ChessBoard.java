package com.gnsr.javachessgame;

public class ChessBoard {
    private Piece[][] board;

    public ChessBoard(){
        this.board = new Piece[8][8];
        setupPieces();
    }

    private void setupPieces(){
        //initial setup of pawns
    }
}