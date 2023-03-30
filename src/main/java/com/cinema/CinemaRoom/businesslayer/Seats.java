package com.cinema.CinemaRoom.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;

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
    ArrayList<Ticket> seatNotAvailableList = new ArrayList<>(TOTAL_ROWS * TOTAL_COLUMNS);

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

    public ArrayList<Ticket> getSeatNotAvailableList() {
        return seatNotAvailableList;
    }

    public void buySeat(Seat seat) {
        int row = seat.getRow();
        int col = seat.getColumn();

        for (Ticket ticket : seatNotAvailableList) {
            if (ticket.seat.getRow() == row && ticket.getSeat().getColumn() == col) {
                // We check if this seat was purchased, if so, we generate an exception
                throw new RuntimeException();
            }
        }

        String token = String.valueOf(randomUUID());

        for (Seat seatAvailable : seatAvailableList) {
            if (seatAvailable.getRow() == row && seatAvailable.getColumn() == col) {
                // We add the place to the list of purchased, and to the card with the token. Remove from available
                seatNotAvailableList.add(new Ticket(seatAvailable, token));
                seatAvailableList.remove(seatAvailable);
                break;
            }
        }
    }

    public Seat returnSeat(Token t) {
        String token = t.getToken();

        for (Ticket ticket : seatNotAvailableList) {
            if (ticket.getToken().equals(token)) {

                // Again, add to the available
                seatAvailableList.add(ticket.getSeat());

                // Removing space from purchased
                seatNotAvailableList.remove(ticket);
                return ticket.getSeat();
            }
        }

        // If a ticket with this token was not found, we generate an exception
        throw new RuntimeException();
    }

    public Object stats() {
        int currentIncome = 0;
        for (Ticket ticket : seatNotAvailableList) {
            currentIncome += ticket.getSeat().getPrice();
        }

        int numberOfAvailableSeats = seatAvailableList.size();

        int numberOfPurchasedTickets = seatNotAvailableList.size();

        return new Stats(currentIncome, numberOfAvailableSeats, numberOfPurchasedTickets);
    }
}
