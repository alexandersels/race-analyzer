package com.racing.analyzer.backend.commands;

import com.racing.analyzer.backend.builders.commands.CreateRaceCommandBuilder;
import com.racing.analyzer.backend.entities.Race;

public class CreateRaceCommand {

    private String name;
    private boolean isRecording;
    private String url;

    public CreateRaceCommand(String name, boolean isRecording, String url) {
        this.name = name;
        this.isRecording = isRecording;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public String getUrl() {
        return url;
    }

    public Race getEntityToCreate() {
        return Race.builder()
                .name(name)
                .url(url)
                .recording(isRecording)
                .build();
    }

    public static CreateRaceCommandBuilder getBuilder() {
        return new CreateRaceCommandBuilder();
    }
}
