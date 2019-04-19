package com.racing.analyzer.backend.repositories;

import com.racing.analyzer.backend.entities.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

}
