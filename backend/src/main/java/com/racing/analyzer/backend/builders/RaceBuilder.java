package com.racing.analyzer.backend.builders;

import com.racing.analyzer.backend.entities.Race;

public class RaceBuilder extends BaseBuilder<Race> {

    private String name;

    public RaceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected Race createInstance() {
        return new Race(name, false);
    }

}
