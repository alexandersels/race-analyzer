package com.racing.analyzer.backend.assemblers;

import com.racing.analyzer.backend.controllers.impl.DetailedDriverControllerImpl;
import com.racing.analyzer.backend.controllers.impl.LiveTimingControllerImpl;
import com.racing.analyzer.backend.dto.statistics.DriverDTO;
import com.racing.analyzer.backend.resources.DriverResource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class DriverAssembler implements ResourceAssembler<DriverDTO, DriverResource> {

    @Override
    public DriverResource toResource(DriverDTO driverDTO) {
        DriverResource resource = DriverResource.fromDto(driverDTO);
        resource.add(linkTo(methodOn(DetailedDriverControllerImpl.class)
                .getDetailedDriver(driverDTO.getRaceId(), driverDTO.getNumber()))
                .withRel("driverDetails"));
        resource.add(linkTo(methodOn(LiveTimingControllerImpl.class)
                .getTimingsForRaceAndDriver(driverDTO.getRaceId(), driverDTO.getNumber()))
                .withRel("timings"));
        return resource;
    }

}