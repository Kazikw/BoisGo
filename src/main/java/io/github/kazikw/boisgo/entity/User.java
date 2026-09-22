package io.github.kazikw.boisgo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "users") // Zmieniamy nazwe tabeli, bo 'user' to czesto slowo zastrzezone w SQL
//@Data
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String login;
    String password;
    String email;
    String nick;
    @Enumerated(EnumType.STRING)// w bazie apisze sie nazwa zamiast numeru. Dzieki temu jak doda sie kolejny wpis to sie nie rozjedzie
    private Role role;
    @ManyToMany
    @JoinTable(
            name = "admin_facilities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "facility_id")
    )
    private List<Facility> managedFacilities;
}
