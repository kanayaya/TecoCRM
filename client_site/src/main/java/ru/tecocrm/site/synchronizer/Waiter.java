package ru.tecocrm.site.synchronizer;

import java.util.Optional;

public class Waiter<T> {
    private T result;
    private boolean resultIsNull = false;

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
    public synchronized void set(T result) {
        if (result == null) resultIsNull = true;
        this.result = result;
        notify();
    }
}
