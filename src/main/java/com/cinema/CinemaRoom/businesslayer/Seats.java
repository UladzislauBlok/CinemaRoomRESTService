package com.cinema.CinemaRoom.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.UUID.randomUUID;

@JsonPropertyOrder({
        "total_rows",
        "total_columns",
        "available_seats",
})
public class Seats {

    @JsonProperty("total_rows")
    private final int TOTAL_ROWS = 9;

    @JsonProperty("total_columns")
    private final int TOTAL_COLUMNS = 9;

    @JsonProperty("available_seats")
    ArrayList<Seat> seatAvailableList = new ArrayList<>(TOTAL_ROWS * TOTAL_COLUMNS);

    @JsonIgnore
    ArrayList<Seat> seatNotAvailableList = new ArrayList<>(TOTAL_ROWS * TOTAL_COLUMNS);

    @JsonIgnore
    Map<String, Seat> seatNotAvailableMap = new HashMap<>();

    public Seats() {
        for (int i = 1; i <= TOTAL_ROWS; i++) {
            for (int j = 1; j <= TOTAL_COLUMNS; j++) {
                // Initialize all seats
                seatAvailableList.add(new Seat(i,j));
            }
        }
    }

    @JsonIgnore
    public int getTotalRows() {
        return TOTAL_ROWS;
    }

    @JsonIgnore
    public int getTotalColumns() {
        return TOTAL_COLUMNS;
    }

    public ArrayList<Seat> getSeatAvailableList() {
        return seatAvailableList;
    }

    public ArrayList<Seat> getSeatNotAvailableList() {
        return seatNotAvailableList;
    }

    public String buySeat(Seat seat) {
        int row = seat.getRow();
        int col = seat.getColumn();

        for (Seat seat1 : seatNotAvailableList) {
            if (seat1.getRow() == row && seat1.getColumn() == col) {
                // We check if this seat was purchased, if so, we generate an exception
                throw new RuntimeException();
            }
        }

        String token = String.valueOf(randomUUID());

        for (Seat seat1 : seatAvailableList) {
            if (seat1.getRow() == row && seat1.getColumn() == col) {
                // We add the place to the list of purchased, and to the card with the token. Remove from available
                seatNotAvailableList.add(seat1);
                seatAvailableList.remove(seat1);
                seatNotAvailableMap.put(token, seat1);
                break;
            }
        }

        return token;
    }

    public Seat returnSeat(Token t) {
        String token = t.getToken();

        if (!seatNotAvailableMap.containsKey(token)) {
            // Checking the card for a token
            throw new RuntimeException();
        } else {
            Seat seat = seatNotAvailableMap.get(token);
            // Removing space from purchased(array and map)
            seatNotAvailableMap.remove(token);
            for (Seat seat1 : seatNotAvailableList) {
                if (seat1.equals(seat)) {
                    seatNotAvailableList.remove(seat1);
                    break;
                }
            }

            // Again, add to the available
            seatAvailableList.add(seat);
            return seat;
        }
    }

    public Object stats() {
        int currentIncome = 0;
        for (Seat seat : seatNotAvailableList) {
            currentIncome += seat.getPrice();
        }

        int numberOfAvailableSeats = seatAvailableList.size();

        int numberOfPurchasedTickets = seatNotAvailableList.size();

        return new Stats(currentIncome, numberOfAvailableSeats, numberOfPurchasedTickets);
    }
}
