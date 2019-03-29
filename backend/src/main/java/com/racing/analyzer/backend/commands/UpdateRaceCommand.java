package com.racing.analyzer.backend.commands;

import com.racing.analyzer.backend.dto.race.UpdateRaceDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateRaceCommand extends Command {

    private final long id;
    private final String name;
    private final boolean recording;
    private final String url;

    @Builder
    public UpdateRaceCommand(long id, String name, boolean recording, String url, int version) {
        super(version);
        this.id = id;
        this.name = name;
        this.recording = recording;
        this.url = url;
    }

    public static UpdateRaceCommand fromDto(UpdateRaceDTO dto) {
        return UpdateRaceCommand.builder()
                .id(dto.getId())
                .name(dto.getName())
                .recording(dto.isRecording())
                .url(dto.getUrl())
                .version(dto.getVersion())
                .build();
    }
}
