package com.racing.analyzer.backend.commands;

import lombok.Getter;

@Getter
public abstract class Command {

    private final int version;

    protected  Command(int version) {
        this.version = version;
    }

}
