package com.racing.analyzer.backend.repositories;

import com.racing.analyzer.backend.entities.LiveTiming;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveTimingRepository extends JpaRepository<LiveTiming, Long> {
}
