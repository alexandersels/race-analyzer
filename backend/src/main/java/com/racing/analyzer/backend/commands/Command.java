package com.racing.analyzer.backend.commands;

public abstract class Command {

    private final int version;

    protected  Command(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

}
