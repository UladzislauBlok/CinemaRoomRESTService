package com.cinema.CinemaRoom.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {

    @JsonIgnore
    private final int LOWER_PRICE = 8;

    @JsonIgnore
    private final int HIGHEST_PRICE = 10;

    @JsonIgnore
    private final int ROW_DIVIDER = 4;

    private int row;

    private int column;

    private int price;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        if (row <= ROW_DIVIDER) {
            this.price = HIGHEST_PRICE;
        } else {
            this.price = LOWER_PRICE;
        }
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

    public int getPrice() {
        return price;
    }
}
