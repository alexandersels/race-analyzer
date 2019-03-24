package com.racing.analyzer.backend.builders;

import com.racing.analyzer.backend.entities.Race;

public class RaceBuilder extends BaseBuilder<Race> {

    private String name;
    private String url;
    private boolean isRecording;

    public RaceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RaceBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public RaceBuilder isRecording(boolean isRecording) {
        this.isRecording = isRecording;
        return this;
    }

    @Override
    protected Race createInstance() {
        return Race.builder()
                   .name(name)
                   .recording(isRecording)
                   .url(url)
                   .build();
    }

}
