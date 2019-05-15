package com.racing.analyzer.backend.assemblers;

import com.racing.analyzer.backend.dto.statistics.RoundDTO;
import com.racing.analyzer.backend.resources.RoundResource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class RoundAssembler implements ResourceAssembler<RoundDTO, RoundResource> {

    @Override
    public RoundResource toResource(RoundDTO roundDto) {
        return RoundResource.fromDto(roundDto).orElse(null);
    }
}

