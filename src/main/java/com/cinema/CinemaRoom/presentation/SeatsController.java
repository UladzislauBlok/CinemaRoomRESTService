package com.cinema.CinemaRoom.presentation;

import com.cinema.CinemaRoom.businesslayer.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SeatsController {
    Seats seats = new Seats();

    @GetMapping("/seats")
    public Seats getSeats() {
        return seats;
    }

    @PostMapping("/purchase")
    public Object buyTicket(@RequestBody Seat seat) {
        if (seat.getRow() < 1 || seat.getRow() > seats.getTotalRows() ||
                seat.getColumn() < 1 || seat.getColumn() > seats.getTotalColumns()) {
            return new ResponseEntity<>(Map.of("error" , "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
        try {
            seats.buySeat(seat);
            return seats.getSeatNotAvailableList().get(seats.getSeatNotAvailableList().size() - 1);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error" , "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }
    }
}
