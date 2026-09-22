package io.github.kazikw.boisgo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Blockades {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "facility_id")
    Facility facility;
    String reasonOfBlock;

    LocalDate startDate;
    LocalDate endDate;
    LocalTime startHour;
    LocalTime endHour;

}
