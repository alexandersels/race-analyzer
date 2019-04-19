package com.racing.analyzer.backend.controllers.impl;

import com.racing.analyzer.backend.assemblers.DetailedDriverAssembler;
import com.racing.analyzer.backend.controllers.DetailedDriverController;
import com.racing.analyzer.backend.dto.statistics.DetailedDriverDTO;
import com.racing.analyzer.backend.resources.DetailedDriverResource;
import com.racing.analyzer.backend.services.DetailedDriverService;
import com.racing.analyzer.backend.services.impl.DetailedDriverServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Component
public class DetailedDriverControllerImpl implements DetailedDriverController {

    @Autowired
    private DetailedDriverService detailedDriverService;

    @Autowired
    private DetailedDriverAssembler detailedDriverAssembler;

    @Override
    public ResponseEntity<?> getDetailedDriverList(@PathVariable long id) {
        Collection<DetailedDriverResource> detailedDriverResources = detailedDriverService
                .getDetailedDriverList(id)
                .stream()
                .map(dto -> detailedDriverAssembler.toResource(dto))
                .collect(toList());
        Resources resources = new Resources<>(detailedDriverResources);
        return ResponseEntity.ok().body(resources);

    }

    @Override
    public ResponseEntity<?> getDetailedDriver(@PathVariable long id, @PathVariable int number) {
        DetailedDriverDTO detailedDriverDto = detailedDriverService.getDetailedDriver(id, number);
        return ResponseEntity.ok().body(detailedDriverAssembler.toResource(detailedDriverDto));
    }

}
