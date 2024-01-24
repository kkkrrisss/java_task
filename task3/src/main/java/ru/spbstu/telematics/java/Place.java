
package ru.spbstu.telematics.java;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Place {

    private final int number;
    private State state = State.FREE;

    private final Lock lock = new ReentrantLock(true);

    public Place(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Lock getLock() {
        return lock;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}