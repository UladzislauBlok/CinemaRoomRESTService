package com.cinema.CinemaRoom.presentation;

import com.cinema.CinemaRoom.businesslayer.Seats;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatsController {
    Seats seats = new Seats();

    @GetMapping("/seats")
    public Seats getSeats() {
        return seats;
    }
}
