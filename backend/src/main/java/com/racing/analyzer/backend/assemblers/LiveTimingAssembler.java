package com.racing.analyzer.backend.assemblers;

import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.dto.race.RaceDTO;
import com.racing.analyzer.backend.resources.LiveTimingResource;
import com.racing.analyzer.backend.resources.RaceResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class LiveTimingAssembler implements ResourceAssembler<LiveTimingDTO, Resource<LiveTimingDTO>> {

    @Override
    public Resource<LiveTimingDTO> toResource(LiveTimingDTO liveTimingDTO) {
        return new Resource<>(liveTimingDTO,
                linkTo(methodOn(LiveTimingResource.class).getById(liveTimingDTO.getId())).withSelfRel(),
                linkTo(methodOn(LiveTimingResource.class).getRaces()).withRel("livetimings"));

    }
}

