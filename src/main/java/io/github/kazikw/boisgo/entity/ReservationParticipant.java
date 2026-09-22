package io.github.kazikw.boisgo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
//TOdo XXXdo skminienia czy dawac buildera tu
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
//@IdClass(ReservationParticipantId.class)

public class ReservationParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;// TODO WYWALILBYM TO ID BO I TAK NIE BD UZYWAL
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    Reservation reservation;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User participant;



}
