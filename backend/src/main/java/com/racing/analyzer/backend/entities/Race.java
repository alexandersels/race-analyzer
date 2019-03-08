package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.builders.RaceBuilder;
import com.racing.analyzer.backend.commands.HandlerImplementation;
import com.racing.analyzer.backend.commands.ICommandHandler;
import com.racing.analyzer.backend.commands.UpdateRaceCommand;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "Race")
public class Race extends BaseEntity{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "recording")
    private boolean recording;

    public Race(String name, boolean recording) {
        this.name = name;
        this.recording = recording;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean getRecording() {
        return recording;
    }

    @Override
    protected Collection<ICommandHandler> getCommandHandlers() {
        return Arrays.asList(
                new HandlerImplementation<>(UpdateRaceCommand.class, this::update));
    }

    private void update(UpdateRaceCommand command) {

        if(!name.equals(command.getName())) {
            name = command.getName();
        }

        if(recording != command.isRecording()) {
            recording = command.isRecording();
        }

    }

    public static RaceBuilder getBuilder() {
        return new RaceBuilder();
    }


}
