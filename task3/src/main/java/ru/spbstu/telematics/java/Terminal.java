package ru.spbstu.telematics.java;

import java.util.HashMap;

public class Terminal {

    private final static HashMap<Integer, Place> places = new HashMap<>();
    private final int number;

    public Terminal(int number) {
        this.number = number;
    }

    private Place selectedPlace;

    public static void addPlace(Place place) {
        places.put(place.getNumber(), place);
        System.out.println("Added place " + place.getNumber());
    }

    public State getState(int placeNumber) {
        Place place = places.get(placeNumber);
        if (place == null) {
            return null;
        }
        return place.getState();
    }

    public boolean selectPlace(int placeNumber) {
        Place place = places.get(placeNumber);
        if (place == null) {
            return false;
        }
        place.getLock().lock();
        try {
            if (place.getState() == State.FREE) {
                Thread.sleep(1000);
                place.setState(State.SELECTED);
                selectedPlace = place;
                System.out.println("Terminal " + number + ": Selected place " + place.getNumber() + " by terminal " + number);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            place.getLock().unlock();
        }

        return selectedPlace == place;
    }

    public void reservePlace() {
        if (selectedPlace == null) {
            return;
        }
        selectedPlace.getLock().lock();
        try {
            if (selectedPlace.getState() == State.SELECTED) {
                Thread.sleep(1000);
                selectedPlace.setState(State.RESERVED);
                System.out.println("Terminal " + number + ": Reserved place " + selectedPlace.getNumber());
            } else if (selectedPlace.getState() == State.RESERVED) {
                System.out.println("Terminal " + number + ": Cannot reserve place " + selectedPlace.getNumber() + ", already reserved");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            selectedPlace.getLock().unlock();
        }

    }

}