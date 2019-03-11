package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.builders.RaceBuilder;
import com.racing.analyzer.backend.commands.HandlerImplementation;
import com.racing.analyzer.backend.commands.ICommandHandler;
import com.racing.analyzer.backend.commands.UpdateRaceCommand;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Race")
public class Race extends BaseEntity {

    @Id
    @Column(name = "id")
    @Access(AccessType.PROPERTY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "recording")
    private boolean recording;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "race",fetch = FetchType.LAZY)
    private List<LiveTiming> timings;

    public Race() {
    }

    public Race(String name, boolean recording, String url) {
        this.name = name;
        this.recording = recording;
        this.url = url;
    }

    public static RaceBuilder getBuilder() {
        return new RaceBuilder();
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean isRecording() {
        return recording;
    }

    public List<LiveTiming> getTimings() {
        return timings;
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

        if(!url.equals(command.getUrl())) {
            url = command.getUrl();
        }

    }

    public String getUrl() {
        return url;
    }
}
