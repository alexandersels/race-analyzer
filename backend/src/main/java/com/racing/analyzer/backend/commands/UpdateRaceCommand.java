package com.racing.analyzer.backend.commands;

import com.racing.analyzer.backend.builders.commands.UpdateRaceCommandBuilder;

public class UpdateRaceCommand extends Command {

    private final String name;
    private final boolean recording;

    public UpdateRaceCommand(String name, boolean recording, int version) {
        super(version);
        this.name = name;
        this.recording = recording;
    }

    public static UpdateRaceCommandBuilder getBuilder() {
        return new UpdateRaceCommandBuilder();
    }

    public String getName() {
        return name;
    }

    public boolean isRecording() {
        return recording;
    }
}
