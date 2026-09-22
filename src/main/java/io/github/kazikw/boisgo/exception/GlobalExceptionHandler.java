package io.github.kazikw.boisgo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import io.github.kazikw.boisgo.dto.response.ErrorResponse;
@Slf4j
@RestControllerAdvice
//@Advice + @Rest controller;  @Advice - Ma prezchwytywać wszyskie wyjątki @Rest bo zwracamy json a nie html
public class GlobalExceptionHandler {


//    @ExceptionHandler(AlreadyParticipantException.class)
//    public ResponseEntity<ErrorResponse> hadleReservationAlreadyFinishedException(ReservationAlreadyFinishedException reservationAlreadyFinishedException){
    @ExceptionHandler(AccessDeniedToReservationException.class)
    public ResponseEntity<ErrorResponse>handleAccessDeniedToReservationException(AccessDeniedToReservationException exception){
        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }

    @ExceptionHandler(OwnerException.class)
    public ResponseEntity<ErrorResponse> handleOwnerExeption(OwnerException ownerException){
        ErrorResponse error = new ErrorResponse(
                ownerException.getMessage(),
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

        @ExceptionHandler(ReservationAlreadyFinishedException.class)
    public ResponseEntity<ErrorResponse> hadleReservationAlreadyFinishedException(ReservationAlreadyFinishedException reservationAlreadyFinishedException){
        ErrorResponse error = new ErrorResponse(
                reservationAlreadyFinishedException.getMessage(),
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


    @ExceptionHandler(ReservationFullException.class)
    public ResponseEntity<ErrorResponse> hadleFullException(ReservationFullException reservationFullException){
        ErrorResponse error = new ErrorResponse(
                reservationFullException.getMessage(),
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse>handleAccessDeniedExeption(AccessDeniedException exception){
            ErrorResponse error = new ErrorResponse(
                    exception.getMessage(), Instant.now()
            );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(error);
    }

    @ExceptionHandler(AlreadyParticipantException.class)
    public ResponseEntity<ErrorResponse> hadleAlreadyParticipantException(AlreadyParticipantException alreadyParticipantException){
        ErrorResponse error = new ErrorResponse(
                alreadyParticipantException.getMessage(),
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }


    @ExceptionHandler(ResourceNotFoundException.class)//Jeżeli złapiesz taki endpoint (jeżeli java rzuci resorce not found Exeption)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException resNoFoundException){
        //To wywołaj metodę handleResourceNotFound przekaż jej tego Exa. A mteoda zwróci Ci body;
        log.warn("Nie znaleziono zasobu!:" , resNoFoundException);
         ErrorResponse error = new ErrorResponse(
                resNoFoundException.getMessage(),
                Instant.now()
        );
//         ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);//    ResponseEntity uyj metody status; metoda status zwraca obiekt bodybuilder; Bodybuilder ma metode body ustawiajaca body która zwraca bodybuilder. Bodybuilder ma metode build która buduje obiekt Repsonse entity;
         return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
//    IllegalStateException

    @ExceptionHandler(ReservationConflict.class)
    public ResponseEntity<ErrorResponse> handleReservationConflict(ReservationConflict conflictException){
//        log.info("Nachodzące się rezerwacje!");
//        log.warn("Nachodzące się rezerwacje! {}" , conflictException.getMessage());
        log.warn("Nachodzące się rezerwacje: {}", conflictException.getMessage());
//        log.warn("Nachodzące się rezerwacje!" , conflictException); //podobno ten log wypisałby cały trace i mogłoby to spowodować wieloma linijkami w consoli
        //Reasumując:  getmessage dla konfliktów biznesowych; A exeption dla nieprzewidzianych wyjatków kiedy MUSZĘ coś przedebugować
        ErrorResponse error = new ErrorResponse(
                conflictException.getMessage(),
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedAction(RuntimeException runtimeException){
        log.error("Somthing unexpected happend!" , runtimeException);
//        ErrorResponse error = new ErrorResponse(
//                runtimeException.getMessage(),
//                Instant.now()
//        );
//      Nie powinniśmy przekazywać do encji exepction Message! Może być niebezpieczne!
        ErrorResponse error = new ErrorResponse(
                "Wystąpił nieoczekiwany błąd serwera",
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.warn("problem walidacji!!!");
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .orElse("Nieprawidłowe dane wejściowe");
        ErrorResponse error = new ErrorResponse(
                message,
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> hanldeUnexeptedExeption(Exception ex){
        log.error("Coś poszło bardzo nie tak! Nawet nie runtimeExeption Tylko zwykly exeption!");

        ErrorResponse error = new ErrorResponse(
                "Wystąpił nieoczekiwany błąd serwera",
                Instant.now()
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
