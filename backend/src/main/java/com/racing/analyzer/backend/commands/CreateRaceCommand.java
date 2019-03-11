package com.racing.analyzer.backend.commands;

import com.racing.analyzer.backend.builders.commands.CreateRaceCommandBuilder;
import com.racing.analyzer.backend.dto.RaceDTO;
import com.racing.analyzer.backend.entities.Race;

public class CreateRaceCommand {

    private String name;
    private boolean isRecording;

    public CreateRaceCommand(String name, boolean isRecording) {
        this.name = name;
        this.isRecording = isRecording;
    }

    public Race getEntityToCreate() {
        return Race.getBuilder()
                .withName(name)
                .build();
    }

    public static CreateRaceCommandBuilder getBuilder() {
        return new CreateRaceCommandBuilder();
    }

}
