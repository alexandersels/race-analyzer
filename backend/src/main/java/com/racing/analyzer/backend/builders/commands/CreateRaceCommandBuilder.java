package com.racing.analyzer.backend.builders.commands;

import com.racing.analyzer.backend.builders.BaseBuilder;
import com.racing.analyzer.backend.commands.CreateRaceCommand;
import com.racing.analyzer.backend.dto.RaceDTO;

public class CreateRaceCommandBuilder extends BaseBuilder<CreateRaceCommand> {

    private String name;
    private boolean isRecording;
    private String url;

    public CreateRaceCommandBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateRaceCommandBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public CreateRaceCommandBuilder isRecording(boolean isRecording) {
        this.isRecording = isRecording;
        return this;
    }

    public CreateRaceCommandBuilder startFrom(RaceDTO dto) {
        this.name = dto.getName();
        this.isRecording = dto.isRecording();
        this.url = dto.getUrl();
        return this;
    }

    @Override
    protected CreateRaceCommand createInstance() {
        return new CreateRaceCommand(name, isRecording, url);
    }
}
