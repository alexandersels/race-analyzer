package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.commands.HandlerImplementation;
import com.racing.analyzer.backend.commands.ICommandHandler;
import com.racing.analyzer.backend.commands.race.UpdateRaceCommand;
import lombok.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "race")
@Getter
@NoArgsConstructor
public class Race extends BaseEntity {

    @Id
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "recording")
    private boolean recording;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "race", fetch = FetchType.LAZY)
    private List<LiveTiming> timings;

    @Builder
    public Race(long id, String name, boolean recording, String url, List<LiveTiming> timings) {
        this.id = id;
        this.name = name;
        this.recording = recording;
        this.url = url;
        this.timings = timings;
    }

    @Override
    protected Collection<ICommandHandler> getCommandHandlers() {
        return Arrays.asList(
                new HandlerImplementation<>(UpdateRaceCommand.class, this::update));
    }

    private void update(UpdateRaceCommand command) {

        if (!name.equals(command.getName())) {
            name = command.getName();
        }

        if (recording != command.isRecording()) {
            recording = command.isRecording();
        }

        if (!url.equals(command.getUrl())) {
            url = command.getUrl();
        }

    }

    public String getUrl() {
        return url;
    }
}
