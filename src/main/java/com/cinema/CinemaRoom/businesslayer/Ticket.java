package com.cinema.CinemaRoom.businesslayer;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "token",
        "ticket",
})
public class Ticket {
    Seat seat;

    String token;

    public Ticket(Seat seat, String token) {
        this.seat = seat;
        this.token = token;
    }

    public Seat getSeat() {
        return seat;
    }

    public String  getToken() {
        return token;
    }
}
