package com.racing.analyzer.backend.builders;

public abstract class BaseBuilder<T> {

    public boolean isValid() {
        return true;
    }

    public T build() {
        if (isValid()) {
            return createInstance();
        } else {
            throw new NullPointerException();
        }
    }

    protected abstract T createInstance();
}
