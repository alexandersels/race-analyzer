package com.racing.analyzer.backend.assemblers;

import com.racing.analyzer.backend.controllers.LiveTimingController;
import com.racing.analyzer.backend.controllers.RaceController;
import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.resources.LiveTimingResource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class LiveTimingAssembler implements ResourceAssembler<LiveTimingDTO, LiveTimingResource> {

    @Override
    public LiveTimingResource toResource(LiveTimingDTO liveTimingDTO) {
        LiveTimingResource resource = LiveTimingResource.fromDto(liveTimingDTO);
        resource.add(linkTo(methodOn(LiveTimingController.class).getById(liveTimingDTO.getId())).withSelfRel());
        resource.add(linkTo(methodOn(RaceController.class).getById(liveTimingDTO.getRace())).withRel("race"));
        return resource;
    }
}

