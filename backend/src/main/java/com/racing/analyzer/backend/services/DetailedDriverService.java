package com.racing.analyzer.backend.services;

import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;

import java.util.Collection;
import javax.transaction.Transactional;

public interface DetailedDriverService {

    Collection<DetailedDriverDTO> getDetailedDriverList(long raceId);

    DetailedDriverDTO getDetailedDriver(long raceId, int driverNumber);
}
