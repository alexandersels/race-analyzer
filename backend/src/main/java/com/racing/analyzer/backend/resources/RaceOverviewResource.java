package com.racing.analyzer.backend.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.racing.analyzer.backend.assemblers.DriverAssembler;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Getter
public class RaceOverviewResource extends ResourceSupport {

    @JsonIgnore
    private final DriverAssembler driverAssembler;

    private final String name;
    private final int amountOfDrivers;
    private final int amountOfRounds;
    private final int amountOfPitStops;
    private final DriverResource winner;
    private final Collection<DriverResource> drivers;

    private RaceOverviewResource(RaceOverviewDTO dto) {
        driverAssembler = new DriverAssembler();

        name = dto.getName();
        amountOfDrivers = dto.getAmountOfDrivers();
        amountOfRounds = dto.getAmountOfRounds();
        amountOfPitStops = dto.getAmountOfPitStops();
        winner = DriverResource.fromDto(dto.getWinner());
        if(dto.getDrivers() != null) {
            drivers = dto.getDrivers().stream()
                    .map(driver -> driverAssembler.toResource(driver))
                    .collect(toList());
        }
        else {
            drivers = null;
        }

    }

    public static RaceOverviewResource fromDto(RaceOverviewDTO dto) {
        if (dto == null) {
            return null;
        }

        return new RaceOverviewResource(dto);
    }
}
