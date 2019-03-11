package com.racing.analyzer.backend.builders.commands;

import com.racing.analyzer.backend.builders.BaseBuilder;
import com.racing.analyzer.backend.commands.UpdateRaceCommand;
import com.racing.analyzer.backend.dto.RaceDTO;
import com.racing.analyzer.backend.entities.Race;

public class UpdateRaceCommandBuilder extends BaseBuilder<UpdateRaceCommand> {

    private String name;
    private String url;
    private boolean isRecording;
    private int version;

    public UpdateRaceCommandBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UpdateRaceCommandBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public UpdateRaceCommandBuilder isRecording(boolean isRecording) {
        this.isRecording = isRecording;
        return this;
    }

    public UpdateRaceCommandBuilder withVersion(int version) {
        this.version = version;
        return this;
    }

    public UpdateRaceCommandBuilder startFrom(RaceDTO dto) {
        this.name = dto.name;
        this.url = dto.url;
        this.version = dto.version;
        this.isRecording = dto.recording;
        return this;
    }

    public UpdateRaceCommandBuilder startFrom(Race race) {
        this.name = race.getName();
        this.url = race.getUrl();
        this.version = race.getVersion();
        this.isRecording = race.isRecording();
        return this;
    }

    @Override
    protected UpdateRaceCommand createInstance() {
        return new UpdateRaceCommand(name, isRecording, url, version);
    }
}
