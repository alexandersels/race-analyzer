package com.racing.analyzer.backend.resources;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.racing.analyzer.backend.assemblers.DriverAssembler;
import com.racing.analyzer.backend.dto.statistics.RaceOverviewDTO;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;

@Getter
public class RaceOverviewResource extends ResourceSupport {

    private final String name;
    private final int amountOfDrivers;
    private final int amountOfRounds;
    private final int amountOfPitStops;
    private final DriverResource winner;

    @JsonUnwrapped
    private final Resources drivers;

    private RaceOverviewResource(RaceOverviewDTO dto) {
        DriverAssembler driverAssembler = new DriverAssembler();
        EmbeddedWrappers wrapper = new EmbeddedWrappers(false);
        name = dto.getName();
        amountOfDrivers = dto.getAmountOfDrivers();
        amountOfRounds = dto.getAmountOfRounds();
        amountOfPitStops = dto.getAmountOfPitStops();
        winner = DriverResource.fromDto(dto.getWinner());
        List<EmbeddedWrapper> collect = dto.getDrivers().stream()
                                           .map(driverDTO -> wrapper.wrap(driverAssembler.toResource(driverDTO)))
                                           .collect(toList());
        drivers = new Resources(collect);

    }

    public static RaceOverviewResource fromDto(RaceOverviewDTO dto) {
        if(dto == null) {
            return null;
        }
        return new RaceOverviewResource(dto);
    }
}
