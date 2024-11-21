package me.zahar.task;

import java.util.Random;

public class AuctionMain {
    public static void main(String[] args) {
        int startPrice = 50;

        for (int auctionNum = 1; auctionNum <= 3; auctionNum++) { // Три аукциона
            int numberParticipant = 3 + new Random().nextInt(3); // 3–5 участников
            System.out.println("Аукцион #" + auctionNum + ", число участников: " + numberParticipant);
            Auction auction = new Auction(numberParticipant);

            for (int num = 0; num < numberParticipant; num++) {
                int cash = 100 + new Random().nextInt(50);
                Participant participant = new Participant(num, startPrice, cash);
                auction.add(participant);
                participant.start();
            }

            // Ждём завершения всех потоков текущего аукциона
            try {
                for (Thread thread : Thread.getAllStackTraces().keySet()) {
                    if (thread.getName().startsWith("Participant")) {
                        thread.join();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("\n");
        }
    }
}

