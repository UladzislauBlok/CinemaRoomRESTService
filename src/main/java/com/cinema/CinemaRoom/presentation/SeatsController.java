package com.cinema.CinemaRoom.presentation;

import com.cinema.CinemaRoom.businesslayer.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>(
                    Map.of("token", seats.buySeat(seat), "ticket",
                            seats.getSeatNotAvailableList().get(seats.getSeatNotAvailableList().size() - 1)),
                    HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error" , "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    public Object returnTicket(@RequestBody Token token) {
        try {
            return new ResponseEntity<>(Map.of("returned_ticket", seats.returnSeat(token)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/stats")
    public Object stats(@RequestParam(defaultValue = "incorrectPassword") String password) {
        if (password.equals("super_secret")) {
            return seats.stats();
        } else {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
    }
}
