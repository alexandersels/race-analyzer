package com.racing.analyzer.backend.assemblers;

import com.racing.analyzer.backend.controllers.LiveTimingController;
import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.resources.DetailedDriverResource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class DetailedDriverAssembler implements ResourceAssembler<DetailedDriverDTO, DetailedDriverResource> {

    @Override
    public DetailedDriverResource toResource(DetailedDriverDTO driverDTO) {
        DetailedDriverResource resource = DetailedDriverResource.fromDto(driverDTO);
        resource.add(linkTo(methodOn(LiveTimingController.class)
                .getTimingsForRaceAndDriver(driverDTO.getRaceId(), driverDTO.getNumber()))
                .withRel("timings"));
        return resource;
    }

}