package io.github.kazikw.boisgo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
//@Data
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    String city;
    private String address;


    @OneToMany(mappedBy = "facility")
    // To mówi Hibernate'owi: "Relacją zarządza pole 'facility' w klasie Reservation"
    List<Reservation> reservationList;

    //TODO poczytać o bazach daanych. Np skąd mam wiedziec czy city to pwoinna byc skladowa czy osobna tabela

}



