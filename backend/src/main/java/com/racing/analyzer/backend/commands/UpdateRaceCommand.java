package com.racing.analyzer.backend.commands;

import com.racing.analyzer.backend.dto.RaceDTO;

public class UpdateRaceCommand extends Command {

    private final String name;
    private final boolean recording;

    private UpdateRaceCommand(String name, boolean recording, int version) {
        super(version);
        this.name = name;
        this.recording = recording;
    }

    public String getName() {
        return name;
    }

    public boolean isRecording() {
        return recording;
    }

    public static UpdateRaceCommand of(RaceDTO raceDTO) {
        return new UpdateRaceCommand(raceDTO.name, raceDTO.recording, raceDTO.version);
    }
}
