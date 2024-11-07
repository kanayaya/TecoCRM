package ru.tecocrm.site.synchronizer;

public class Waiter<T> {
    private T result;
    private boolean resultIsNull = false;

    public synchronized T get() throws InterruptedException {
        while (result == null && ! resultIsNull) {
            wait();
        }
        return result;
    }
    public synchronized void set(T result) {
        if (result == null) resultIsNull = true;
        this.result = result;
        notify();
    }
}
