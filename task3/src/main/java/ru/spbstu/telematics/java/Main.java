package ru.spbstu.telematics.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {

        for (int i = 1; i <= 16; i++) {
            Terminal.addPlace(new Place(i));
        }
        try (ExecutorService executorService = Executors.newFixedThreadPool(4)) {
            for (int i = 1; i <= 4; i++) {
                Terminal terminal = new Terminal(i);
                executorService.submit(() -> {
                    for (int j = 1; j <= 10; j++) {
                        int random = ThreadLocalRandom.current().nextInt(16) + 1;
                        terminal.selectPlace(random);
                        terminal.reservePlace();
                    }
                });
            }
        }

    }
}
