package com.racing.analyzer.backend.commands.race;

import com.racing.analyzer.backend.dto.race.CreateRaceDTO;
import com.racing.analyzer.backend.entities.Race;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateRaceCommand {

    private String name;
    private String url;

    @Builder
    public CreateRaceCommand(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public static CreateRaceCommand fromDTO(CreateRaceDTO dto) {
        return CreateRaceCommand.builder()
                .name(dto.getName())
                .url(dto.getUrl())
                .build();
    }

    public Race getEntityToCreate() {
        return Race.builder()
                .name(name)
                .url(url)
                .recording(false)
                .build();
    }

}
