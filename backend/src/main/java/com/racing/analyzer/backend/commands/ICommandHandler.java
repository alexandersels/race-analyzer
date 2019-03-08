package com.racing.analyzer.backend.commands;

public interface ICommandHandler {

    boolean canExecute(Command command);

    void execute(Command command);
}
