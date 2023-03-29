package com.cinema.CinemaRoom.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

@JsonPropertyOrder({
        "total_rows",
        "total_columns",
        "available_seats",
})
public class Seats {

    @JsonProperty("total_rows")
    final int totalRows = 9;

    @JsonProperty("total_columns")
    final int totalColumns = 9;

    @JsonProperty("available_seats")
    ArrayList<Seat> seatAvailableList = new ArrayList<>(totalRows * totalColumns);

    @JsonIgnore
    ArrayList<Seat> seatNotAvailableList = new ArrayList<>(totalRows * totalColumns);

    public Seats() {
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                seatAvailableList.add(new Seat(i,j));
            }
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public ArrayList<Seat> seatAvailableList() {
        return seatAvailableList;
    }

    public void seatAvailableList(ArrayList<Seat> seatList) {
        this.seatAvailableList = seatList;
    }

    public ArrayList<Seat> getSeatAvailableList() {
        return seatAvailableList;
    }

    public void setSeatAvailableList(ArrayList<Seat> seatAvailableList) {
        this.seatAvailableList = seatAvailableList;
    }

    public ArrayList<Seat> getSeatNotAvailableList() {
        return seatNotAvailableList;
    }

    public void setSeatNotAvailableList(ArrayList<Seat> seatNotAvailableList) {
        this.seatNotAvailableList = seatNotAvailableList;
    }

    public void buySeat(Seat seat) {
        int row = seat.getRow();
        int col = seat.getColumn();
        for (Seat seat1 : seatNotAvailableList) {
            if (seat1.getRow() == row && seat1.getColumn() == col) {
                throw new RuntimeException();
            }
        }
        seatNotAvailableList.add(seatAvailableList.get((row - 1) * totalColumns + (col - 1)));
        seatAvailableList.remove((row - 1) * totalColumns + (col - 1));
    }
}
