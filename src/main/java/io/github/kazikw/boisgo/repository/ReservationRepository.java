package io.github.kazikw.boisgo.repository;

import io.github.kazikw.boisgo.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    //
    @Query("""
    SELECT RP.reservation
    FROM ReservationParticipant RP
    WHERE RP.participant.id = :userId 
    ORDER BY 
        CASE 
            WHEN RP.reservation.date >= :today THEN 0 
            ELSE 1 
        END,
        RP.reservation.date ASC
""")
    Page<Reservation> findParticipantSorted(@Param("today") LocalDate today, @Param("userId") Long userId, Pageable pageable );

    //



    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Reservation r
            WHERE r.facility.id = :facilityId
              AND r.date = :date
              AND r.startTime = :startTime
            """)
    boolean existsConflictingReservation(
            @Param("facilityId") Long facilityId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime
    );

    @Query("SELECT rp.reservation FROM ReservationParticipant rp WHERE rp.participant.id = :userId")
    Page<Reservation> findReservationsByParticipantId(
            @Param("userId") Long userId,
            Pageable pageable
    );


    @Query("""
SELECT r
FROM Reservation r
WHERE r.facility.id = :facilityId
  AND r.date = :date
""")    List<Reservation> findByFacilityIdAndDate(
            @Param("facilityId") Long facilityId,
            @Param("date") LocalDate date);

//dupa dupa
@Query("""
    SELECT r
    FROM Reservation r
    WHERE r.facility.city = :city
      AND r.date > CURRENT_DATE
      AND NOT EXISTS (
            SELECT rp
            FROM ReservationParticipant rp
            WHERE rp.reservation = r
              AND rp.participant.id = :userId
      )
      AND (
            r.isPublic = true
            OR EXISTS (
                SELECT fr
                FROM FriendRelations fr
                JOIN ReservationParticipant rp2 ON rp2.participant.id = fr.userB.id
                WHERE rp2.reservation = r
                  AND fr.userA.id = :userId
            )
      )
""")
Page<Reservation> findJoinableByCity(
        @Param("city") String city,
        @Param("userId") Long userId,
        Pageable pageable
);

    @Query("""
    select  r FROM Reservation r where r.reservationOwner.id = :userId AND r.date >= CURRENT_DATE
    order by r.date asc 
""")
    Page<Reservation> findFutureReservationsByOwnerId(@Param("userId") Long userId, Pageable pageable);
}