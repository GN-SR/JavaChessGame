package com.gnsr.javachessgame;

import javax.swing.text.Position;

public abstract class Piece {
    protected Positiion position;
    protected PieceColor color;

    public Piece(PieceColor color, Position position){
        this.color = color;
        this.position = position;

    }

    public PieceColor getColor() {
        return color;
    }

    public Positiion getPosition() {
        return position;
    }

    public void setPosition(Positiion position) {
        this.position = position;
    }
    public abstract booleanisValidMove(Position newPosition, Piece[][] board);
}