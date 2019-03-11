package com.racing.analyzer.backend.builders.commands;

import com.racing.analyzer.backend.builders.BaseBuilder;
import com.racing.analyzer.backend.commands.CreateRaceCommand;
import com.racing.analyzer.backend.dto.RaceDTO;

public class CreateRaceCommandBuilder extends BaseBuilder<CreateRaceCommand> {

    private String name;
    private boolean isRecording;

    public CreateRaceCommandBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CreateRaceCommandBuilder isRecording(boolean isRecording) {
        this.isRecording = isRecording;
        return this;
    }

    public CreateRaceCommandBuilder startFrom(RaceDTO dto) {
        this.name = dto.name;
        this.isRecording = dto.recording;
        return this;
    }

    @Override
    protected CreateRaceCommand createInstance() {
        return new CreateRaceCommand(name, isRecording);
    }
}
