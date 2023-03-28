package com.cinema.CinemaRoom.businesslayer;

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
    ArrayList<Seat> seatList = new ArrayList<>(totalRows * totalColumns);

    public Seats() {
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                seatList.add(new Seat(i,j));
            }
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public ArrayList<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(ArrayList<Seat> seatList) {
        this.seatList = seatList;
    }
}
