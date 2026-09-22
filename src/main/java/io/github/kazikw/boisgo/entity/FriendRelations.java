package io.github.kazikw.boisgo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendRelations {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_a_id")//test czy to jest dobrze bo ide wygenerowalo
    User userA;

    @ManyToOne
    @JoinColumn(name = "user_b_id")
    User userB;


}
