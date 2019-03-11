package com.racing.analyzer.backend.resources;

import org.springframework.hateoas.Resource;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class BaseResource {

    protected BaseResource() {

    }

    protected URI createUri(Resource<?> resource) {
        try {
            return new URI(resource.getId().expand().getHref());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
