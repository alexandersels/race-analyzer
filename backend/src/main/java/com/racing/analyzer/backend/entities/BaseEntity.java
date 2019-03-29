package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.commands.Command;
import com.racing.analyzer.backend.commands.ICommandHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseEntity {

    @Transient
    private final Collection<ICommandHandler> commandHandlers = new ArrayList<>();

    @Column(name = "version")
    @Version
    @Getter
    protected int version;

    protected BaseEntity() {
        registerCommandHandlers();
    }

    public void execute(Command command) {
        if (command == null) {
            throw new NullPointerException("command");
        }

        checkVersion(command);
        commandHandlers.stream()
                .filter(handler -> handler.canExecute(command))
                .forEach(handler -> handler.execute(command));
    }

    protected abstract Collection<ICommandHandler> getCommandHandlers();

    private void checkVersion(Command command) {
        if (this.version != command.getVersion()) {
            throw new OptimisticLockException("version mismatch !");
        }
    }

    private void registerCommandHandlers() {
        this.commandHandlers.addAll(getCommandHandlers());
    }

}

