package io.github.kazikw.boisgo.repository;

import io.github.kazikw.boisgo.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
}