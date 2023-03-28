package com.cinema.CinemaRoom.businesslayer;

public class Seat {

    int row;
    int column;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Seat() {
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
