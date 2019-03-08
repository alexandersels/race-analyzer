package com.racing.analyzer.backend.builders;

import com.racing.analyzer.backend.entities.Race;

public class RaceBuilder {

    private String name;

    public RaceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Race build() {
        return new Race(name, false);
    }

}
