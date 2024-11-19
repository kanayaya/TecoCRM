package ru.tecocrm.site.synchronizer;

import lombok.NonNull;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Waiter<T> implements Future<Optional<T>>, Consumer<T> {
    private T result;
    private boolean resultIsNull = false;

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return result != null || resultIsNull;
    }

    public synchronized Optional<T> get() {
        while (result == null && ! resultIsNull) {
            try {
                wait();
            } catch (InterruptedException e) {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public synchronized Optional<T> get(long timeout, @NonNull TimeUnit unit) {
        try {
            wait(unit.toMillis(timeout));
        } catch (InterruptedException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(result);
    }

    public synchronized void accept(T result) {
        if (result == null) resultIsNull = true;
        this.result = result;
        notify();
    }
}
