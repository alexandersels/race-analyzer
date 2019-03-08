package com.racing.analyzer.backend.entities;

import com.racing.analyzer.backend.commands.Command;
import com.racing.analyzer.backend.commands.ICommandHandler;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@MappedSuperclass
public abstract class BaseEntity {

    @Transient
    private final Collection<ICommandHandler> commandHandlers = new ArrayList<>();

    @Column(name = "version")
    @Version
    @NotNull
    protected int version;

    protected BaseEntity() {
        registerCommandHandlers();
    }

    public int getVersion() {
        return version;
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
