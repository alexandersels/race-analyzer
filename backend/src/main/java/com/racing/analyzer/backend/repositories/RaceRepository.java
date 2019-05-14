package com.racing.analyzer.backend.repositories;

import com.racing.analyzer.backend.entities.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    @Query("SELECT r FROM Race r WHERE r.recording = true")
    Collection<Race> findAllRecordingRaces();
}
