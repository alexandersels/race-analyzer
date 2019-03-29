package com.racing.analyzer.backend.resources;

import com.racing.analyzer.backend.assemblers.LiveTimingAssembler;
import com.racing.analyzer.backend.dto.livetiming.LiveTimingDTO;
import com.racing.analyzer.backend.services.LiveTimingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class LiveTimingResource extends BaseResource {

    @Autowired
    private LiveTimingService service;

    @Autowired
    private LiveTimingAssembler assembler;

    @GetMapping("/livetimings/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        Optional<LiveTimingDTO> liveTiming = service.getById(id);
        return liveTiming.map(dto -> {
            Resource resource = assembler.toResource(dto);
            return ResponseEntity.ok().body(resource);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/livetimings")
    public ResponseEntity<?> getRaces() {
        Collection<LiveTimingDTO> liveTimings = service.getAll();
        Resources resources = new Resources<>(liveTimings, linkTo(methodOn(LiveTimingResource.class).getRaces()).withSelfRel());
        return ResponseEntity.ok().body(resources);
    }
}
