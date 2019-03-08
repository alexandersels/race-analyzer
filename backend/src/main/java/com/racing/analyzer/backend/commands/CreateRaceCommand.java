package com.racing.analyzer.backend.commands;

import com.racing.analyzer.backend.dto.RaceDTO;
import com.racing.analyzer.backend.entities.Race;

public class CreateRaceCommand {

    private final String name;

    private CreateRaceCommand(String name) {
        this.name = name;
    }

    public Race getEntityToCreate() {
        return Race.getBuilder()
                .withName(name)
                .build();
    }

    public static CreateRaceCommand of(RaceDTO dto) {
        if(dto == null) {
            throw new NullPointerException("dto");
        }
        return new CreateRaceCommand(dto.name);
    }
}
