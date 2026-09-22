package io.github.kazikw.boisgo.exception;

public class ReservationConflict extends RuntimeException {
    public ReservationConflict(String message) {
        super(message);
    }
}
