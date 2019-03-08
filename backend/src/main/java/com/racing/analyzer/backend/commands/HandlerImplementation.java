package com.racing.analyzer.backend.commands;

import java.util.function.Consumer;

public class HandlerImplementation<C extends Command> implements ICommandHandler {

    private final Class<C> commandType;
    private final Consumer<C> doHandle;

    public HandlerImplementation(Class<C> commandType, Consumer<C> doHandle){
        this.commandType = commandType;
        this.doHandle = doHandle;
    }

    @Override
    public boolean canExecute(Command command) {
        return commandType.isAssignableFrom(command.getClass());
    }

    @Override
    public void execute(Command command) {
        doHandle.accept((C) command);
    }
}
