package io.github.kazikw.boisgo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Data
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @ManyToOne
    @JoinColumn(name = "facility_id")//TODO POCZYTAC KIEDY UZYWEAC JOINCOLUMN. CZy zawszy czy jest tu potrzebne?
    Facility facility;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    User reservationOwner;
    LocalDate date;
    boolean isPublic;//TODO bollean a nie BOOLEAN; typ prost zamiast obiektu
    int capacity;
    private LocalTime startTime;
    @Enumerated(EnumType.STRING) // Krytyczne!
    ReservationStatus status;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ReservationParticipant> participantList;

    //TODO DO Rozwazenia czy godzina rozpoczecia bedizemy wyciagac z daty. Czy rezerwujamy zawsze na godzine czy pozwalamy cusom hours

    public boolean isFinished(){
        if (this.getDate().isBefore(LocalDate.now())){
            return true;
        };
        return (this.getDate().equals(LocalDate.now()) && this.getStartTime().isBefore(LocalTime.now()));

    }

    public boolean isOwner(Long userId){
        return this.reservationOwner.getId().equals(userId);
    }

}



//@OneToMany(mappedBy = "reservation", cascade = CascadeType.REMOVE, orphanRemoval = true)
//private List<ReservationParticipant> participants = new ArrayList<>();