package io.github.kazikw.boisgo.repository;


import io.github.kazikw.boisgo.entity.ReservationParticipant;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

//@Entity
//Todo Dlaczego nie pisac entity?
public interface ReservationParticipantRepository extends JpaRepository<ReservationParticipant, Long> {

    // Ponieważ max(count(moje rezerwacje )) > max(liczba członkow danej rezerwacji) to dobierzemy sie od 2 strony
    //ZWROCIMY REKORD BO JEST BARDZIEJ UZYTECZNY
    @Query("select rp from  ReservationParticipant rp where rp.reservation.id = :reservationId AND rp.participant.id = :participantId")
    Optional <ReservationParticipant>findByReservationAndUser(@Param("reservationId") Long reservationId, @Param("participantId") Long participantId);



    Long countAllByReservation_Id(Long reservationId);
}
