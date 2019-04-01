package com.racing.analyzer.backend.repositories;

import com.racing.analyzer.backend.entities.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    @Query("select race from Race race left join fetch race.timings where race.id =:id")
    Optional<Race> getRaceJoinedWithTimings(@Param("id") long id);

}
